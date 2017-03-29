package com.baiyigame.adslibrary.manager;


import android.content.Context;

import com.baiyigame.adslibrary.Utils.PreferenceUtils;
import com.baiyigame.adslibrary.base.BaseModel;
import com.baiyigame.adslibrary.config.Define;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.OpenAdvertyModel;
import com.baiyigame.adslibrary.model.TokenModel;
import com.baiyigame.adslibrary.net.HttpUtils;
import com.baiyigame.adslibrary.net.JSONBase;
import com.baiyigame.adslibrary.net.JsonUtils;
import com.baiyigame.adslibrary.net.UrlUtils;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;
import com.baiyigame.adslibrary.port.ILoadLisener;

/**
 * Created by Administrator on 2017/3/15.
 */

public class CoopenNetManager
{
    private static CoopenNetManager instence = null;

    private Context mContext = null;

    private CoopenNetManager(Context context)
    {
        this.mContext = context;
    }

    public static CoopenNetManager getInstence(Context context)
    {
        if (instence == null)
            instence = new CoopenNetManager(context);
        return instence;
    }

    private MeterailModel meterailModel = null;
    private OpenAdvertyModel openAdvertyModel = null;

    public CoopenNetManager request(String key,ILoadLisener loadLisener)
    {
        BaseModel baseModel = new BaseModel();
        openAdvertyModel = (OpenAdvertyModel) baseModel.open(key + Define.Unity_Coopen_Key);
        if (openAdvertyModel != null)
        {
            if (openAdvertyModel.getStatus() != -1)
            {
                meterailModel = (MeterailModel) baseModel.open(key+Define.Unity_ADS_Meterail);
                if (meterailModel != null)
                {
                    if (meterailModel.getStatus() != -1)
                    {
                        if (loadLisener != null) {
                              loadLisener.onSuccess(meterailModel, openAdvertyModel);
                            }
                        TokenModel md = (TokenModel) baseModel.open(Define.TOKEN);
                       if (md != null)
                       {
                           if (md.getStatus() != -1)
                           {
                               if (PreferenceUtils.getInstence(mContext).Get("isModify", false))
                                {
                                   GetADSInfo(md,key,loadLisener);
                                }
                               GetMeterail(md, key,loadLisener);
                           }
                           else
                           {
                               GetADSInit(key,loadLisener);
                           }
                       }
                       else
                       {
                           GetADSInit(key,loadLisener);
                       }
                        return getInstence(mContext);
                    }
                    else
                    {
                        GetADSInit(key,loadLisener);
                    }
                }
                else
                {
                    GetADSInit(key,loadLisener);
                }
            }
            else
            {
                GetADSInit(key,loadLisener);
            }
        }
        else
        {
            GetADSInit(key,loadLisener);
        }
        baseModel = null;
        return getInstence(mContext);
    }

    private void GetMeterail(TokenModel model, final String AdsKey, final ILoadLisener loadLisener)
    {
        if (model.getStatus() == -1)
        {
            if (loadLisener != null)
            {
                loadLisener.onError(model.getMsg(), model.getStatus());
            }
            return;
        }
        HttpUtils utils = new HttpUtils(mContext);
        utils.setUrl(UrlUtils.getMeterailUrl());
        String postData = JsonUtils.getADSMeterailJson(model, AdsKey);
        utils.setBodyData(postData);
        utils.setListener(new IHttpFinishedListener()
        {
            @Override
            public void onSuccess(Object result)
            {
                meterailModel = JSONBase.ToJavaBean(result.toString(), MeterailModel.class);
                meterailModel.save(meterailModel, AdsKey + Define.Unity_ADS_Meterail);
            }

            @Override
            public void onError(String errorMsg, int resposeCode)
            {
                if (loadLisener != null)
                {
                     loadLisener.onError(errorMsg, resposeCode);
                }
            }
        });
        utils.start();
    }
    private void GetADSInfo(final TokenModel model, final String AdsKey, final ILoadLisener loadLisener)
    {

        if (model.getStatus() == -1)
        {
            if (loadLisener != null)
            {
                loadLisener.onError(model.getMsg(), model.getStatus());
            }
            return;
        }

        HttpUtils utils = new HttpUtils(mContext);
        utils.setUrl(UrlUtils.getAdsStyleUrl());
        utils.setBodyData(JsonUtils.getADSMeterailJson(model, AdsKey));
        utils.setListener(new IHttpFinishedListener()
        {
            @Override
            public void onSuccess(Object result)
            {
                //Log.e("Response-testADSInfo:", result.toString());
                openAdvertyModel = JSONBase.ToJavaBean(result.toString(), OpenAdvertyModel.class);
                openAdvertyModel.save(openAdvertyModel, AdsKey + Define.Unity_Coopen_Key);
                GetMeterail(model, AdsKey, loadLisener);
            }

            @Override
            public void onError(String errorMsg, int resposeCode)
            {
                //Log.e("Response error:", errorMsg + ":" + resposeCode);
                if (loadLisener != null)
                {
                    loadLisener.onError(errorMsg, resposeCode);
                }
            }
        });
        utils.start();
    }

    private void GetADSInit(final String AdsKey, final ILoadLisener loadLisener)
    {
        BaseModel baseModel = new BaseModel();
        TokenModel md = (TokenModel) baseModel.open(Define.TOKEN);
        if (md != null)
        {
            if (md.getStatus() != -1)
            {
//               if (PreferenceUtils.getInstence(mContext).Get("isModify", false))
//               {
                   GetADSInfo(md,AdsKey,loadLisener);
//               }
            }
        }
    }
}

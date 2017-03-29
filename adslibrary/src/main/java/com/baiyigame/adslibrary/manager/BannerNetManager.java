package com.baiyigame.adslibrary.manager;


import android.content.Context;

import com.baiyigame.adslibrary.Utils.PreferenceUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.base.BaseModel;
import com.baiyigame.adslibrary.config.ADConfig;
import com.baiyigame.adslibrary.config.Define;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.TokenModel;
import com.baiyigame.adslibrary.net.HttpUtils;
import com.baiyigame.adslibrary.net.JSONBase;
import com.baiyigame.adslibrary.net.JsonUtils;
import com.baiyigame.adslibrary.net.UrlUtils;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;
import com.baiyigame.adslibrary.port.ILoadLisener;

/**
 * Created by Administrator on 2017/3/8.
 */

public class BannerNetManager {

    private static BannerNetManager instence = null;

    private Context mContext = null;

    private BannerNetManager(Context context)
    {
        this.mContext = context;
    }

    public static BannerNetManager getInstence(Context context) {
        if (instence == null)
            instence = new BannerNetManager(context);
        return instence;
    }

    private MeterailModel meterailModel = null;
    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private ILoadLisener loadLisener = null;


    public BannerNetManager coopenRequest(String key) {
        BaseModel baseModel = new BaseModel();
        TokenModel md = (TokenModel) baseModel.open(Define.TOKEN);
        if (md != null) {
            if (PreferenceUtils.getInstence(mContext).Get("isModify", false)) {
                GetADSInfo(md, key);
            } else {
                bannerInfoAdvertyModel = (BannerInfoAdvertyModel) baseModel.open(key + Define.Unity_ADS_Key);
                if (bannerInfoAdvertyModel == null) {
                    GetADSInfo(md, key);
                } else {
                    meterailModel = (MeterailModel) baseModel.open(key + Define.Unity_ADS_Meterail);
                    if (meterailModel != null) {
                        return getInstence(mContext);
                    } else {
                        GetMeterail(md, key);
                    }
                }
            }
        } else {
            GetADSInit(key);
        }

        baseModel = null;
        return getInstence(mContext);

    }

    public BannerNetManager request(String key) {
        BaseModel baseModel = new BaseModel();
        TokenModel md = (TokenModel) baseModel.open(Define.TOKEN);
        if (md != null) {
            if (PreferenceUtils.getInstence(mContext).Get("isModify", false)) {
                GetADSInfo(md, key);
            } else {
                bannerInfoAdvertyModel = (BannerInfoAdvertyModel) baseModel.open(key + Define.Unity_ADS_Key);
                if (bannerInfoAdvertyModel == null) {
                    GetADSInfo(md, key);
                } else {
                    GetMeterail(md, key);
                }
            }
        } else {
            GetADSInit(key);
        }

        baseModel = null;
        return getInstence(mContext);
    }

    private void GetMeterail(TokenModel model, final String AdsKey) {
        if (model.getStatus() == -1) {
            if (loadLisener != null) {
                loadLisener.onError(model.getMsg(), model.getStatus());
            }
            return;
        }
        HttpUtils utils = new HttpUtils(mContext);
        utils.setUrl(UrlUtils.getMeterailUrl());
        String postData = JsonUtils.getADSMeterailJson(model, AdsKey);
        utils.setBodyData(postData);
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {
                meterailModel = JSONBase.ToJavaBean(result.toString(), MeterailModel.class);
                meterailModel.save(meterailModel, AdsKey + Define.Unity_ADS_Meterail);
                //meterailModel = (MeterailModel) meterailModel.open(AdsKey + Define.Unity_ADS_Meterail);
                //Log.e("Response-testADSInfo:", meterailModel.toString());
                if (loadLisener != null) {
                    loadLisener.onSuccess(meterailModel, bannerInfoAdvertyModel);
                }
            }
            @Override
            public void onError(String errorMsg, int resposeCode) {
                //Log.e("Response error:", errorMsg + ":" + resposeCode);
                if (loadLisener != null) {
                    loadLisener.onError(errorMsg, resposeCode);
                }
            }
        });
        utils.start();
    }


    private void GetADSInfo(final TokenModel model, final String AdsKey) {

        if (model.getStatus() == -1) {
            if (loadLisener != null) {
                loadLisener.onError(model.getMsg(), model.getStatus());
            }
            return;
        }

        HttpUtils utils = new HttpUtils(mContext);
        utils.setUrl(UrlUtils.getAdsStyleUrl());
        utils.setBodyData(JsonUtils.getADSMeterailJson(model, AdsKey));
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {
                //Log.e("Response-testADSInfo:", result.toString());
                bannerInfoAdvertyModel = JSONBase.ToJavaBean(result.toString(), BannerInfoAdvertyModel.class);
                bannerInfoAdvertyModel.save(bannerInfoAdvertyModel, AdsKey + Define.Unity_ADS_Key);
                GetMeterail(model, AdsKey);
            }

            @Override
            public void onError(String errorMsg, int resposeCode) {
                //Log.e("Response error:", errorMsg + ":" + resposeCode);
                if (loadLisener != null) {
                    loadLisener.onError(errorMsg, resposeCode);
                }
            }
        });
        utils.start();
    }

    private void GetADSInit(final String AdsKey) {
        String secretKey = PreferenceUtils.getInstence(mContext).Get(ADConfig.Secret_key,"");
        if (Utils.isStringEmpty(secretKey))
        {
            return;
        }

        HttpUtils utils = new HttpUtils(mContext);
        utils.setUrl(UrlUtils.getInitUrl());
        utils.setBodyData(JsonUtils.getTokenJson(secretKey,mContext));
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {
                TokenModel model = JSONBase.ToJavaBean(result.toString(), TokenModel.class);
                BaseModel baseModel = new BaseModel();
                TokenModel md = (TokenModel) baseModel.open(Define.TOKEN);
                if (md == null) {
                    baseModel.save(model, Define.TOKEN);
                    GetADSInfo(model, AdsKey);
                } else {
                   if (model.getStatus()==1&&model.getStatus()==1)
                   {
                       if (md.getData().getModify_time() == model.getData().getModify_time()) {
                           bannerInfoAdvertyModel = (BannerInfoAdvertyModel) baseModel.open(AdsKey + Define.Unity_ADS_Key);
                           // Log.e("Response-testADSInfo:", bannerInfoAdvertyModel.toString());
                           if (bannerInfoAdvertyModel == null) {
                               GetADSInfo(model, AdsKey);
                           } else {
                               GetMeterail(model, AdsKey);
                           }
                       } else {
                           baseModel.save(model, Define.TOKEN);
                           GetADSInfo(model, AdsKey);
                       }
                   }
                }
            }

            @Override
            public void onError(String errorMsg, int resposeCode) {
                // Log.e("Response error:", errorMsg + ":" + resposeCode);
                if (loadLisener != null) {
                    loadLisener.onError(errorMsg, resposeCode);
                }
            }
        });
        utils.start();
    }

    public void setLoadLisener(ILoadLisener loadLisener) {
        this.loadLisener = loadLisener;
    }


}

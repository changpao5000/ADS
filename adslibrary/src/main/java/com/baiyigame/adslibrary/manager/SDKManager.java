package com.baiyigame.adslibrary.manager;

import android.content.Context;

import com.baiyigame.adslibrary.Utils.PreferenceUtils;
import com.baiyigame.adslibrary.config.ADConfig;
import com.baiyigame.adslibrary.config.Define;
import com.baiyigame.adslibrary.model.TokenModel;
import com.baiyigame.adslibrary.net.HttpUtils;
import com.baiyigame.adslibrary.net.JSONBase;
import com.baiyigame.adslibrary.net.JsonUtils;
import com.baiyigame.adslibrary.net.UrlUtils;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;


/**
 * Created by Administrator on 2017/3/16.
 */

public class SDKManager
{

    private static SDKManager instence = null;

    private Context mContext = null;

    private SDKManager(Context context)
    {
        this.mContext = context;
    }

    public static SDKManager getInstence(Context context)
    {
        if (instence == null)
            instence = new SDKManager(context);
        return instence;
    }

    /**
     * 初始化
     * @param secretkey
     * @param loaderListener
     */
    public void init(String secretkey, ADSLoaderListener loaderListener)
    {

        initToken(secretkey, loaderListener);
        //save secretkey
        PreferenceUtils.getInstence(mContext).Set(ADConfig.Secret_key, secretkey);
    }


    public void initToken(String secretkey, final ADSLoaderListener loaderListener)
    {

        HttpUtils utils = new HttpUtils(mContext);
        utils.setUrl(UrlUtils.getInitUrl());
        utils.setBodyData(JsonUtils.getTokenJson(secretkey, mContext));
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {
                TokenModel model = JSONBase.ToJavaBean(result.toString(), TokenModel.class);
                TokenModel md = (TokenModel) model.open(Define.TOKEN);
                if (md == null)
                {
                    model.save(model, Define.TOKEN);
                }
                else
                {
                    if (model.getStatus() != -1 && md.getStatus() != -1)
                    {
                        if (md.getData().getModify_time() == model.getData().getModify_time())
                        {
                            PreferenceUtils.getInstence(mContext).Set("isModify", false);
                        }
                        else
                        {
                            PreferenceUtils.getInstence(mContext).Set("isModify", true);
                        }
                    }
                    else
                    {
                        PreferenceUtils.getInstence(mContext).Set("isModify", true);
                    }
                }
                if (loaderListener != null)
                {
                    loaderListener.onSuccess();
                }
                model.save(model, Define.TOKEN);
            }

            @Override
            public void onError(String errorMsg, int resposeCode)
            {
                // Log.e("Response error:", errorMsg + ":" + resposeCode);
                if (loaderListener != null)
                {
                    loaderListener.onFailure(errorMsg, resposeCode);
                }
            }
        });
        utils.start();
    }
}

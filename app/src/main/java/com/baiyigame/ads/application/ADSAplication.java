package com.baiyigame.ads.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baiyigame.ads.R;
import com.baiyigame.adslibrary.config.ADConfig;
import com.baiyigame.adslibrary.manager.CertificateManager;
import com.baiyigame.adslibrary.manager.CoopenADSManager;
import com.baiyigame.adslibrary.manager.SDKManager;
import com.baiyigame.adslibrary.port.ADSLoaderListener;


/**
 * Created by Administrator on 2017/3/1.
 */

public class ADSAplication extends Application
{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;

        //配置证书
        CertificateManager.ConfigCertificate(context,R.raw.c);

        //配置开屏广告
        CoopenADSManager manager = CoopenADSManager.getInstence(context);
        manager.setUnityKey(ADConfig.Unity_Coopen_Key);
        manager.show();

        //初始化
        SDKManager.getInstence(context).init(ADConfig.Secret_key, new ADSLoaderListener() {
            @Override
            public void onSuccess() {
                Log.e("ADSLibAplication","Init Success ");
            }

            @Override
            public void onFailure(String errorMsg, int resposeCode) {
                Log.e("ADSLibAplication","Init Fauilar ");
            }
        });
    }

    public static Context getAppContext()
    {
        return context;
    }

}

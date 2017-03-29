package com.baiyigame.adslibrary.application;

import android.app.Application;
import android.content.Context;

import java.io.InputStream;


/**
 * Created by Administrator on 2017/3/1.
 */

public class ADSLibAplication extends Application
{
    private static Context context;
    //Certificate of the flow
    private static InputStream certificateStream;
    //Certificate of resource ID
    private static int certificateResId = 0;

    @Override
    public void onCreate()
    {
        super.onCreate();
        this.context = getApplicationContext();
    }


    public Context getAppContext()
    {
        return context;
    }


    public static void setCertificate(InputStream certificateInputStream)
    {
        certificateStream = certificateInputStream;
    }

    public static InputStream getCertificateStream()
    {
        return certificateStream;
    }


    public static void setCertificateResId(int certificateResId)
    {
        ADSLibAplication.certificateResId = certificateResId;
    }

    public static int getCertificateResId()
    {
        return certificateResId;
    }

}

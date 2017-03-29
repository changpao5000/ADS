package com.baiyigame.adslibrary.manager;

import android.content.Context;

import com.baiyigame.adslibrary.Utils.FileUtils;
import com.baiyigame.adslibrary.application.ADSLibAplication;

import java.io.InputStream;

/**
 * Configuration management class certificate
 *
 * singleton pattern
 *
 * Created by Administrator on 2017/3/23.
 */

public class CertificateManager
{
    private static CertificateManager instence = null;

    private CertificateManager (){}

    public static CertificateManager getInstence()
    {
        if (instence == null)
            instence = new CertificateManager();
        return instence;
    }

    /**
     * Configuration certificate
     * Resource ID of the incoming certificate
     * @param mContext
     * @param certificateResId
     */
     public static void ConfigCertificate(Context mContext, int certificateResId)
     {

        saveCertificate(mContext,certificateResId);
    }

    /**
     * Configuration certificate
     * The incoming certificate of flow
     * @param mContext
     * @param certificateInputStream
     */
    public static void ConfigCertificate(Context mContext, InputStream certificateInputStream)
    {
        saveCertificate(mContext,certificateInputStream);
    }

    /**
     * save
     * @param mContext
     * @param certificateId
     */
    private static void saveCertificate(Context mContext,int certificateId)
    {
        ADSLibAplication.setCertificateResId(certificateId);
        InputStream inputStream = mContext.getResources().openRawResource(
                certificateId);
        ADSLibAplication.setCertificate(inputStream);
        FileUtils.saveCertificate(inputStream);
    }
    /**
     * save
     * @param mContext
     * @param certificateStream
     */
    private static void saveCertificate(Context mContext,InputStream certificateStream)
    {
        ADSLibAplication.setCertificate(certificateStream);
        FileUtils.saveCertificate(certificateStream);
    }
}

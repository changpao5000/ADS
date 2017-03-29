package com.baiyigame.adslibrary.net;


import android.content.Context;

import com.baiyigame.adslibrary.Utils.FileUtils;
import com.baiyigame.adslibrary.application.ADSLibAplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Verification certificate
 * Created by Administrator on 2017/3/3.
 */

public class ADSSSLSocketFactory
{
    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory(Context cxt) throws javax.security.cert.CertificateException, IOException, KeyStoreException,
            NoSuchAlgorithmException, KeyManagementException, CertificateException
    {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        int certificateResId =  ADSLibAplication.getCertificateResId();
        InputStream inputStream = null;
        if (certificateResId == 0)
        {
            inputStream = ADSLibAplication.getCertificateStream();
            if (inputStream == null)
            {
                inputStream = FileUtils.readCertificate();
            }
        }else
        {
            inputStream = cxt.getResources().openRawResource(certificateResId);
        }
        java.security.cert.Certificate ca = cf.generateCertificate(inputStream);

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        keystore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keystore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }

    public static void trustAllHosts()
    {
        // Create a trust manager that does not validate certificate chains
        // Android use X509 cert
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException
            {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException
            {

            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers()
            {
                return new java.security.cert.X509Certificate[] {};
            }
        } };

        // Install the all-trusting trust manager
        try
        {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier()
    {
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    };

}

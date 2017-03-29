package com.baiyigame.adslibrary.net;


import android.content.Context;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.FileUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.base.BaseHttp;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2017/2/28.
 */

public class HttpUtils extends BaseHttp implements Runnable
{

    private IHttpFinishedListener listener = null;
    private Context mContext = null;

    public HttpUtils(Context context)
    {
        this.mContext = context;
    }

    public void start()
    {

       if (!NetWorkUtils.isNetworkAvailable(mContext))
       {
           if (listener != null)
           {
               listener.onError(mContext.getString(R.string.no_net),-1);
           }
           return ;
       }
        new Thread(this).start();
        return ;
    }


    public void setListener(IHttpFinishedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void run()
    {
        StringBuffer sb = new StringBuffer();
        URL url = null;
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        BufferedReader in = null;
        OutputStream outputStream = null;
        int code  = 0;
        try {
            url = new URL(getUrl());

            //The key code
            //ignore https certificate validation |Ignore the HTTPS certificate authentication
            if (url.getProtocol().toUpperCase().equals("HTTPS"))
            {
                HttpsURLConnection https = (HttpsURLConnection) url
                        .openConnection();
                https.setSSLSocketFactory(ADSSSLSocketFactory.getSSLSocketFactory(mContext));
                https.setHostnameVerifier(ADSSSLSocketFactory.DO_NOT_VERIFY);
                ADSSSLSocketFactory.trustAllHosts();
                connection = https;
            }
            else
            {
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setConnectTimeout(300000);
            connection.setReadTimeout(300000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(getMothed()==null?BaseHttp.Methed_Post:getMothed());
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-DataType",BaseHttp.Application_Json);
            connection.addRequestProperty("content-type", getContentType()==null?BaseHttp.Application_Json:getContentType());
            connection.setRequestProperty("Accept", BaseHttp.Application_Json);

            connection.connect();
           if (getBodyData() != null)
           {
               out = new DataOutputStream(
                       connection.getOutputStream());
               out.writeBytes(getBodyData());
               out.flush();
           }
            code  = connection.getResponseCode();
            if (code >= 200&& code <= 206)
            {
                if (getUrl().endsWith(".mp4")||getUrl().endsWith(".3gp"))
                {
                    InputStream im = connection.getInputStream();
                    File file = new File(FileUtils.getSDADSRoot()+"/"+Utils.getImgFileName(getUrl()));
                    if (file.exists())
                    {
                        file.delete();
                    }
                        outputStream = new FileOutputStream(file);
                        int len = 0;
                        byte[] bytes = new byte[1024*4];
                        while ((len = im.read(bytes))!= -1)
                        {
                            outputStream.write(bytes,0,len);
                        }
                        outputStream.flush();
                        if (listener != null)
                        {
                            listener.onSuccess(file.getAbsolutePath());
                        }

                }
                else if (getUrl().endsWith(".png")||getUrl().endsWith(".jpg"))
                {
                    InputStream im = connection.getInputStream();
                    File file = new File(FileUtils.getSDADSRoot()+"/"+ Utils.getImgFileName(getUrl()));
                    if (file.exists())
                    {
                        file.delete();
                    }
                    outputStream = new FileOutputStream(file);
                    int len = 0;
                    byte[] bytes = new byte[1024*4];
                    while ((len = im.read(bytes))!= -1)
                    {
                        outputStream.write(bytes,0,len);
                    }
                    outputStream.flush();
                    if (listener != null)
                    {
                        listener.onSuccess(file.getAbsolutePath());
                    }
                }
                else
                {
                    in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()), 8*1024);
                    String line;
                    while ((line = in.readLine()) != null)
                    {
                        sb.append(line.trim());
                    }

                    if (listener != null)
                    {
                        listener.onSuccess(sb.toString());
                    }
                }
            }else
            {
                String msg = "";
                if (code>=100&&code<=102)
                {
                    msg = mContext.getString(R.string.request_accept);
                }else if (code >= 300 && code<=305)
                {
                    msg = mContext.getString(R.string.redirecting);
                }else if (code >= 400 && code<=415)
                {
                    msg = mContext.getString(R.string.client_request_error);
                }else if (code >= 500 && code<=505)
                {
                    msg = mContext.getString(R.string.servixe_error);
                }else
                {
                    msg = mContext.getString(R.string.unknow_error);
                }
                if (listener != null)
                {
                    listener.onError(msg,code);
                }
            }
        }
        catch (SocketTimeoutException e)
        {
            e.printStackTrace();
            if (listener != null)
            {
                listener.onError(e.getMessage(),code);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            if (listener != null)
            {
                listener.onError(e.getMessage(),code);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (listener != null)
            {
                listener.onError(e.getMessage(),code);
            }
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

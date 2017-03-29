package com.baiyigame.adslibrary.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.base.BaseHttp;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ImageLoader extends BaseHttp implements Runnable
{
    private IHttpFinishedListener listener ;

    private Context mContext = null;

    public ImageLoader(Context context)
    {
        this.mContext = context;
    }

    Bitmap bitmap = null;
    public void request()
    {
        StringBuffer sb = new StringBuffer();
        URL url = null;
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        BufferedReader in = null;
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
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setConnectTimeout(30000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(getMothed()==null? BaseHttp.Methed_Post:getMothed());
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
                if (getUrl().endsWith(".png")||getUrl().endsWith(".jpg"))
                {
                    InputStream im = connection.getInputStream();
                    byte[] bytes = toByteArray(im);
                    bitmap = ImageUtils.bytes2Bitmap(bytes);
                    handler.sendEmptyMessage(0);
                    bytes = null;
                }
            }else
            {
                String msg = "";
                if (code>=100&&code<=102)
                {
                    msg = mContext.getString(R.string.request_accept);
                }
                else if (code >= 300 && code<=305)
                {
                    msg = mContext.getString(R.string.redirecting);
                }
                else if (code >= 400 && code<=415)
                {
                    msg = mContext.getString(R.string.client_request_error);
                }
                else if (code >= 500 && code<=505)
                {
                    msg = mContext.getString(R.string.servixe_error);
                }
                else
                {
                    msg = mContext.getString(R.string.unknow_error);
                }
                if (listener != null)
                {
                    listener.onError(msg,code);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null)
            {
                listener.onError(e.getMessage(),code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null)
            {
                listener.onError(e.getMessage(),code);
            }
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if (listener != null)
                    {
                        listener.onSuccess(bitmap);
                    }

                    break;
            }
        }
    };
    public void setListener(IHttpFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
       request();
    }

    public void start() {

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

    public byte[] toByteArray(InputStream input)
            throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    public int copy(InputStream input, OutputStream output)
            throws IOException
    {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    public long copyLarge(InputStream input, OutputStream output)
            throws IOException
    {
        byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}

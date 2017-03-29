package com.baiyigame.adslibrary.manager;

import android.content.Context;
import android.content.res.Resources;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.FileUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.net.HttpUtils;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;
import com.baiyigame.adslibrary.port.IVideoPrepareComplete;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Administrator on 2017/3/8.
 */

public class VideoPlayerManager
{
    private String videoUrl;

    private IVideoPrepareComplete iVideoPrepareComplete;

    private static VideoPlayerManager instence = null;

    private Context mContext = null;

    private VideoPlayerManager(Context context)
    {
        this.mContext = context;
    }

    public static VideoPlayerManager getInstence(Context context)
    {
        if (instence == null)
        {
            instence = new VideoPlayerManager(context);
        }
        return instence;
    }


    public VideoPlayerManager play(String viodeoUrl)
    {
        if (Utils.isStringEmpty(viodeoUrl))
        {
            if (iVideoPrepareComplete != null)
            {
                iVideoPrepareComplete.onError(Resources.getSystem().getString(R.string.url_is_null), -1);
            }
            return getInstence(mContext);
        }

        this.videoUrl = viodeoUrl;
        final File file = new File(FileUtils.getSDADSRoot() + "/" + Utils.getImgFileName(viodeoUrl));
        if (file.exists()) {
            if (iVideoPrepareComplete != null)
            {
                iVideoPrepareComplete.onComplete(file.getAbsoluteFile().toString());
            }
            return getInstence(mContext);
        }
        else
        {
            //viodeoUrl = "https://117.34.95.103/adp-web/upload/web/adv/material/video/60b4b63e44704c3f9fa081aca54fe6eb.mp4";
            HttpUtils utils = new HttpUtils(mContext);
            utils.setUrl(viodeoUrl);
            utils.setListener(new IHttpFinishedListener()
            {
                @Override
                public void onSuccess(Object result)
                {
                    if (iVideoPrepareComplete != null)
                    {
                        iVideoPrepareComplete.onComplete(file.getAbsoluteFile().toString());
                    }
                }

                @Override
                public void onError(String errorMsg, int resposeCode)
                {
                    if (iVideoPrepareComplete != null)
                    {
                        iVideoPrepareComplete.onError(errorMsg, resposeCode);
                    }
                }
            });
            utils.start();
            return getInstence(mContext);
        }
    }

    public void delete() {
        File file = new File(FileUtils.getSDADSRoot());
        File[] files = file.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                if (pathname.exists())
                {
                    if (pathname.getAbsolutePath().endsWith(".mp4"))
                    {
                        return true;
                    }
                }
                return false;
            }
        });

        if (files == null)
        {
            return;
        }

        for (File f : files)
        {
            if (f.exists())
            {
                f.delete();
            }
        }
    }

    public void setiVideoPrepareComplete(IVideoPrepareComplete iVideoPrepareComplete)
    {
        this.iVideoPrepareComplete = iVideoPrepareComplete;
    }
}

package com.baiyigame.adslibrary.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.baiyigame.adslibrary.Utils.FileUtils;
import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.cache.BitmapCacheUtils;
import com.baiyigame.adslibrary.net.ImageLoader;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;
import com.baiyigame.adslibrary.port.ImageLoaderListener;

import java.io.File;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ImageManager {

    private ImageLoaderListener imageLoaderListener = null;

    private Bitmap bitmap = null;

    private static ImageManager instence = null;

    private Context mContext = null;

    public ImageManager(Context context)
    {
        this.mContext = context ;
    }

//    public static ImageManager getInstence(Context context) {
//        if (instence == null)
//            instence = new ImageManager(context);
//        return instence;
//    }

    public void request(String url, ImageView img) {
        Bitmap bitmap = BitmapCacheUtils.getInstance().get(Utils.getImgFileName(url));
        if (bitmap != null)
        {
            if (imageLoaderListener != null) {
                imageLoaderListener.onSuccess(img, bitmap, url);
            }
        }
        else
        {
            File file = new File(FileUtils.getSDADSRoot() + "/" + Utils.getImgFileName(url));
            if (file.exists()) {
                Bitmap bm = ImageUtils.getBitmapToSD(file);
                if (bm != null) {
                    if (imageLoaderListener != null) {
                        imageLoaderListener.onSuccess(img, bm, url);
                    }
                    BitmapCacheUtils.getInstance().put(Utils.getImgFileName(url), bm);
                }
                else
                {
                    loadImg(url, img, file);
                }
            }
            else {
                loadImg(url, img, file);
            }
        }
    }

    private void loadImg(final String imgUrl, final ImageView mImg, final File file)
    {
//
        ImageLoader img = new ImageLoader(mContext);
        img.setUrl(imgUrl);
        img.setListener(new IHttpFinishedListener()
        {
            @Override
            public void onSuccess(Object result)
            {
                Bitmap bitmap = (Bitmap) result;
                if (imageLoaderListener != null)
                {
                    imageLoaderListener.onSuccess(mImg, bitmap, imgUrl);
                }
                BitmapCacheUtils.getInstance().put(Utils.getImgFileName(imgUrl), bitmap);
                //File file = new File(FileUtils.getSDADSRoot() + "/" + Utils.getImgFileName(imgUrl));
                if (file.exists())
                {
                    file.delete();
                }
                ImageUtils.saveSD(file,bitmap);
            }

            @Override
            public void onError(String errorMsg, int resposeCode)
            {
                if (imageLoaderListener != null)
                {
                    imageLoaderListener.onError(mImg,imgUrl,errorMsg);
                }

            }
        });
        img.start();
    }

    public void setImageLoaderListener(ImageLoaderListener imageLoaderListener)
    {
        this.imageLoaderListener = imageLoaderListener;
    }
}
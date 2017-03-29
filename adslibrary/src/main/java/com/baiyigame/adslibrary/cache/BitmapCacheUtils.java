package com.baiyigame.adslibrary.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Cache utility class
 * Created by Administrator on 2017/3/9.
 */

public class BitmapCacheUtils
{
    private int maxSize =
            (int) (Runtime.getRuntime().freeMemory() / 2)<=0?4*1024:(int)
                    (Runtime.getRuntime().freeMemory() / 2);// 获取可用内存的一半做为缓存容器大小

    private LruCache<String, Bitmap> mLruCaches = new LruCache<String, Bitmap>(maxSize);

    private static BitmapCacheUtils instance = null;
    private BitmapCacheUtils(){}
    public static BitmapCacheUtils getInstance()
    {
        if (instance == null)
        {
            instance = new BitmapCacheUtils();
        }
        return instance;
    }

    public void put(String key,Bitmap bitmap)
    {
        if (key == null)
        {
            throw new NullPointerException("key == null");
        }
        if (bitmap == null)
        {
            throw new NullPointerException("bitmap == null");
        }
        mLruCaches.put(key,bitmap);
    }

    public Bitmap get(String key)
    {
        if (key == null)
        {
            throw new NullPointerException("key == null");
        }
        return mLruCaches.get(key);
    }
}


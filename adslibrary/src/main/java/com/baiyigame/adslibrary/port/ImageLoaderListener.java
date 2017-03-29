package com.baiyigame.adslibrary.port;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface ImageLoaderListener
{
    void onSuccess(View view, Bitmap bitmap, String path);

    void onError(View view, String path, String errorMsg);

    void Loading(View view, String path);
}
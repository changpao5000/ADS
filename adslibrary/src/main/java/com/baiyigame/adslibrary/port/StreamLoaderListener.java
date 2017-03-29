package com.baiyigame.adslibrary.port;

import android.view.View;

/**
 * Information flow whether advertising success
 * Created by Administrator on 2017/3/20.
 */

public interface StreamLoaderListener
{
    void onSuccess(View view);
    void onFailure(String errorMsg, int resposeCode);
}

package com.baiyigame.adslibrary.port;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface IVideoPrepareComplete
{
    void onComplete(String path);

    void onError(String errorMsg, int resposeCode);
}

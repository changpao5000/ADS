package com.baiyigame.adslibrary.port;

/**
 * Http request finished
 * Created by Administrator on 2017/2/28.
 */

public interface IHttpFinishedListener
{
    void onSuccess(Object result);
    void onError(String errorMsg, int resposeCode);
}

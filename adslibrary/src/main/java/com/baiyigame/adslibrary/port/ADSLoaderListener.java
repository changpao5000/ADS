package com.baiyigame.adslibrary.port;

/**
 * Judgment is wifi or 3 g network,
 * the embodiment of the users here, wifi can recommend downloading or streaming.
 * Created by Administrator on 2017/3/16.
 */

public interface ADSLoaderListener
{
    void onSuccess();
    void onFailure(String errorMsg, int resposeCode);
}

package com.baiyigame.adslibrary.port;


import com.baiyigame.adslibrary.model.MeterailModel;

/**
 * Created by Administrator on 2017/3/2.
 */

public interface ILoadLisener
{
    /**
     * The second parameter is the advertising information
     * @param meterailModel
     * @param AdvertyInfoModel
     */
    void onSuccess(MeterailModel meterailModel, Object AdvertyInfoModel);

    void onError(String errorMsg, int resposeCode);
}

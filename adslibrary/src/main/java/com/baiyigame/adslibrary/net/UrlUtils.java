package com.baiyigame.adslibrary.net;


import com.baiyigame.adslibrary.config.ADConfig;

/**
 * Created by Administrator on 2017/3/3.
 */

public class UrlUtils
{
    /**
     * Init url
     * get token and modefy time.
     * @return
     */
    public static String getInitUrl()
    {
        String url = ADConfig.ROOT_Url + "/v1.2/app/unit/token";
        return url;
    }

    /**
     * get ads position,size and style and so on.
     * @return
     */
    public static String getAdsStyleUrl()
    {
        String url = ADConfig.ROOT_Url + "/v1.2/app/unit/key";
        return url;
    }

    /**
     * get Advertising material,This is to display on the interface.
     * @return
     */
    public static String getMeterailUrl()
    {
        String url = ADConfig.ROOT_Url + "/v1.2/app/adv/mat";
        return url;
    }
}

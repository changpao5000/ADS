package com.baiyigame.adslibrary.net;


import com.baiyi.gson.BYGson;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Administrator on 2017/3/1.
 */

public class JSONBase
{
    public static JSONArray toJSONArray(String result)
    {
        try
        {
            return new JSONArray("["+result+"]");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Parses the Json data into the corresponding mapping object
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static  <T> T ToJavaBean(String jsonData, Class<T> type)
    {
        BYGson gson = new BYGson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

}

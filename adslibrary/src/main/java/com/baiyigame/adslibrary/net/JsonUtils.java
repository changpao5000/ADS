package com.baiyigame.adslibrary.net;

import android.content.Context;

import com.baiyigame.adslibrary.Utils.Base64;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.model.TokenModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/2/28.
 */

public class JsonUtils
{

    static String[] strs;

    public static String[] getSecretKey(String secretkey)
    {
        String str = null;
        if (str == null)
        {
            str = Utils.byteToString(Base64.decode(secretkey));

            if (strs == null)
            {
                strs = str.split("_");
            }

            return strs;
        }
        return null;
    }

    public static String getTokenJson(String secretkey, Context context)
    {
        JSONObject object = new JSONObject();
        try
        {
            strs = getSecretKey(secretkey);
            object.accumulate("app_key", strs[1]);
            object.accumulate("apr_key", strs[0]);
            object.accumulate("device_id", Utils.getUUID(context));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static String getADSMeterailJson(TokenModel model, String adskey)
    {
        JSONObject object = new JSONObject();
        try
        {
            object.accumulate("unit_key",adskey);
            object.accumulate("token",model.getData().getToken());
            return object.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
       return null;
    }
}

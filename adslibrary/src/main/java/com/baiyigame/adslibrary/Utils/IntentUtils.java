package com.baiyigame.adslibrary.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.baiyigame.adslibrary.activities.CoopenActivity;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.OpenAdvertyModel;


/**
 * Created by Administrator on 2017/3/7.
 */

public class IntentUtils
{
    public static void goWeb(Context context ,String url)
    {
        if (Utils.isStringEmpty(url))
        {
            return;
        }
       try
       {
           Uri uri = Uri.parse(url);
           Intent intent = new  Intent(Intent.ACTION_VIEW, uri);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
       }
       catch (ActivityNotFoundException e)
       {
           e.printStackTrace();
           throw new ActivityNotFoundException(e.getMessage());
       }
    }

    public static boolean goCoopenADs(Context context, MeterailModel meterailModel, OpenAdvertyModel openAdvertyModel)
    {
        try
        {
            Intent intent = new Intent();
            intent.setClass(context, CoopenActivity.class);
            intent.putExtra("meterailModel",meterailModel);
            intent.putExtra("openAdvertyModel",openAdvertyModel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}

package com.baiyigame.adslibrary.Utils;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * Created by Administrator on 2017/3/1.
 */

public class ScreenUtils
{
    public static int getScreeWidth(Context context)
    {
        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }
    public static int getScreeHeight(Context context)
    {
        DisplayMetrics dm = context.getApplicationContext().getResources()
               .getDisplayMetrics();
        int height = dm.heightPixels;
        return height;
    }
}

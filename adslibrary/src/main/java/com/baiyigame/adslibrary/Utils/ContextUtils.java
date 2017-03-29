package com.baiyigame.adslibrary.Utils;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ContextUtils
{
    public static LayoutInflater getlayoutInfater(Context context)
    {
        return (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }
}

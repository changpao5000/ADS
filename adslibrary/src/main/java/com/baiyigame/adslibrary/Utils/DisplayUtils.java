package com.baiyigame.adslibrary.Utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/3/6.
 */

public class DisplayUtils
{
    /**
     *
     *Converts the px value to dip or dp value, ensure the size remains the same
     * @param pxValue
     *            (DisplayMetrics class attribute density)
     * @return
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Will dip or dp value converted to the px, ensure the size remains the same
     *
     * @param dipValue
     *            (DisplayMetrics class attribute density)
     * @return
     */
    public static int dip2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * Will px value converted to sp, guarantee the text size remains the same
     *
     * @param pxValue
     * @param fontScale
     *            (scaledDensity DisplayMetrics class attribute)
     * @return
     */
    public static int px2sp(Context context, float pxValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * The sp value converted to the px, ensure the text size remains the same
     *
     * @param spValue
     * @param fontScale
     *            (scaledDensity DisplayMetrics class attribute)
     * @return
     */
    public static int sp2px(Context context, float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

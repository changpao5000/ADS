package com.baiyigame.adslibrary.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/1.
 */

public class DateUtils
{
    public static String getTimeHHMM(Long time)
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date data = new Date(time);
        return timeFormat.format(data);
    }

    public static String getTimeMMDD(Long time)
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd");
        Date data = new Date(time);
        return timeFormat.format(data);
    }

    public static String getTimeYMDHHMM(Long time)
    {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 2015-09-08 10:16:47
        return format.format(date);
    }

    public static String getTimeYMD(Long time)
    {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getTimeYMD2(Long time)
    {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        return format.format(date);
    }

    public static String getTimeYMD3(Long time)
    {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        return format.format(date);
    }

    public static String getTimeYMD4(Long time)
    {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm");
        return format.format(date);
    }

    /**
     *
     * @param time
     * @return
     */
    public static long getTimeSecond(String time)
    {
        long seconds = 0;
        if (Utils.isStringEmpty(time))
        {
            return seconds;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            seconds = format.parse(time).getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return seconds;
    }
    public static long getTimeSecond2(String time)
    {
        long seconds = 0;
        if (Utils.isStringEmpty(time))
        {
            return seconds;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            seconds = format.parse(time).getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return seconds;
    }

    public static String getDZTime(String time)
    {
        if (Utils.isStringEmpty(time))
        { //
            return "";
        }

        //
        long seconds = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            seconds = format.parse(time).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return time;
        }

        return getTimeYMD4(seconds);
    }


    public static String getCollectTime(String time)
    {
        if (Utils.isStringEmpty(time))
        {
            return "";
        }

        if (time.contains("T")){
            time = time.replace("T"," ");
        }

        long seconds = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            seconds = format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }

        return getTimeYMD3(seconds);
    }

    public static String getCurrentTime(long date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(new Date(date));
        return str;
    }
    public static String getCurrentTime2(long date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = format.format(new Date(date));
        return str;
    }
    public static String getCurrentTime3(long date)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String str = format.format(new Date(date));
        return str;
    }

    /**
     * Determine whether within two minutes
     * @param locTime
     * @return
     */
    public static boolean isSecondMinite(long locTime)
    {
        long currentTime = System.currentTimeMillis();
        long temp = currentTime - locTime;
        if ((temp / 1000) > 120)
        {
            return true;
        }
        return false;
    }

    /**
     *Determine whether in one day
     * @param locTime
     * @return
     */
    public static boolean isDay(long locTime)
    {
        Date locDate = new Date(locTime);
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        int currentDay = curDate.getDate();
        int locDay = locDate.getDate();
        if (currentDay != locDay) {
            return true;
        }
        return false;
    }
}

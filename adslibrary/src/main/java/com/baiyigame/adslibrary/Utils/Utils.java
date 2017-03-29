package com.baiyigame.adslibrary.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.baiyigame.adslibrary.config.Define;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2017/2/28.
 */

public class Utils{

    public static int getAppVersion(Context context)
    {
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 1;
    }

    public static String bytesToHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
            {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    public static String byteToString(byte[] bytes)
    {
        if (bytes != null)
        {
            return new String(bytes,0,bytes.length);
        }
        return null;
    }

    public static String getUUID(Context context)
    {

        PreferenceUtils pref = PreferenceUtils.getInstence(context);
        String uuid = pref.Get(Define.UUID,"");
        if (Utils.isStringEmpty(uuid))
        {
            uuid = UUID.randomUUID().toString();
            pref.Set(Define.UUID,uuid);
        }
        return uuid;
    }

    public static String getDeviceId(Context context)
    {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return  szImei;
    }

    public static String[] split(String original, String regex)
    {
        int startIndex = 0;
        Vector v = new Vector();
        String[] str = null;
        int index = 0;
        startIndex = original.indexOf(regex);
        while (startIndex < original.length() && startIndex != -1)
        {
            String temp = original.substring(index, startIndex);
            System.out.println(" " + startIndex);
            v.addElement(temp);
            index = startIndex + regex.length();
            startIndex = original.indexOf(regex, startIndex + regex.length());
        }
        v.addElement(original.substring(index + 1 - regex.length()));
        str = new String[v.size()];
        for (int i = 0; i < v.size(); i++)
        {
            str[i] = (String) v.elementAt(i);
        }
        return str;
    }

    public static boolean isStringEmpty(String str)
    {
        if (TextUtils.isEmpty(str))
        {
            return true;
        }
        else if ("".equals(str))
        {
            return true;
        }
        else if (str == null)
        {
            return true;
        }
        return false;
    }

    /**
     * image file name
     * @param imagUrl
     * @return
     */
    public static String getImgFileName(String imagUrl)
    {
        String[] arr= Utils.split(imagUrl,"/");
        return arr[arr.length-1];
    }

    public static <T>  boolean isListEmpty(List<T> list)
    {
        if (list == null)
        {
            return true;
        }
        if (list.size()<=0)
        {
            return true;
        }
        return false;
    }
}

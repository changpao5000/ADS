package com.baiyigame.adslibrary.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/1.
 */

public class PreferenceUtils
{

    private Context mContext;

    private SharedPreferences sp = null;
    //private SharedPreferences.Editor editor = null;

    private static PreferenceUtils instence;

    private boolean isInit = false;

    public static PreferenceUtils getInstence(Context context)
    {
        if (instence == null)
        {
            instence = new PreferenceUtils(context);
        }
        return instence;
    }

    private PreferenceUtils(Context context)
    {
        this.mContext = context;
        if (sp == null)
        {
            sp = mContext.getSharedPreferences("preference", Context.MODE_PRIVATE);
        }
        //init();
    }

    public boolean Get(String key, boolean defaultValue)
    {
        return sp.getBoolean(key, defaultValue);
    }
    public String Get(String key, String defaultValue)
    {
        return sp.getString(key, defaultValue);
    }
    public int Get(String key, int defaultValue)
    {
        return sp.getInt(key, defaultValue);
    }
    public String getStringSet(String key, int index)
    {
        Set<String> set = sp.getStringSet(key, null);
        List<String> list = new ArrayList<>();
        if (set != null)
        {
            Iterator<String> it = set.iterator();
            while (it.hasNext())
            {
               list.add(it.next());
            }
            return list.get(index);
        }
        return null;
    }
    public void Set(String key, String value)
    {
        sp.edit().putString(key, value).commit();
    }
    public void Set(String key, int value)
    {
        sp.edit().putInt(key, value).commit();
    }
    public void Set(String key, boolean value)
    {
        sp.edit().putBoolean(key, value).commit();
    }
    public void Set(String key, Set<String> set)
    {
        sp.edit().putStringSet(key,set).commit();
    }

    public void remove(String key)
    {
        sp.edit().remove(key).commit();
    }

    public void clear()
    {
        sp.edit().clear().commit();
    }

    private void init()
    {
        if (isInit)
            return;

        try {

            Field field;
            // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
            field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
            // 获取mBase变量
            Object obj = field.get(mContext);
            // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            field = obj.getClass().getDeclaredField("mPreferencesDir");
            field.setAccessible(true);
            // 创建自定义路径
            File file = new File(FileUtils.getSDPath());
            // 修改mPreferencesDir变量的值
            field.set(obj, file);

            if (sp == null)
            {
                sp = mContext.getSharedPreferences("preference", Context.MODE_PRIVATE);
            }

            isInit = true;

        }
        catch (NoSuchFieldException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

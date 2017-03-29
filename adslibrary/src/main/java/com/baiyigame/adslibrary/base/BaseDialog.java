package com.baiyigame.adslibrary.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.baiyigame.adslibrary.manager.VideoPlayerManager;


/**
 * The base class for the pop-up Dialog box Dialog
 * Created by Administrator on 2017/3/3.
 */

public class BaseDialog extends Dialog
{

    public BaseDialog(Context context)
    {
        this(context,0);
    }

    public BaseDialog(final Context context, int themeResId)
    {
        super(context, themeResId);
        setOwnerActivity((Activity) context);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                VideoPlayerManager.getInstence(context).delete();
            }
        });
    }
}

package com.baiyigame.adslibrary.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/3/24.
 */

public class BaseView extends RelativeLayout
{
    private static final int Show_Close_View = 0;

    private ImageView imageView;

    public BaseView(Context context)
    {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case Show_Close_View:
                    imageView.setVisibility(VISIBLE);
                    break;
            }
        }
    };
    public void delayShowClose(ImageView mImg, final int delayTime)
    {
        if (delayTime <= 0)
        {
            return;
        }
        mImg.setVisibility(GONE);
        this.imageView = mImg;
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(delayTime*1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(Show_Close_View);
            }
        }.start();
    }
}

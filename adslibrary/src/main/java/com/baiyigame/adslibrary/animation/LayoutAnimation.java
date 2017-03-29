package com.baiyigame.adslibrary.animation;

import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Click on the animation (Only change the transparency)
 * Created by Administrator on 2017/3/23.
 */

public class LayoutAnimation
{

    private static LayoutAnimation instence = null;
    private  View view = null;

    private LayoutAnimation()
    {}

    public static LayoutAnimation getInstence()
    {
        if (instence == null)
        {
            instence = new LayoutAnimation();
        }
        return instence;
    }

    public LayoutAnimation clickAnim(View view)
    {
        this.view = view ;
        view.setAlpha(0.8f);
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }.start();

        return getInstence();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    view.setAlpha(1f);
                    break;
            }
        }
    };
}

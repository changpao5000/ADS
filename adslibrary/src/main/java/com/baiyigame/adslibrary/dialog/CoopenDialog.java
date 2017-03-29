package com.baiyigame.adslibrary.dialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.baiyigame.adslibrary.manager.VideoPlayerManager;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.OpenAdvertyModel;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.view.CoopenADSView;

/**
 * Opening the advertising display box
 * Temporary don't have to
 * Created by Administrator on 2017/3/13.
 */

public class CoopenDialog implements OnCloseBtnClickListner
{

    private OpenAdvertyModel openAdvertyModel = null;
    private MeterailModel meterailModel = null;
    private WindowManager wm = null;
    private CoopenADSView view = null;
    private static final int Delay_Close_WM = 0;

    private Context mContext = null;

    public CoopenDialog(Context context)

    {
        this.mContext = context ;
    }

    public void Init()
    {
       if (wm == null)
       {
           try {
               wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
               WindowManager.LayoutParams para = new WindowManager.LayoutParams();
               para.height = ViewGroup.LayoutParams.MATCH_PARENT;
               para.width = ViewGroup.LayoutParams.MATCH_PARENT;
               para.format = PixelFormat.RGBA_8888;
//        para.x = Gravity.CENTER;
//        para.y = Gravity.CENTER;
//        para.gravity = Gravity.CENTER;

               para.verticalWeight = 1;

               para.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN |
                       WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

                para.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

               view = new CoopenADSView(mContext);
               view.setOpenAdvertyModel(openAdvertyModel);
               view.setMeterailModel(meterailModel);
               view.init();
               view.setListner(this);

               wm.addView(view, para);
           }
           catch (WindowManager.BadTokenException exception)
           {
               System.err.println(exception.getMessage());
           }
       }
        //autoClose();
    }

    private void autoClose()
    {
        int delayTime = openAdvertyModel.getData().getAot();
        if (delayTime > 0) {
            delayClose(delayTime);
        }
    }

    /**
     * Delay closing dialog
     *
     * @param delayTime
     */
    private void delayClose(final int delayTime)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(delayTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(Delay_Close_WM);
            }
        }.start();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case Delay_Close_WM:
                    onCloseClick();
                    break;
            }
        }
    };

    @Override
    public void onCloseClick()
    {
        if (view != null)
        {
            wm.removeView(view);
            view = null;
        }
        VideoPlayerManager.getInstence(mContext).delete();
    }

    public void setOpenAdvertyModel(OpenAdvertyModel openAdvertyModel)
    {
        this.openAdvertyModel = openAdvertyModel;
    }

    public void setMeterailModel(MeterailModel meterailModel)
    {
        this.meterailModel = meterailModel;
    }
}

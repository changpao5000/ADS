package com.baiyigame.adslibrary.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.manager.VideoPlayerManager;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.OpenAdvertyModel;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.view.CoopenADSView;

/**
 * Opening the advertising display the Activity
 * Created by Administrator on 2017/3/17.
 */

public class CoopenActivity extends AppCompatActivity implements OnCloseBtnClickListner
{
    private static final String TAG = CoopenActivity.class.getSimpleName();

    private static final int Delay_Close_WM = 0;
    private CoopenADSView view = null;

    private OpenAdvertyModel openAdvertyModel = null;
    private MeterailModel meterailModel = null;

    public static boolean isAutoClose = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Remove the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//Remove the information bar
        super.onCreate(savedInstanceState);

        View title = this.findViewById(android.R.id.title);
        if (title != null)
        {
            title.setVisibility(View.GONE);
        }

//        Window window = getWindow();
//        ColorDrawable drawable = new ColorDrawable();
//        drawable.setColor(Color.BLACK);
//        window.setBackgroundDrawable(drawable);

        openAdvertyModel = (OpenAdvertyModel) getIntent().getSerializableExtra("openAdvertyModel");
        meterailModel = (MeterailModel) getIntent().getSerializableExtra("meterailModel");

        view = new CoopenADSView(this);
        view.setOpenAdvertyModel(openAdvertyModel);
        view.setMeterailModel(meterailModel);
        view.init();
        view.setListner(this);

        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);

        setContentView(view, params);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);//需要添加的语句

        autoClose();

    }

    private void autoClose()
    {
        int delayTime = openAdvertyModel.getData().getAot();
        delayClose(delayTime);
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
                   if (delayTime < 10000)
                   {
                       Thread.sleep(30000);
                   }
                   else
                   {
                       Thread.sleep(delayTime*1000);
                   }
                } catch (InterruptedException e)
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
                    if (isAutoClose)
                    {
                        onCloseClick();
                        isAutoClose = true;
                    }
                    break;
            }
        }
    };
    @Override
    public void onCloseClick()
    {
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        VideoPlayerManager.getInstence(this).delete();
    }
}

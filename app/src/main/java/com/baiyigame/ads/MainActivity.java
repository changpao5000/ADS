package com.baiyigame.ads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baiyigame.adslibrary.config.ADConfig;
import com.baiyigame.adslibrary.manager.BannerAdsManager;
import com.baiyigame.adslibrary.manager.CoopenADSManager;
import com.baiyigame.adslibrary.manager.InfoFlowADSManager;
import com.baiyigame.adslibrary.manager.InsertAdsManager;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.StreamLoaderListener;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CoopenADSManager manager = new CoopenADSManager(this);
//        manager.show();
//        Log.e("ADS------","开屏广告执行 ！");

        setContentView(R.layout.activity_main);
        layout = (LinearLayout) findViewById(R.id.stream_add_view);
    }
    public void chaYe(View view) {
        InsertAdsManager im = new InsertAdsManager(this);
        im.setUnityKey(ADConfig.Unity_Insert_Key);
        im.show();
        im.setLoaderListener(new ADSLoaderListener() {
            @Override
            public void onSuccess() {
                Log.e("MainActivity ", "插页广告显示成功");
                h.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(String errorMsg, int resposeCode) {
                Log.e("MainActivity ", "插页广告显示失败");
                Log.e("MainActivity ", errorMsg);
            }
        });
    }
private View view;
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    layout.removeAllViews();
                    layout.addView(view);
                    break;
            }
        }
    };

    public void banner(View view) {
        BannerAdsManager manager = new BannerAdsManager(this);
        manager.setUnityKey(ADConfig.Unity_Streamer_Key);
        manager.setLoop(false);
        manager.show();
        manager.setLoaderListener(new ADSLoaderListener() {
            @Override
            public void onSuccess() {
                Log.e("MainActivity ", "Banner 不轮播广告显示成功");
                h.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(String errorMsg, int resposeCode) {
                Log.e("MainActivity ", "Banner 不轮播广告显示失败");
            }
        });
    }

    public void bannerLooper(View view) {
        BannerAdsManager manager = new BannerAdsManager(this);
        manager.setUnityKey(ADConfig.Unity_Streamer_Key);
        manager.setLoop(true);
        manager.show();
        manager.setLoaderListener(new ADSLoaderListener() {
            @Override
            public void onSuccess() {
                Log.e("MainActivity ", "Banner 轮播广告显示成功");
                h.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(String errorMsg, int resposeCode) {
                Log.e("MainActivity ", "Banner 轮播广告显示失败");
            }
        });
    }

    /**
     * 开屏
     *
     * @param view
     */
    public void CoopenADS(View view) {
        CoopenADSManager manager = CoopenADSManager.getInstence(this);
        manager.setUnityKey(ADConfig.Unity_Coopen_Key);
        manager.show();
        manager.setLoaderListener(new ADSLoaderListener() {
            @Override
            public void onSuccess() {
                Log.e("MainActivity ", "开屏广告显示成功");
                h.sendEmptyMessage(0);
            }
            @Override
            public void onFailure(String errorMsg, int resposeCode) {
                Log.e("MainActivity ", "开屏广告显示失败");
            }
        });
    }

    /**
     * 信息流
     *
     * @param view
     */
    public void informationFlow(View view) {
        InfoFlowADSManager manager = new InfoFlowADSManager(this);
        manager.setUnityKey(ADConfig.Unity_Information_Flow_Key);
        manager.show();
        manager.setLoaderListener(new StreamLoaderListener() {
            @Override
            public void onSuccess(View view) {
                MainActivity.this.view = view;
               if (MainActivity.this.view != null)
               {
                   Log.e("MainActivity ", "信息流广告显示成功");
                   h.sendEmptyMessage(1);
               }
            }
            @Override
            public void onFailure(String errorMsg, int resposeCode) {
                Log.e("MainActivity ", "信息流广告显示失败");
            }
        });
    }
}

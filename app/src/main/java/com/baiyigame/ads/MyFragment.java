package com.baiyigame.ads;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baiyigame.adslibrary.Utils.PreferenceUtils;
import com.baiyigame.adslibrary.config.ADConfig;
import com.baiyigame.adslibrary.config.Define;
import com.baiyigame.adslibrary.dialog.InsertDialog;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.TokenModel;
import com.baiyigame.adslibrary.net.HttpUtils;
import com.baiyigame.adslibrary.net.JSONBase;
import com.baiyigame.adslibrary.net.JsonUtils;
import com.baiyigame.adslibrary.net.UrlUtils;
import com.baiyigame.adslibrary.port.IHttpFinishedListener;

import java.util.Random;

/**
 * Created by Administrator on 2017/3/7.
 */

public class MyFragment extends android.support.v4.app.Fragment {
       @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View dialog = inflater.inflate(R.layout.img, container, false);
        RelativeLayout dialog = new RelativeLayout(getActivity());
        int[] colors = new int[]
                {R.color.colorAccent, R.color.colorPrimary,R.color.colorAccent, R.color.colorPrimaryDark};
        Random random = new Random();
        dialog.setBackgroundResource(colors[random.nextInt(4)]);
        dialod();
        return dialog;
    }

    private void dialod() {
        testInsertADS(ADConfig.Unity_Insert_Key);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    insertDialog = new InsertDialog(getActivity());
                    insertDialog.setBannerInfoAdvertyModel(bannerInfoAdvertyModel);
                    insertDialog.setMeterailModel(meterailModel);
                    insertDialog.init();
                    insertDialog.show();
                    break;
            }
        }
    };
    InsertDialog insertDialog = null;
    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private MeterailModel meterailModel = null;
    private void testMeterail(TokenModel model, final String AdsKey) {
        HttpUtils utils = new HttpUtils(getActivity());
        utils.setUrl(UrlUtils.getMeterailUrl());
        utils.setBodyData(JsonUtils.getADSMeterailJson(model, AdsKey));
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {
                meterailModel = JSONBase.ToJavaBean(result.toString(), MeterailModel.class);
                meterailModel.save(meterailModel, AdsKey + Define.Unity_ADS_Meterail);
                //meterailModel = (MeterailModel) meterailModel.open(AdsKey + Define.Unity_ADS_Meterail);

                handler.sendEmptyMessage(0);
            }

            @Override
            public void onError(String errorMsg, int resposeCode) {
                Log.e("Response error:", errorMsg + ":" + resposeCode);
            }
        });
        utils.start();
    }


    private void testADSInfo(final TokenModel model, final String AdsKey) {

        HttpUtils utils = new HttpUtils(getActivity());
        utils.setUrl(UrlUtils.getAdsStyleUrl());
        utils.setBodyData(JsonUtils.getADSMeterailJson(model, AdsKey));
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {
                //Log.e("Response-testADSInfo:", result.toString());
                bannerInfoAdvertyModel = JSONBase.ToJavaBean(result.toString(), BannerInfoAdvertyModel.class);
                bannerInfoAdvertyModel.save(bannerInfoAdvertyModel, AdsKey + Define.Unity_ADS_Key);
                testMeterail(model, AdsKey);
            }

            @Override
            public void onError(String errorMsg, int resposeCode) {
                Log.e("Response error:", errorMsg + ":" + resposeCode);
            }
        });
        utils.start();
    }

    private void testInsertADS(final String AdsKey) {
        String secretKey = PreferenceUtils.getInstence(getActivity()).Get(ADConfig.Secret_key,"");
        HttpUtils utils = new HttpUtils(getActivity());
        utils.setUrl(UrlUtils.getInitUrl());
        utils.setBodyData(JsonUtils.getTokenJson(secretKey,getActivity()));
        utils.setListener(new IHttpFinishedListener() {
            @Override
            public void onSuccess(Object result) {

                TokenModel model = JSONBase.ToJavaBean(result.toString(), TokenModel.class);
                //
                TokenModel md = (TokenModel) model.open(AdsKey + Define.TOKEN);
                if (md == null) {
                    model.save(model, AdsKey + Define.TOKEN);
                    testADSInfo(model, AdsKey);
                } else {
                    if (md.getData().getModify_time() == model.getData().getModify_time()) {
                        bannerInfoAdvertyModel = new BannerInfoAdvertyModel();
                        bannerInfoAdvertyModel = (BannerInfoAdvertyModel) bannerInfoAdvertyModel.open(AdsKey + Define.Unity_ADS_Key);

                        if (bannerInfoAdvertyModel == null) {
                            testADSInfo(model, AdsKey);
                        } else {
                            Log.e("Response-testADSInfo:", bannerInfoAdvertyModel.toString());
                            testMeterail(model, AdsKey);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg, int resposeCode) {
                Log.e("Response error:", errorMsg + ":" + resposeCode);
            }
        });
        utils.start();
    }

}

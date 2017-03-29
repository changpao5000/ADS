package com.baiyigame.adslibrary.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baiyigame.adslibrary.Utils.PreferenceUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.base.BaseManager;
import com.baiyigame.adslibrary.model.InfoFlowAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.ILoadLisener;
import com.baiyigame.adslibrary.port.StreamLoaderListener;
import com.baiyigame.adslibrary.view.InformationFlowView;

import java.util.List;

/**
 * Information flow advertising management class
 * Created by Administrator on 2017/3/13.
 */

public class InfoFlowADSManager extends BaseManager
{

    private Context mContext = null;

    private MeterailModel meterailModel = null;
    private InfoFlowAdvertyModel infoFlowAdvertyModel = null;

    public static final int Data_Init_Complete = 0;

    private StreamLoaderListener loaderListener = null;

    private  InformationFlowView view = null;

//    private static InfoFlowADSManager Instence = null;
    public InfoFlowADSManager(Context context)
    {
        this.mContext = context ;
    }
//
//    public static InfoFlowADSManager getInstence(Context context) {
//        if (Instence == null)
//        {
//            synchronized (context)
//            {
//                if (Instence == null)
//                    Instence = new InfoFlowADSManager(context);
//            }
//        }
//        return Instence;
//    }

    public void show()
    {
        InfoFlowNetManager.getInstence(mContext).request(getUnityKey())
                .setLoadLisener(new ILoadLisener()
                {
                    @Override
                    public void onSuccess(MeterailModel meterailModel, Object AdvertyInfoModel)
                    {
                        InfoFlowADSManager.this.meterailModel = meterailModel;
                        InfoFlowADSManager.this.infoFlowAdvertyModel = (InfoFlowAdvertyModel) AdvertyInfoModel;
                        handler.sendEmptyMessage(Data_Init_Complete);
                    }

                    @Override
                    public void onError(String errorMsg, int resposeCode)
                    {
                        //Log.e("Response error:", errorMsg + ":" + resposeCode);
                        if (loaderListener != null)
                        {
                            loaderListener.onFailure(errorMsg,resposeCode);
                        }
                    }
                });
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case Data_Init_Complete:
                    showDialog();
                    break;
            }
        }
    };

    /**
     * 显示开屏广告
     */
    private void showDialog()
    {
        int nextIndex = PreferenceUtils.getInstence(mContext).Get("nextIndex",0);
        List<MeterailModel.data> datas = meterailModel.getData();
        if (Utils.isListEmpty(datas))
        {
            return;
        }
        if (nextIndex >= datas.size())
        {
            nextIndex = 0;
        }
        MeterailModel.data data = meterailModel.getData().get(nextIndex);
        String matcontype = data.getMatcontype();

       if (view == null)
       {
           view = new InformationFlowView(mContext);
       }

        view.setInfoFlowAdvertyModel(infoFlowAdvertyModel);
        view.setMeterailModel(data);
        view.setMatconType(matcontype);
        view.init(new ADSLoaderListener()
        {
            @Override
            public void onSuccess()
            {
                if (loaderListener != null)
                {
                    loaderListener.onSuccess(view);
                }
            }

            @Override
            public void onFailure(String errorMsg, int resposeCode)
            {
                if (loaderListener != null)
                {
                    loaderListener.onFailure(errorMsg,resposeCode);
                }
            }
        });
        PreferenceUtils.getInstence(mContext).Set("nextIndex",nextIndex+1);
    }


    public void setLoaderListener(StreamLoaderListener loaderListener)
    {
        this.loaderListener = loaderListener;
    }
}

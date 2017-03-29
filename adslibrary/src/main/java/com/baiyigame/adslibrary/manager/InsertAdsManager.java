package com.baiyigame.adslibrary.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.PreferenceUtils;
import com.baiyigame.adslibrary.base.BaseManager;
import com.baiyigame.adslibrary.dialog.InsertDialog;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.ILoadLisener;


/**
 * Created by Administrator on 2017/3/8.
 */

public class InsertAdsManager extends BaseManager
{
    private Context mContext = null;

    private ADSLoaderListener loaderListener = null;

    public InsertAdsManager(Context context)
    {
        this.mContext = context;
    }

    public static final int Data_Init_Complete = 0;
    public static final int Show_Dialog = 2;
    private MeterailModel meterailModel = null ;
    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private boolean isLoop = false;
    private int index = 0;
    private InsertDialog insertDialog = null;

    public void show()
    {
        BannerNetManager.getInstence(mContext).request(getUnityKey())
                .setLoadLisener(new ILoadLisener()
                {
                    @Override
                    public void onSuccess(MeterailModel meterailModel, Object AdvertyInfoModel)
                    {
                        InsertAdsManager.this.meterailModel = meterailModel;
                        InsertAdsManager.this.bannerInfoAdvertyModel = (BannerInfoAdvertyModel) AdvertyInfoModel;
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
                    initDialog();
                    break;
                case Show_Dialog:
                    showDialog();
                    break;
            }
        }
    };

    private void initDialog()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                do {
                    if (meterailModel.getStatus() == -1)
                    {
                        //failure
                        isLoop = false;
                    }
                    else
                    {
                        index = PreferenceUtils.getInstence(mContext).Get("Index",0);
                        if (index >= meterailModel.getData().size())
                        {
                            index = 0;
                            PreferenceUtils.getInstence(mContext).Set("Index",index);
                        }
                        String matType = meterailModel.getData().get(index).getMatcontype();
                        if (!mContext.getResources().getString(R.string.html).equals(matType))
                        {
                            handler.sendEmptyMessage(Show_Dialog);
                            isLoop = false;
                        }
                        else
                        {
                            ++index;
                            isLoop = true;
                        }
                        PreferenceUtils.getInstence(mContext).Set("Index",index);
                    }
                }
                while (isLoop);
            }
        }.start();

    }

    private void showDialog()
    {
       try {
           if (insertDialog != null)
           {
               if (insertDialog.isShowing())
               {
                   insertDialog.dismiss();
                   insertDialog.cancel();
               }
               insertDialog = null;
           }

           insertDialog = new InsertDialog(mContext);
           insertDialog.onAttachedToWindow();
           insertDialog.setBannerInfoAdvertyModel(bannerInfoAdvertyModel);
           insertDialog.setMeterailModel(meterailModel);
           insertDialog.setIndex(index);
           insertDialog.init();
           insertDialog.show();
           if (loaderListener != null)
           {
               loaderListener.onSuccess();
           }
       }
       catch (WindowManager.BadTokenException e)
       {
          if (null != insertDialog)
          {
              insertDialog.dismiss();
          }
           if (loaderListener != null)
           {
               loaderListener.onFailure(e.getMessage(),-1);
           }
           //throw new WindowManager.BadTokenException(e.getMessage());
       } catch (Exception e)
       {
           if (null != insertDialog)
           {
               insertDialog.dismiss();
           }
           //throw new IllegalStateException(e.getMessage());
           if (loaderListener != null)
           {
               loaderListener.onFailure(e.getMessage(),-1);
           }
       }
    }
    public void setLoaderListener(ADSLoaderListener loaderListener)
    {
        this.loaderListener = loaderListener;
    }
}

package com.baiyigame.adslibrary.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.IntentUtils;
import com.baiyigame.adslibrary.base.BaseManager;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.OpenAdvertyModel;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.ILoadLisener;


/**
 *  Opening the advertising management class
 *  * Created by Administrator on 2017/3/13.
 */

public class CoopenADSManager  extends BaseManager
{

    private Context mContext = null;

    private MeterailModel meterailModel = null;
    private OpenAdvertyModel openAdvertyModel = null;

    private ADSLoaderListener loaderListener = null;

    public static final int Data_Init_Complete = 0;

    private static CoopenADSManager Instence = null;
    private CoopenADSManager(Context context)
    {
        this.mContext = context ;
    }

    public static CoopenADSManager getInstence(Context context)
    {
       if (Instence == null)
       {
           synchronized (context)
           {
               if (Instence == null)
                   Instence = new CoopenADSManager(context);
           }
       }
        return Instence;
    }

    public void show()
    {
        CoopenNetManager.getInstence(mContext).request(getUnityKey(),new ILoadLisener()
        {
                    @Override
                    public void onSuccess(MeterailModel meterailModel, Object AdvertyInfoModel)
                    {
                        CoopenADSManager.this.meterailModel = meterailModel;
                        CoopenADSManager.this.openAdvertyModel = (OpenAdvertyModel) AdvertyInfoModel;
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
     * According to the tail of advertising
     */
    private void showDialog()
    {
       if (meterailModel.getStatus() != -1)
       {
//           CoopenDialog dialog = new CoopenDialog(mContext);
//           dialog.setMeterailModel(meterailModel);
//           dialog.setOpenAdvertyModel(openAdvertyModel);
//           dialog.Init();

           if (IntentUtils.goCoopenADs(mContext,meterailModel,openAdvertyModel))
           {
               if (loaderListener != null)
               {
                   loaderListener.onSuccess();
               }
           }
           else
           {
               if (loaderListener != null)
               {
                   loaderListener.onFailure(mContext.getResources().getString(R.string.act_not_find),-1);
               }
           }
       }
    }

    public void setLoaderListener(ADSLoaderListener loaderListener)
    {
        this.loaderListener = loaderListener;
    }
}

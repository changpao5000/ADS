package com.baiyigame.adslibrary.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.baiyigame.adslibrary.base.BaseManager;
import com.baiyigame.adslibrary.dialog.BannerDialog;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.ILoadLisener;
import com.baiyigame.adslibrary.port.OnDialogCanaleListner;


/**
 * manger banner
 * Created by Administrator on 2017/3/8.
 */

public class BannerAdsManager extends BaseManager implements OnDialogCanaleListner
{
    private Context mContext = null;

    private ADSLoaderListener loaderListener = null;

    public BannerAdsManager(Context context){
        this.mContext = context;
    }

    public static final int Data_Init_Complete = 0;
    public static final int Show_Dialog = 2;
    private MeterailModel meterailModel = null ;
    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;

//    Set up information flow (banners) advertising is not round
    private boolean isLoop = false;

    private int duration = 10000;
    private int index = 0;
    private BannerDialog bannerDialog = null;

    public void show()
    {
        BannerNetManager.getInstence(mContext).request(getUnityKey())
                .setLoadLisener(new ILoadLisener()
                {
                    @Override
                    public void onSuccess(MeterailModel meterailModel, Object bannerInfoAdvertyModel) {
                        BannerAdsManager.this.meterailModel = meterailModel;
                        BannerAdsManager.this.bannerInfoAdvertyModel = (BannerInfoAdvertyModel) bannerInfoAdvertyModel;
                        handler.sendEmptyMessage(Data_Init_Complete);
//                        if (BannerAdsManager.this.meterailModel.getStatus() !=-1&&   BannerAdsManager.this.bannerInfoAdvertyModel.getStatus()!=-1)
//                        {
//                            if (loaderListener != null)
//                            {
//                                loaderListener.onSuccess();
//                            }
//                        }
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
                    loopDialog();
                    break;
                case Show_Dialog:
                    showDialog();
                    break;
            }
        }
    };

    private void loopDialog()
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
                      }else
                      {
                          handler.sendEmptyMessage(Show_Dialog);
                          ++index;
                          if (index>=meterailModel.getData().size())
                          {
                              index = 0;
                          }
                          try {
                              int r = bannerInfoAdvertyModel.getData().getR();
                              if (r>0)
                              {
                                  Thread.sleep(r);
                              }else
                              {
                                  Thread.sleep(duration);
                              }
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                      }
                }
                while (isLoop);
            }
        }.start();

    }

    private void showDialog() {
       try
       {
           if (bannerDialog != null)
           {
               if (bannerDialog.isShowing())
               {
                   bannerDialog.dismiss();
                   bannerDialog.cancel();
               }
               bannerDialog = null;
           }

           bannerDialog = new BannerDialog(mContext);
           bannerDialog.onAttachedToWindow();
           bannerDialog.setMeterailModel(meterailModel);
           bannerDialog.setBannerInfoAdvertyModel(bannerInfoAdvertyModel);
           bannerDialog.setIndex(index);
           bannerDialog.init();
           bannerDialog.show();
           bannerDialog.setDialogCanaleListner(this);
           if (loaderListener != null)
           {
               loaderListener.onSuccess();
           }

       }catch (WindowManager.BadTokenException e)
       {
           if (bannerDialog != null)
           {
               bannerDialog.dismiss();
           }
           if (loaderListener != null)
           {
               loaderListener.onFailure(e.getMessage(),-1);
           }
           //throw new WindowManager.BadTokenException(e.getMessage());
       }
       catch (Exception e)
       {
           if (bannerDialog != null)
           {
               bannerDialog.dismiss();
           }
           if (loaderListener != null)
           {
               loaderListener.onFailure(e.getMessage(),-1);
           }
           //throw new IllegalStateException(e.getMessage());
       }
    }

    /**
     * Set the banner ads is shuffling
     * @param loop
     */
    public void setLoop(boolean loop)
    {
        isLoop = loop;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    @Override
    public void onCancel()
    {
        setLoop(false);
    }

    public void setLoaderListener(ADSLoaderListener loaderListener)
    {
        this.loaderListener = loaderListener;
    }
}

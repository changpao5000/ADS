package com.baiyigame.adslibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.Utils.DisplayUtils;
import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.Utils.IntentUtils;
import com.baiyigame.adslibrary.Utils.ScreenUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.animation.LayoutAnimation;
import com.baiyigame.adslibrary.imageview.CircleImageView;
import com.baiyigame.adslibrary.imageview.RectangleImageView;
import com.baiyigame.adslibrary.manager.ImageManager;
import com.baiyigame.adslibrary.manager.VideoPlayerManager;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.IVideoPrepareComplete;
import com.baiyigame.adslibrary.port.ImageLoaderListener;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.view.video.NormalScreenVideoView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class InsertADSView extends BaseView implements View.OnClickListener
{

    private OnCloseBtnClickListner listner;
    private View view;

    private RectangleImageView mImgBg;
    private CircleImageView mImgClose;
    private NormalScreenVideoView mInserAdsVideo;
    private ProgressBar mProgressBar;

    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private MeterailModel meterailModel = null;

    private int index;

    private RelativeLayout root;

    private  BannerInfoAdvertyModel.data.style.c style = null;

    public InsertADSView(Context context) {
        this(context, null);
    }

    public InsertADSView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsertADSView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {
        style = bannerInfoAdvertyModel.getData().getStyle().getC();

        if (view == null) {
            LayoutInflater inflater = ContextUtils.getlayoutInfater(getContext());
            view = inflater.inflate(R.layout.insert_ads_view, this, false);

            mImgBg = (RectangleImageView) view.findViewById(R.id.ads_bg_img);
            mImgClose = (CircleImageView) view.findViewById(R.id.close);
            mInserAdsVideo = (NormalScreenVideoView) view.findViewById(R.id.inset_ads_video);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
            root = (RelativeLayout) view.findViewById(R.id.root);

            mImgBg.setOnClickListener(this);
            mImgClose.setOnClickListener(this);
            mInserAdsVideo.setOnClickListener(this);
            root.setOnClickListener(this);

        }

        delayShowClose(mImgClose,style.getDelay());

        List<MeterailModel.data> datas = meterailModel.getData();

        loadImg(bannerInfoAdvertyModel.getData().getStyle().getC().getUrl(), mImgClose);

        mProgressBar.setVisibility(VISIBLE);

        String matconType = datas.get(index).getMatcontype();
        String picUrl = datas.get(index).getPicurl().get(0);
        if (getContext().getResources().getString(R.string.banner_pic).equals(matconType))
        {
            mInserAdsVideo.setVisibility(GONE);
            mImgBg.setVisibility(VISIBLE);
            loadImg(picUrl, mImgBg);

        }
        else if (getContext().getResources().getString(R.string.banner_video).equals(matconType))
        {
            mInserAdsVideo.setVisibility(VISIBLE);
            mImgBg.setVisibility(GONE);

            playVideo(picUrl);
        }
        setViewStyle();

        addView(view);
    }

    String videoPath;

    private void playVideo(String url)
    {
        if (Utils.isStringEmpty(url))
        {
            return;
        }
        VideoPlayerManager.getInstence(getContext()).play(url)//
                .setiVideoPrepareComplete(new IVideoPrepareComplete()
                {
                    @Override
                    public void onComplete(String path)
                    {
                        Log.e("Response path:", path);
                        videoPath = path;
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onError(String errorMsg, int resposeCode)
                    {
                        Log.e("Response error:", errorMsg + ":" + resposeCode);
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
                case 0:
                    mProgressBar.setVisibility(GONE);
                    mInserAdsVideo.play(videoPath);
                    break;
            }
        }
    };

    private void loadImg(String url, final ImageView img)
    {
       synchronized (img)
       {
           ImageManager manager = new ImageManager(getContext());
           manager.setImageLoaderListener(new ImageLoaderListener()
           {

               @Override
               public void onSuccess(View view, Bitmap bitmap, String path)
               {
                   mProgressBar.setVisibility(GONE);
                   if (img instanceof CircleImageView)
                   {
                       Bitmap bitmap1 = ImageUtils.GetRoundedCornerBitmap(bitmap, bannerInfoAdvertyModel.getData().getStyle().getC().getR());
                       img.setImageBitmap(bitmap1);
                   } else {
                       img.setImageBitmap(bitmap);
                   }
               }

               @Override
               public void onError(View view, String path, String errorMsg)
               {
                   Log.e("Response onError:", errorMsg + ":" + path);
                   mProgressBar.setVisibility(GONE);
               }

               @Override
               public void Loading(View view, String path)
               {
                   Log.e("Response Loading:",  path);
               }
           });
           manager.request(url,img);
       }
    }

    private void setViewStyle()
    {
        closeStyle();
    }


    private void closeStyle()
    {

        LayoutParams layoutParams = new LayoutParams(DisplayUtils.dip2px(getContext(), style.getW()), DisplayUtils.dip2px(getContext(), style.getH()));
        layoutParams.setMargins((int) (ScreenUtils.getScreeWidth(getContext()) - mImgClose.getWidth()) / 100 * style.getX(),//
                (int) (ScreenUtils.getScreeWidth(getContext()) - mImgClose.getHeight()) / 100 * style.getY(), 0, 0);
        mImgClose.setBackgroundColor(Color.parseColor(style.getBc()));

        mImgClose.setLayoutParams(layoutParams);
    }

    public void setMeterailModel(MeterailModel meterailModel)
    {
        this.meterailModel = meterailModel;
    }

    public void setBannerInfoAdvertyModel(BannerInfoAdvertyModel bannerInfoAdvertyModel)
    {
        this.bannerInfoAdvertyModel = bannerInfoAdvertyModel;
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.ads_bg_img || i == R.id.inset_ads_video || i == R.id.root)
        {
            LayoutAnimation.getInstence().clickAnim(this);
            IntentUtils.goWeb(getContext(),meterailModel.getData().get(index).getWeburl());

        } else if (i == R.id.close)
        {
            if (listner != null) {
                listner.onCloseClick();
            }

        }
    }

    public void setListner(OnCloseBtnClickListner listner)
    {
        this.listner = listner;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
}

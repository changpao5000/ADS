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
import com.baiyigame.adslibrary.view.video.FullScreenVideoView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BannerADSView extends BaseView implements View.OnClickListener
{
    private OnCloseBtnClickListner listner;

    private View view;

    private RectangleImageView mBannerPicImg;
    private CircleImageView mImgClose;

    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private MeterailModel meterailModel = null;

    //图片模块
    private RelativeLayout mBannerPic;
    //视频模块
    private RelativeLayout mBannerVideo;
    private FullScreenVideoView mBannerVideoView;
    private ProgressBar mProgressBar;
    //文字模块
    private RelativeLayout mBannerChar;
    //Icon
    private CircleImageView mBannerCharIcon;
    //文字模块背景tu
    private RectangleImageView mBannerCharBg;
    private AutoRollRelativeLayout autoRollRelativeLayout;

    //第几个对象
    private int index = 0;

    private static final int Play_Video = 0;

    private BannerInfoAdvertyModel.data.style.c style = null;

    public BannerADSView(Context context)
    {
        this(context, null);
    }

    public BannerADSView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BannerADSView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

    }

    public void init()
    {
        style = bannerInfoAdvertyModel.getData().getStyle().getC();

        if (view == null) {
            LayoutInflater inflater = ContextUtils.getlayoutInfater(getContext());
            view = inflater.inflate(R.layout.banner_ads_view, this, false);

            mBannerPicImg = (RectangleImageView) view.findViewById(R.id.banner_pic_bg);
            mImgClose = (CircleImageView) view.findViewById(R.id.close);

            mBannerPic = (RelativeLayout) view.findViewById(R.id.banner_pic);
            mBannerVideo = (RelativeLayout) view.findViewById(R.id.banner_video);
            mBannerChar = (RelativeLayout) view.findViewById(R.id.banner_char);
            mBannerCharIcon = (CircleImageView) view.findViewById(R.id.banner_char_icon);
           // mBannerCharBg = (RectangleImageView) view.findViewById(R.id.banner_char_bg);
            autoRollRelativeLayout = (AutoRollRelativeLayout) view.findViewById(R.id.anim1);

            mBannerVideoView = (FullScreenVideoView) view.findViewById(R.id.banner_video_player);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);

            mBannerPicImg.setOnClickListener(this);
            mImgClose.setOnClickListener(this);
            mBannerVideoView.setOnClickListener(this);
            autoRollRelativeLayout.setOnClickListener(this);
            mBannerCharIcon.setOnClickListener(this);
        }

        delayShowClose(mImgClose,style.getDelay());

        loadImg(bannerInfoAdvertyModel.getData().getStyle().getC().getUrl(), mImgClose);

        mProgressBar.setVisibility(VISIBLE);

        String matconType = meterailModel.getData().get(index).getMatcontype();
        if (getContext().getString(R.string.bannner_char).equals(matconType))
        {
            mBannerPic.setVisibility(GONE);
            mBannerVideo.setVisibility(GONE);
            mBannerChar.setVisibility(VISIBLE);

            String iconurl = meterailModel.getData().get(index).getIconurl();
            if (!Utils.isStringEmpty(iconurl)) {
                loadImg(iconurl, mBannerCharIcon);
            }

            autoRollRelativeLayout.initView();
            autoRollRelativeLayout.setIndex(index);
            autoRollRelativeLayout.setMeterailModel(meterailModel);
            autoRollRelativeLayout.setBannerInfoAdvertyModel(bannerInfoAdvertyModel);
            autoRollRelativeLayout.updateUI();
            autoRollRelativeLayout.startAnimation();

        } else if (getContext().getString(R.string.banner_pic).equals(matconType)) {
            mBannerPic.setVisibility(VISIBLE);
            mBannerVideo.setVisibility(GONE);
            mBannerChar.setVisibility(GONE);
            ArrayList<String> urls = meterailModel.getData().get(index).getPicurl();
            if (!Utils.isListEmpty(urls)) {
                loadImg(urls.get(0), mBannerPicImg);
            }
        } else if (getContext().getString(R.string.banner_video).equals(matconType)) {
            mBannerPic.setVisibility(GONE);
            mBannerVideo.setVisibility(VISIBLE);
            mBannerChar.setVisibility(GONE);
            playVideo();
        }

        setViewStyle();
        addView(view);
    }

    String videoPath;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case Play_Video:
                    mProgressBar.setVisibility(GONE);
                    //mBannerVideoView.setVisibility(VISIBLE);
                    mBannerVideoView.play(videoPath);
                    break;
            }
        }
    };
    private void playVideo()
    {
        String url = meterailModel.getData().get(index).getPicurl().get(0);
        if (Utils.isStringEmpty(url))
        {
            return;
        }
        VideoPlayerManager.getInstence(getContext()).play(url)//
                .setiVideoPrepareComplete(new IVideoPrepareComplete()
                {
                    @Override
                    public void onComplete(String path) {
                        Log.e("Response path:", path);
                        videoPath = path;
                        handler.sendEmptyMessage(Play_Video);
                    }

                    @Override
                    public void onError(String errorMsg, int resposeCode)
                    {
                        Log.e("Response error:", errorMsg + ":" + resposeCode);
                    }
                });
    }

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
                   }
                   else
                   {
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
                   Log.e("Response Loading:", path);
               }
           });
           manager.request(url, img);
       }
    }

    private void setViewStyle()
    {
        closeStyle();
        iconStrle();
    }




    private void iconStrle()
    {
        BannerInfoAdvertyModel.data.style.i style = bannerInfoAdvertyModel.getData().getStyle().getI();
        LayoutParams layoutParams = new LayoutParams(DisplayUtils.dip2px(getContext(), style.getS()), DisplayUtils.dip2px(getContext(), style.getS()));
        layoutParams.setMargins(style.getL(),//
                style.getT(), 0, 0);
        mBannerCharIcon.setBackgroundColor(Color.parseColor(style.getBc()));

        mBannerCharIcon.setLayoutParams(layoutParams);
    }

    private void closeStyle()
    {

        LayoutParams layoutParams = new LayoutParams(DisplayUtils.dip2px(getContext(), style.getW()), DisplayUtils.dip2px(getContext(), style.getH()));
        layoutParams.setMargins(
                (int) (ScreenUtils.getScreeWidth(getContext()) - mImgClose.getWidth()) / 100 * style.getX(),//
                (int) (ScreenUtils.getScreeWidth(getContext()) - mImgClose.getHeight()) / 100 * style.getY(), 0, 0);
        mImgClose.setBackgroundColor(Color.parseColor(style.getBc()));

        mImgClose.setLayoutParams(layoutParams);

        postInvalidate();
        invalidate();
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.close)
        {
            if (listner != null)
            {
                listner.onCloseClick();
            }
            autoRollRelativeLayout.cancelAnim();

        }
        else if (i == R.id.banner_video_player || i == R.id.anim1
                || i == R.id.banner_char_icon || i == R.id.banner_pic_bg)
        {
            LayoutAnimation.getInstence().clickAnim(this);
            IntentUtils.goWeb(getContext(),
                    meterailModel.getData().get(index).getWeburl());
        }
    }

    public void setMeterailModel(MeterailModel meterailModel)
    {
        this.meterailModel = meterailModel;
    }

    public void setBannerInfoAdvertyModel(BannerInfoAdvertyModel bannerInfoAdvertyModel)
    {
        this.bannerInfoAdvertyModel = bannerInfoAdvertyModel;
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

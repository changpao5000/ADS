package com.baiyigame.adslibrary.view.flowview;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.Utils.DisplayUtils;
import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.Utils.IntentUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.animation.LayoutAnimation;
import com.baiyigame.adslibrary.config.ThreeSmallPicModel;
import com.baiyigame.adslibrary.imageview.RectangleImageView;
import com.baiyigame.adslibrary.manager.ImageManager;
import com.baiyigame.adslibrary.model.InfoFlowAdvertyModel;
import com.baiyigame.adslibrary.port.ImageLoaderListener;

import java.util.List;

/**
 * 有三种排版方式
 * 一、主标题  副标题  图片
 * 二、主标题  图片  副标题
 * 三、主标题   图片
 * Created by Administrator on 2017/3/20.
 */

public class ThreeSmallPicStreamView extends BaseStreamView implements View.OnClickListener
{
    public static final String TAG = ThreeSmallPicStreamView.class.getSimpleName();

    private View view = null;

    private RectangleImageView mImgOnePic = null;
    private RectangleImageView mImgTwoPic = null;
    private RectangleImageView mImgThreePic = null;
    private TextView mTitle = null;
    private TextView mSubTitle01 = null;
    private TextView mSubTitle02 = null;

    private  InfoFlowAdvertyModel.data.style.pu pu = null;

    private  InfoFlowAdvertyModel.data.style.ths ob = null;

    public ThreeSmallPicStreamView(Context context)
    {
        this(context, null);
    }

    public ThreeSmallPicStreamView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ThreeSmallPicStreamView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {

        ob = infoFlowAdvertyModel.getData().getStyle().getThs();
        pu = infoFlowAdvertyModel.getData().getStyle().getPu();

        if (view == null) {

            view = ContextUtils.getlayoutInfater(getContext())
                    .inflate(R.layout.three_pic_stream_view, this, false);
            mImgOnePic = (RectangleImageView) view.findViewById(R.id.img_pic_one);
            mImgTwoPic = (RectangleImageView) view.findViewById(R.id.img_pic_teo);
            mImgThreePic = (RectangleImageView) view.findViewById(R.id.img_pic_three);

            mTitle = (TextView) view.findViewById(R.id.title);
            mSubTitle01 = (TextView) view.findViewById(R.id.subtitle01);
            mSubTitle02 = (TextView) view.findViewById(R.id.subtitle02);
        }

        List<String> pics = meterailModel.getPicurl();
        if (!Utils.isListEmpty(pics))
        {
            if (pics.size()>=3)
            {
                loadImg(pics.get(0), mImgThreePic,3);
                loadImg(pics.get(1), mImgOnePic,1);
                loadImg(pics.get(2), mImgTwoPic,2);
            }
            else if (pics.size() >= 2)
            {
                loadImg(pics.get(0), mImgThreePic,3);
                loadImg(pics.get(1), mImgOnePic,1);
                loadImg(pics.get(0), mImgTwoPic,2);
            }
            else
            {
                loadImg(pics.get(0), mImgThreePic,3);
                loadImg(pics.get(0), mImgOnePic,1);
                loadImg(pics.get(0), mImgTwoPic,2);
            }
        }
        mTitle.setText(meterailModel.getName());
        if (ThreeSmallPicModel.Model_02.equals(ob.getP()))
        {
            mSubTitle01.setVisibility(VISIBLE);
            mSubTitle02.setVisibility(GONE);
            mSubTitle01.setText(meterailModel.getSubtitle());

            subTitleStyle(mSubTitle01,pu);
        }
        else if (ThreeSmallPicModel.Model_01.equals(ob.getP()))
        {
            mSubTitle01.setVisibility(GONE);
            mSubTitle02.setVisibility(VISIBLE);
            mSubTitle02.setText(meterailModel.getSubtitle());

            subTitleStyle(mSubTitle02,pu);
        }
        else
        {
            mSubTitle01.setVisibility(GONE);
            mSubTitle02.setVisibility(GONE);
        }

        style();
        addView(view);

        this.setOnClickListener(this);
    }

    private void style()
    {

        setViewStyle(pu);
        titleStyle(mTitle,pu);
    }

    private void loadImg(String imgUrl, ImageView mImg, final int index)
    {
        ImageManager manager = new ImageManager(getContext());
        manager.setImageLoaderListener(new ImageLoaderListener()
        {
            @Override
            public void onSuccess(final View view, final Bitmap bitmap, final String path)
            {
                postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
//                        Log.e(TAG, "onSuccess pic :" + path);
                        threeSmallPicStyle((ImageView) view,bitmap,index);
                    }
                },0);
                postInvalidateDelayed(0);
            }

            @Override
            public void onError(View view, String path, String errorMsg)
            {
                Log.e("Response error:", errorMsg + ":" + path);
            }
            @Override
            public void Loading(View view, String path)
            {
            }
        });
        manager.request(imgUrl, mImg);
    }

    private void threeSmallPicStyle(ImageView mImg, Bitmap bitmap, int index)
    {

        Bitmap bgBitmap = getBorderBitmap(ob.getBc(), ob.getIfi());
        Bitmap newBitmap = ImageUtils.combineBitmap(bgBitmap,ImageUtils.GetRoundedCornerBitmap(bitmap,ob.getIfi()), (int) ob.getBw());

        int width = (int) ob.getWi();
        if (width <= 20)
        {
            width = getWidth()/3-DisplayUtils.dip2px(getContext(), (float) (ob.getLe()*1.5));
        }
        float bar = ob.getSar();
        int heigth = (int) (width/bar);

        mImg.setImageBitmap(newBitmap);

        LinearLayout.LayoutParams params =
                new  LinearLayout.LayoutParams(width,heigth);
        if (1==index || 2==index)
        {
            params.setMargins(DisplayUtils.dip2px(getContext(),(int) ob.getLe()),
                    DisplayUtils.dip2px(getContext(),(int) ob.getTo()),
                    0,
                    DisplayUtils.dip2px(getContext(),(int) ob.getTo()));
        }
        else
        {
            params.setMargins(DisplayUtils.dip2px(getContext(),(int) ob.getLe()),
                    DisplayUtils.dip2px(getContext(),(int) ob.getTo()),
                    DisplayUtils.dip2px(getContext(),(int) ob.getLe()),
                    DisplayUtils.dip2px(getContext(),(int) ob.getTo()));
        }

        mImg.setLayoutParams(params);
        postInvalidate();
        invalidate();
    }

    @Override
    public void onClick(View v)
    {
        LayoutAnimation.getInstence().clickAnim(this);
        IntentUtils.goWeb(getContext(), meterailModel.getWeburl());
    }
}

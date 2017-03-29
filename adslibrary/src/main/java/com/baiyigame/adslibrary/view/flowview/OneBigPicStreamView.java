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
import com.baiyigame.adslibrary.config.BigPicModel;
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

public class OneBigPicStreamView extends BaseStreamView implements View.OnClickListener
{
    public static final String TAG = OneBigPicStreamView.class.getSimpleName();

    private View view = null;

    private TextView mTitle = null;
    private TextView mSubTitle01 = null;
    private TextView mSubTitle02 = null;
    private RectangleImageView mBigPic= null;

    private InfoFlowAdvertyModel.data.style.ob ob = null;

    private  InfoFlowAdvertyModel.data.style.pu pu = null;

    public OneBigPicStreamView(Context context)
    {
        this(context, null);
    }
    public OneBigPicStreamView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public OneBigPicStreamView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {
        ob = infoFlowAdvertyModel.getData().getStyle().getOb();
        pu = infoFlowAdvertyModel.getData().getStyle().getPu();

        if (view == null)
           {
               view = ContextUtils.getlayoutInfater(getContext())
                       .inflate(R.layout.one_big_pic_stream_view,this,false);
               mTitle = (TextView) view.findViewById(R.id.title);
               mSubTitle01 = (TextView) view.findViewById(R.id.subtitle01);
               mSubTitle02 = (TextView) view.findViewById(R.id.subtitle02);
               mBigPic = (RectangleImageView) view.findViewById(R.id.big_pic);
           }
        mTitle.setText(meterailModel.getName());
        List<String> pics = meterailModel.getPicurl();
        if (!Utils.isListEmpty(pics))
        {
            loadBigImg(pics.get(0),mBigPic);
        }

        if (BigPicModel.Mode_02.equals(ob.getP()))
        {
            mSubTitle01.setVisibility(VISIBLE);
            mSubTitle02.setVisibility(GONE);
            mSubTitle01.setText(meterailModel.getSubtitle());

            subTitleStyle(mSubTitle01,pu);
        }
        else if (BigPicModel.Mode_01.equals(ob.getP()))
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

    private void BigPicStyle(ImageView mImg, Bitmap bitmap)
    {
        Bitmap bgBitmap = getBorderBitmap(ob.getBc(), ob.getIfi());
        Bitmap newBitmap = ImageUtils.combineBitmap(bgBitmap,ImageUtils.GetRoundedCornerBitmap(bitmap,ob.getIfi()), (int) ob.getBw());

        int  width = getWidth()-(int) ob.getLe()*2;
        float bar = ob.getBar();
        int heigth = (int) (width/bar);

       // mImg.setImageBitmap(newBitmap);
        mImg.setBackground(ImageUtils.bitmap2Drawable(newBitmap));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heigth);
//        params.setMargins((int) ob.getLe(),(int) ob.getTo(),(int) ob.getLe(),(int) ob.getTo());
        params.setMargins(DisplayUtils.dip2px(getContext(),(int) ob.getLe()),
                DisplayUtils.dip2px(getContext(),(int) ob.getTo()),
                DisplayUtils.dip2px(getContext(),(int) ob.getLe()),
                DisplayUtils.dip2px(getContext(),(int) ob.getTo()));
        mImg.setLayoutParams(params);
        postInvalidate();
        invalidate();
    }

    private void loadBigImg(String imgUrl, ImageView mImg)
    {
        ImageManager manager = new ImageManager(getContext());
        manager.setImageLoaderListener(new ImageLoaderListener()
        {
            @Override
            public void onSuccess(final View view, final Bitmap bitmap, final String path) {
               postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       Log.e(TAG,"onSuccess pic :"+path);
                       BigPicStyle((ImageView) view,bitmap);
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
    @Override
    public void onClick(View v)
    {
        LayoutAnimation animation = LayoutAnimation.getInstence().clickAnim(this);
        IntentUtils.goWeb(getContext(), meterailModel.getWeburl());
    }
}

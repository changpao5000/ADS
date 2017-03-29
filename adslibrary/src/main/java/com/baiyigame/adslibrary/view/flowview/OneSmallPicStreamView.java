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
import com.baiyigame.adslibrary.config.OneSmallPicModel;
import com.baiyigame.adslibrary.imageview.RectangleImageView;
import com.baiyigame.adslibrary.manager.ImageManager;
import com.baiyigame.adslibrary.model.InfoFlowAdvertyModel;
import com.baiyigame.adslibrary.port.ImageLoaderListener;

import java.util.List;

/**
 * 两种模式
 * 一、主标题 副标题  图片在右面
 * 二、图片  主标题   副标题
 * Created by Administrator on 2017/3/20.
 */

public class OneSmallPicStreamView extends BaseStreamView implements View.OnClickListener
{
    public static final String TAG = OneSmallPicStreamView.class.getSimpleName();

    private View view = null;

    private TextView mTitle = null;
    private TextView mSubTitle = null;
    private RectangleImageView mOneSmallPic = null;

    private  InfoFlowAdvertyModel.data.style.os os= null;

    public OneSmallPicStreamView(Context context)
    {
        this(context, null);
    }

    public OneSmallPicStreamView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public OneSmallPicStreamView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        os = infoFlowAdvertyModel.getData().getStyle().getOs();

        if (view == null) {
            if (OneSmallPicModel.Model_01.equals(os.getP()))
            {
                view = ContextUtils.getlayoutInfater(getContext())
                        .inflate(R.layout.one_small_pic_stream_view_01, this, false);
            }else
            {
                view = ContextUtils.getlayoutInfater(getContext())
                        .inflate(R.layout.one_small_pic_stream_view_02, this, false);
            }
            mTitle = (TextView) view.findViewById(R.id.title);
            mSubTitle = (TextView) view.findViewById(R.id.subtitle);
            mOneSmallPic = (RectangleImageView) view.findViewById(R.id.one_small_pic);
        }
        mTitle.setText(meterailModel.getName());
        mSubTitle.setText(meterailModel.getSubtitle());

        List<String> pics = meterailModel.getPicurl();
        if (!Utils.isListEmpty(pics))
        {
            loadImg(pics.get(0),mOneSmallPic);
        }

        style();
        addView(view);

        this.setOnClickListener(this);
    }
    private void style()
    {
        InfoFlowAdvertyModel.data.style.pu pu = infoFlowAdvertyModel.getData().getStyle().getPu();
        setViewStyle(pu);
        titleStyle(mTitle,pu);
        subTitleStyle(mSubTitle,pu);
    }

    private void loadImg(String imgUrl, final ImageView mImg)
    {
        ImageManager manager = new ImageManager(getContext());
        manager.setImageLoaderListener(new ImageLoaderListener() {
            @Override
            public void onSuccess(final View view, final Bitmap bitmap, final String path)
            {
                postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        oneSmallpicStyle((ImageView) view,bitmap);
                        Log.e(TAG, "onSuccess pic :" + path);
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

    private void oneSmallpicStyle(ImageView mImg,Bitmap bitmap)
    {

        Bitmap bgBitmap = getBorderBitmap(os.getBc(), os.getIfi());
        Bitmap newBitmap = ImageUtils.combineBitmap(
                bgBitmap,ImageUtils.GetRoundedCornerBitmap(bitmap,os.getIfi()), (int) os.getBw());

        int width = (int) os.getWi();
        if (width <= 20)
        {
            width = getWidth()/8-(int) os.getLe();
        }
        float isr = os.getIsr();
        int  heigth = (int) (width/isr);

        mImg.setImageBitmap(newBitmap);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(width,heigth);
//        params.setMargins((int) os.getLe(),(int) os.getTo(),(int) os.getRi(),(int) os.getTo());
        params.setMargins(DisplayUtils.dip2px(getContext(),(int) os.getLe()),
                DisplayUtils.dip2px(getContext(),(int) os.getTo()),
                DisplayUtils.dip2px(getContext(),(int) os.getRi()),
                DisplayUtils.dip2px(getContext(),(int) os.getTo()));

        mImg.setLayoutParams(params);
    }

    @Override
    public void onClick(View v)
    {
        LayoutAnimation.getInstence().clickAnim(this);
        IntentUtils.goWeb(getContext(), meterailModel.getWeburl());
    }
}

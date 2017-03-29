package com.baiyigame.adslibrary.view.flowview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.model.InfoFlowAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;

/**
 * Created by Administrator on 2017/3/21.
 */

public class BaseStreamView  extends RelativeLayout
{

    public InfoFlowAdvertyModel infoFlowAdvertyModel = null;
    public MeterailModel.data meterailModel = null;

    public BaseStreamView(Context context)
    {
        this(context, null);
    }

    public BaseStreamView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BaseStreamView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void setViewStyle(InfoFlowAdvertyModel.data.style.pu pu)
    {
        setBackGround(null,pu.getFs(),pu.getFc(),(int) pu.getFw());
        setMargin((int) pu.getPs());
    }
    public  void setBackGround(String viewColor,float roundPx,String borderColor,int borderSize)
    {
        Bitmap bitmap = ImageUtils.combineBitmap(getBorderBitmap(borderColor,roundPx),getViewBitmap(viewColor,roundPx),borderSize);
        setBackground(ImageUtils.bitmap2Drawable(bitmap));
    }
    /**
     * 得到边框图片
     * @param borderColor
     * @param roundPx
     * @return
     */
    public Bitmap getBorderBitmap(String borderColor,float roundPx)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black_bg);
        bitmap = ImageUtils.getAlphaBitmap(bitmap, ImageUtils.toColorFromString(borderColor));
        return ImageUtils.GetRoundedCornerBitmap(bitmap,roundPx);
    }

    private Bitmap getViewBitmap(String viewColor,float roundPx)
    {
        if (Utils.isStringEmpty(viewColor)||viewColor.contains("ffffff")||viewColor.contains("FFFFFF"))
        {
            viewColor = "#FF000000";
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black_bg);
        bitmap = ImageUtils.getAlphaBitmap(bitmap, /*ImageUtils.toColorFromString(viewColor)*/Color.BLACK);
        return ImageUtils.GetRoundedCornerBitmap(bitmap,roundPx);
    }

    /**
     * 内容到手机边缘的距离
     * @param distance
     */
    public void setMargin(int distance)
    {
        //setPadding(distance,distance,distance,distance);
        RelativeLayout.LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(distance,distance,distance,distance);
        setLayoutParams(params);
    }

    public void titleStyle(TextView mTitle,InfoFlowAdvertyModel.data.style.pu pu)
    {
        mTitle.setTextSize(pu.getHs());
        mTitle.setTextColor(ImageUtils.toColorFromString(pu.getHc()));
        mTitle.setTextScaleX(pu.getHts());
        mTitle.setPadding((int) pu.getHlm(),(int) pu.getHtm(),0,0);
//        mTitle.setFontFeatureSettings(pu.getSf());

//        RelativeLayout.LayoutParams params =
//                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) pu.getHlh());
//        params.setMargins((int) pu.getHlm(),(int) pu.getHtm(),0,0);
//        mTitle.setLayoutParams(params);

    }

    public void subTitleStyle(TextView mSubTitle,InfoFlowAdvertyModel.data.style.pu pu)
    {
        mSubTitle.setTextSize(pu.getSs());
        mSubTitle.setTextColor(ImageUtils.toColorFromString(pu.getSc()));
        //字间距
        mSubTitle.setTextScaleX(pu.getSts());
        mSubTitle.setPadding((int) pu.getSlm(),(int) pu.getStm(),0,0);
    }

    public void setInfoFlowAdvertyModel(InfoFlowAdvertyModel infoFlowAdvertyModel)
    {
        this.infoFlowAdvertyModel = infoFlowAdvertyModel;
    }

    public void setMeterailModel(MeterailModel.data meterailModel)
    {
        this.meterailModel = meterailModel;
    }

}

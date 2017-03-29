package com.baiyigame.adslibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.Utils.DisplayUtils;
import com.baiyigame.adslibrary.Utils.ScreenUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.adapter.MyAutoBannerAdapter;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.model.OpenAdvertyModel;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.port.OnViewPagerScrollFinishListener;
import com.baiyigame.adslibrary.viewpager.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public class CoopenADSView extends BaseView implements View.OnClickListener,
        OnViewPagerScrollFinishListener
{

    private MeterailModel meterailModel = null;
    private OpenAdvertyModel openAdvertyModel = null;

    private OnCloseBtnClickListner listner = null;

    private View view = null;
    private MyViewPager customViewPager = null;
    private ImageView mImgClose = null;

    //存放图片或者视屏路径的集合
    private List<String> picUrls = null;
    //存放跳转路径的集合
    List<String> webUrls = null;

    private MyAutoBannerAdapter autoBannerAdapter = null;

    private  OpenAdvertyModel.data.style.c style = null;

    public CoopenADSView(Context context)
    {
        this(context,null);
    }

    public CoopenADSView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public CoopenADSView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Init view
     */
    public void init()
    {

        style = openAdvertyModel.getData().getStyle().getC();

        if (view == null)
        {
            LayoutInflater inflater = ContextUtils.getlayoutInfater(getContext());
            view = inflater.inflate(R.layout.coopen_ads_lauout,this,false);
            customViewPager = (MyViewPager) view.findViewById(R.id.auto_viewpager);
            mImgClose = (ImageView) view.findViewById(R.id.close);

            mImgClose.setOnClickListener(this);

            customViewPager.setScrollFinishListener(this);
        }

        delayShowClose(mImgClose,style.getDelay());

        initData();
        adapterViewPager();
        closeStyle();

        addView(view);
    }

    private void initData()
    {
        List<MeterailModel.data> datas = meterailModel.getData();
        if (webUrls == null)
        {
            webUrls = new ArrayList<String>();
        }
        else
        {
            webUrls.clear();
        }
        if (picUrls == null)
        {
            picUrls = new ArrayList<String>();
        }
        else
        {
            picUrls.clear();
        }

        for (int i = 0; i < datas.size(); i++) {
            MeterailModel.data data = datas.get(i);
            List<String> pics = data.getPicurl();
            if (!Utils.isListEmpty(pics))
            {
                picUrls.add(pics.get(0));
                webUrls.add(data.getWeburl());
            }
        }
    }

    private void adapterViewPager()
    {
        autoBannerAdapter = new MyAutoBannerAdapter(getContext());
        autoBannerAdapter.changeItems(picUrls);
        autoBannerAdapter.setWebUriList(webUrls);
        customViewPager.setNum(picUrls.size());
        customViewPager.setAdapter(autoBannerAdapter);
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

    public void setOpenAdvertyModel(OpenAdvertyModel openAdvertyModel)
    {
        this.openAdvertyModel = openAdvertyModel;
    }

    public void setListner(OnCloseBtnClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.close) {
            if (listner != null) {
                listner.onCloseClick();
            }

        }
    }

    @Override
    public void onScrllFinished()
    {
        if (listner != null) {
            listner.onCloseClick();
        }
    }
}

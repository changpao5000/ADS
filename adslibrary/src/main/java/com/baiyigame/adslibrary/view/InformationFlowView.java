package com.baiyigame.adslibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.config.MatconType;
import com.baiyigame.adslibrary.model.InfoFlowAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.ADSLoaderListener;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.view.flowview.OneBigPicStreamView;
import com.baiyigame.adslibrary.view.flowview.OneSmallPicStreamView;
import com.baiyigame.adslibrary.view.flowview.TextStreamView;
import com.baiyigame.adslibrary.view.flowview.ThreeSmallPicStreamView;
import com.baiyigame.adslibrary.view.flowview.TwoMiddlePicStreamView;


/**
 * 信息流广告 View
 * Created by Administrator on 2017/3/13.
 */

public class InformationFlowView extends RelativeLayout
{
    public static final String TAG = InformationFlowView.class.getSimpleName();
    
    private InfoFlowAdvertyModel infoFlowAdvertyModel = null;
    private MeterailModel.data meterailModel = null;

    private String matconType ;

   private OnCloseBtnClickListner listener = null;

    private View view = null;
    private RelativeLayout mRootStreamView;

    private RelativeLayout.LayoutParams params = null;


    public InformationFlowView(Context context)
    {
        this(context,null);
    }

    public InformationFlowView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public InformationFlowView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init(ADSLoaderListener loaderListener)
    {

        if (view == null)
        {
            LayoutInflater inflater = ContextUtils.getlayoutInfater(getContext());
            view = inflater.inflate(R.layout.information_flow_layout,this,false);
            mRootStreamView = (RelativeLayout) view.findViewById(R.id.root_stream_view);
        }

        mRootStreamView.removeAllViews();
        if (MatconType.None.equals(matconType))
        {
            //没有图片  只显示文字
            TextStreamView streamView = new TextStreamView(getContext());
            streamView.setMeterailModel(meterailModel);
            streamView.setInfoFlowAdvertyModel(infoFlowAdvertyModel);
            streamView.init();
            mRootStreamView.addView(streamView);
        }
        else if (MatconType.OneSmall.equals(matconType))
        {
            //只有一张小图
            OneSmallPicStreamView streamView = new OneSmallPicStreamView(getContext());
            streamView.setMeterailModel(meterailModel);
            streamView.setInfoFlowAdvertyModel(infoFlowAdvertyModel);
            streamView.init();
            mRootStreamView.addView(streamView);
        }
        else if (MatconType.OneBig.equals(matconType))
        {
            //只有一张大图
            OneBigPicStreamView streamView = new OneBigPicStreamView(getContext());
            streamView.setMeterailModel(meterailModel);
            streamView.setInfoFlowAdvertyModel(infoFlowAdvertyModel);
            streamView.init();
            mRootStreamView.addView(streamView);
        }
        else if (MatconType.TwoMiddle.equals(matconType))
        {
            //两张中图片
            TwoMiddlePicStreamView streamView = new TwoMiddlePicStreamView(getContext());
            streamView.setMeterailModel(meterailModel);
            streamView.setInfoFlowAdvertyModel(infoFlowAdvertyModel);
            streamView.init();
            mRootStreamView.addView(streamView);
        }
        else if (MatconType.ThreeSmall.equals(matconType))
        {
            //三张小图
            ThreeSmallPicStreamView streamView = new ThreeSmallPicStreamView(getContext());
            streamView.setMeterailModel(meterailModel);
            streamView.setInfoFlowAdvertyModel(infoFlowAdvertyModel);
            streamView.init();
            mRootStreamView.addView(streamView);
        }

        removeAllViews();
        addView(view);

        if (loaderListener != null)
        {
            loaderListener.onSuccess();
        }
    }

    public void setInfoFlowAdvertyModel(InfoFlowAdvertyModel infoFlowAdvertyModel)
    {
        this.infoFlowAdvertyModel = infoFlowAdvertyModel;
    }

    public void setMeterailModel(MeterailModel.data meterailModel)
    {
        this.meterailModel = meterailModel;
    }

    public void setMatconType(String matconType)
    {
        this.matconType = matconType;
    }
}

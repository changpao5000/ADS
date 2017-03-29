package com.baiyigame.adslibrary.view.flowview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.Utils.IntentUtils;
import com.baiyigame.adslibrary.animation.LayoutAnimation;
import com.baiyigame.adslibrary.config.TextStreamModel;
import com.baiyigame.adslibrary.model.InfoFlowAdvertyModel;

/**
 * 两种排版
 * 一、标题  副标题  内容
 * 二、标题  副标题
 * Created by Administrator on 2017/3/20.
 */

public class TextStreamView extends BaseStreamView implements View.OnClickListener
{

    private View view = null;

    private TextView mTitle = null;
    private TextView mSubTitle = null;
    private TextView mContent = null;

    private  InfoFlowAdvertyModel.data.style.pu pu = null;

    private  InfoFlowAdvertyModel.data.style.no no = null;

    public TextStreamView(Context context)
    {
        this(context, null);
    }

    public TextStreamView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TextStreamView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {

        pu = infoFlowAdvertyModel.getData().getStyle().getPu();
        no = infoFlowAdvertyModel.getData().getStyle().getNo();

        if (view == null) {
            view = ContextUtils.getlayoutInfater(getContext())
                    .inflate(R.layout.text_stream_view, this, false);
            mTitle = (TextView) view.findViewById(R.id.title);
            mSubTitle = (TextView) view.findViewById(R.id.subtitle);
            mContent = (TextView) view.findViewById(R.id.content);
        }

        mTitle.setText(meterailModel.getName());
        mSubTitle.setText(meterailModel.getSubtitle());

        if (TextStreamModel.Model_01.equals(no.getP()))
        {
            mContent.setVisibility(VISIBLE);
            mContent.setText(meterailModel.getMatcon());
            contentStyle(pu);
        }
        else
        {
            mContent.setVisibility(GONE);
        }

        style();
        addView(view);

        this.setOnClickListener(this);
    }

    private void style()
    {

        setViewStyle(pu);

        titleStyle(mTitle,pu);
        subTitleStyle(mSubTitle,pu);

    }

    private void contentStyle(InfoFlowAdvertyModel.data.style.pu pu)
    {
        mContent.setTextSize(pu.getCs());
        mContent.setTextColor(ImageUtils.toColorFromString(pu.getCc()));
        //字间距
        mContent.setTextScaleX(pu.getCts());
        mContent.setPadding((int) pu.getClm(),(int) pu.getCtm(),(int) pu.getClm(),(int) pu.getCtm());
    }

    @Override
    public void onClick(View v)
    {
        LayoutAnimation.getInstence().clickAnim(this);
        IntentUtils.goWeb(getContext(), meterailModel.getWeburl());
    }
}

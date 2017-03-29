package com.baiyigame.adslibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;


/**
 * Created by Administrator on 2017/3/9.
 */

public class AutoRollRelativeLayout extends RelativeLayout
{


    public AutoRollRelativeLayout(Context context)
    {
        super(context);
    }

    public AutoRollRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AutoRollRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private View view;
    private RelativeLayout layout_anim_1;
    private RelativeLayout layout_anim_2;

    private Animation animation1;
    private Animation animation2;

    private TextView title;
    private TextView subTitle;
    private TextView matcon;

    private BannerInfoAdvertyModel bannerInfoAdvertyModel;
    private MeterailModel meterailModel;

    private int index = 0;

    public static final int Run_Delay = 3000;

    public void initView()
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.auto_layout, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);

        layout_anim_1 = (RelativeLayout) view.findViewById(R.id.re_anim1);
        layout_anim_2 = (RelativeLayout) view.findViewById(R.id.re_anim2);
        layout_anim_2.setVisibility(GONE);

        title = (TextView) view.findViewById(R.id.title);
        subTitle = (TextView) view.findViewById(R.id.subtitle);
        matcon = (TextView) view.findViewById(R.id.matcon);

    }

    public void updateUI()
    {
        title.setText(meterailModel.getData().get(index).getName());
        subTitle.setText(meterailModel.getData().get(index).getSubtitle());
        matcon.setText(meterailModel.getData().get(index).getMatcon());
        titleStyle();
        subTitleStyle();
        matconStyle();
    }

    public void startAnimation()
    {
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                firstAnim();
            }
        },Run_Delay);
    }
    public void firstAnim()
    {
        if (animation1 == null) {
          /*
         * 设置滚动的动画，从底部出来，从上部消失
		 */
            layout_anim_1.setAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_vp_in_bottom));
            layout_anim_1.setAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_vp_out_top));

            animation1 = layout_anim_1.getAnimation();

            animation1.setDuration(1000);
            animation1.setRepeatCount(10000);
            animation1.setRepeatMode(Animation.RESTART);
            animation1.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {
                }

                @Override
                public void onAnimationEnd(Animation animation)
                {

                    layout_anim_1.setVisibility(GONE);
                    layout_anim_2.setVisibility(VISIBLE);
                    postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            secondAnim();
                        }
                    }, Run_Delay);
                }
                @Override
                public void onAnimationRepeat(Animation animation)
                {
                }
            });
        }
        layout_anim_1.startAnimation(animation1);
    }

    private void secondAnim()
    {
        if (animation2 == null)
        {
            layout_anim_2.setAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_vp_in_bottom));
            layout_anim_2.setAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_vp_out_top));

            animation2 = layout_anim_2.getAnimation();

            animation2.setDuration(1000);
            animation2.setRepeatCount(10000);
            animation2.setRepeatMode(Animation.RESTART);
            animation2.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationStart(Animation animation)
                {
                }

                @Override
                public void onAnimationEnd(Animation animation)
                {

                    layout_anim_2.setVisibility(GONE);
                    layout_anim_1.setVisibility(VISIBLE);
                    postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            firstAnim();
                        }
                    }, Run_Delay);
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }
            });
        }
        layout_anim_2.startAnimation(animation2);
    }

    private void matconStyle()
    {
        BannerInfoAdvertyModel.data.style.t style = bannerInfoAdvertyModel.getData().getStyle().getT();
        matcon.setTextSize(style.getRs());
        matcon.setBackgroundColor(Color.parseColor(style.getBgc()));
        Drawable drawable = matcon.getBackground();
        drawable.setAlpha(0);
        matcon.setTextColor(Color.parseColor(style.getRc()));

        LayoutParams layoutParams = new LayoutParams(//
                ViewGroup.LayoutParams.WRAP_CONTENT,//
                /*DisplayUtils.dip2px(getContext(),style.getH())*/ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(style.getL() * 15
                , 20, 0, 0);
        matcon.setLayoutParams(layoutParams);
    }

    private void subTitleStyle()
    {
        BannerInfoAdvertyModel.data.style.t style = bannerInfoAdvertyModel.getData().getStyle().getT();
        subTitle.setTextSize(style.getSts());
        subTitle.setBackgroundColor(Color.parseColor(style.getBgc()));
        Drawable drawable = subTitle.getBackground();
        drawable.setAlpha(0);
        subTitle.setTextColor(Color.parseColor(style.getStc()));

        LayoutParams layoutParams = new LayoutParams(//
                ViewGroup.LayoutParams.MATCH_PARENT,//
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(style.getL() * 15
                , 60, 0, 0);
        subTitle.setLayoutParams(layoutParams);
    }

    private void titleStyle()
    {
        BannerInfoAdvertyModel.data.style.t style = bannerInfoAdvertyModel.getData().getStyle().getT();
        title.setTextSize(style.getTs());
        title.setBackgroundColor(Color.parseColor(style.getBgc()));
        Drawable drawable = title.getBackground();
        drawable.setAlpha(0);
        title.setTextColor(Color.parseColor(style.getTc()));

        LayoutParams layoutParams = new LayoutParams(//
                ViewGroup.LayoutParams.MATCH_PARENT,//
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(style.getL() * 15,
                10, 0, 0);
        title.setLayoutParams(layoutParams);
    }

    public void setBannerInfoAdvertyModel(BannerInfoAdvertyModel bannerInfoAdvertyModel)
    {
        this.bannerInfoAdvertyModel = bannerInfoAdvertyModel;
    }

    public void setMeterailModel(MeterailModel meterailModel)
    {
        this.meterailModel = meterailModel;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void cancelAnim()
    {
        if (animation1 != null)
        {
            animation1.cancel();
        }
        if (animation2 != null)
        {
            animation2.cancel();
        }
    }
}

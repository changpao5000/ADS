package com.baiyigame.adslibrary.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.activities.CoopenActivity;
import com.baiyigame.adslibrary.port.OnViewPagerScrollFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class MyViewPager extends RelativeLayout
{
    private static final String TAG = MyViewPager.class.getSimpleName();
    private View view ;

    /** save view */
    private NoPrestrainViewPager mViewPager;
    private LinearLayout dotContainer;
    // small point
    private List<ImageView> mImageViews ;

    private OnViewPagerScrollFinishListener scrollFinishListener = null;

    public MyViewPager(Context context)
    {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
        init();
    }

    private void init()
    {
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.auto_viewpager ,this,false);
            mViewPager = (NoPrestrainViewPager) view.findViewById(R.id.viewPager);

            dotContainer = (LinearLayout) view.findViewById(R.id.dotContainer);
            dotContainer.setGravity(Gravity.CENTER);
            mImageViews = new ArrayList<>();

            mViewPager.setOnPageChangeListener(new AutoBannerChangeListener());
            pagerAdapter = new BannerPagerAdapter();
            mViewPager.setAdapter(pagerAdapter);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(view,params);
        }
    }

    /**
     * set adapter
     * @param adapter AutoBannerAdapter
     */
    public void setAdapter(@NonNull MyViewPager.AutoBannerAdapter adapter)
    {
        this.dotContainer.removeAllViews();
        this.mImageViews.clear();
        this.autoBannerAdapter = adapter;
        int count = PAGER_MAX_VALUE;
        if (count == 0)
        {
            return;
        }
        for (int i = 0; i < count; i++)
        {
            ImageView dotImage = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            dotImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            dotImage.setLayoutParams(params);
            if (i == 0)
            {
                dotImage.setBackgroundResource(R.mipmap.radio_red);
            }
            else
            {
                dotImage.setBackgroundResource(R.mipmap.radio_hui);
            }
            mImageViews.add(dotImage);
            dotContainer.addView(dotImage);
        }
        pagerAdapter.notifyDataSetChanged();
    }

    public void setNum(int num)
    {
        this.PAGER_MAX_VALUE = num;
    }

    private int pos  = 0;

    private class AutoBannerChangeListener implements NoPrestrainViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            CoopenActivity.isAutoClose = false;
        }

        @Override
        public void onPageSelected(int position)
        {

            pos = position;

            if (mImageViews.size() == 0) {
                return;
            }
//            int pos = position % mImageViews.size();
            mImageViews.get(position).setBackgroundResource(R.mipmap.radio_red);
            for (int i = 0; i < mImageViews.size(); i++) {
                if (position != i) {
                    mImageViews.get(i).setBackgroundResource(R.mipmap.radio_hui);
                }
            }
        }

        @Override
        public void onPageIsLastScroll(boolean isLastPageScroll)
        {
            if (isLastPageScroll && pos > 0)
            {
                if (scrollFinishListener != null)
                    {
                        scrollFinishListener.onScrllFinished();
                    }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            //Log.e(TAG,"state ----------------------> "+state);
        }
    }

    private static int PAGER_MAX_VALUE = 10000;

    /** ViewPagerAdapter */
    private BannerPagerAdapter pagerAdapter;

    private class BannerPagerAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            return PAGER_MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            if (autoBannerAdapter == null || autoBannerAdapter.getCount() == 0)
            {
                return null;
            }
            View view = autoBannerAdapter.getView(null,container, position);
            container.addView(view);
            notifyDataSetChanged();
            mViewPager.invalidate();
            mViewPager.postInvalidate();
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
    }

    private MyViewPager.AutoBannerAdapter autoBannerAdapter;

    /**
     * Shuffling adapter interface layout
     */
    public interface AutoBannerAdapter
    {
        int getCount();
        View getView(View convertView, ViewGroup container, int position);
    }

    public void setScrollFinishListener(OnViewPagerScrollFinishListener scrollFinishListener)
    {
        this.scrollFinishListener = scrollFinishListener;
    }
}

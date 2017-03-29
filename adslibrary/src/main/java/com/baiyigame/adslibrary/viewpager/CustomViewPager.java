package com.baiyigame.adslibrary.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baiyigame.adslibrary.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class CustomViewPager extends LinearLayout
{
    private View view ;

    /** 存放视图 */
    private NoPrestrainViewPager mViewPager;
    private LinearLayout dotContainer;
    // 小点
    private List<ImageView> mImageViews ;

    public CustomViewPager(Context context)
    {
        this(context,null);
    }

    public CustomViewPager(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.auto_viewpager ,this,false);
            mViewPager = (NoPrestrainViewPager) view.findViewById(R.id.viewPager);
            mViewPager.setOffscreenPageLimit(0);
            dotContainer = (LinearLayout) view.findViewById(R.id.dotContainer);
            dotContainer.setGravity(Gravity.CENTER);
            mImageViews = new ArrayList<>();
            mViewPager.setOnTouchListener(new OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    switch (motionEvent.getAction())
                    {
                        case MotionEvent.ACTION_UP:
                            // 开始图片滚动
                            startImageTimerTask();
                            break;
                        default:
                            // 停止图片滚动
                            stopImageTimerTask();
                            break;
                    }
                    return false;
                }
            });
            mViewPager.setOnPageChangeListener(new AutoBannerChangeListener());
            pagerAdapter = new BannerPagerAdapter();
            mViewPager.setAdapter(pagerAdapter);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(view,params);
        }
    }
    /**
     * 开始轮播
     */
    public void startImageTimerTask()
    {
//        if (mImageViews.size() > 1) {
//            isRunning = true;
//            mHandler.removeMessages(AUTO_START);
//            mHandler.sendEmptyMessageDelayed(AUTO_START, mWaitMillisecond);
//        }
    }

    /**
     * 停止轮播
     */
    public void stopImageTimerTask()
    {
        mHandler.removeMessages(AUTO_START);
        isRunning = false;
    }
    /** 轮播状态 */
    private boolean isRunning = false;

    /** 轮播间隔 */
    private int mWaitMillisecond = 3000;

    private static final int AUTO_START = 0;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case AUTO_START:
                    if (isRunning)
                    {
                        if (mImageViews.size() != 0)
                        {
                            if (mViewPager.getCurrentItem() == PAGER_MAX_VALUE - 1)
                            {
                                mViewPager.setCurrentItem(0);
                            }
                            else
                            {
                                mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1));
                            }
                            mHandler.sendEmptyMessageDelayed(AUTO_START, mWaitMillisecond);
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 设置适配器
     * @param adapter AutoBannerAdapter
     */
    public void setAdapter(@NonNull AutoBannerAdapter adapter)
    {
        stopImageTimerTask();
        this.dotContainer.removeAllViews();
        this.mImageViews.clear();
        this.autoBannerAdapter = adapter;
        int count = autoBannerAdapter.getCount();
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
            if (i == 0) {
                dotImage.setBackgroundResource(R.mipmap.radio_red);
            } else {
                dotImage.setBackgroundResource(R.mipmap.radio_hui);
            }
            mImageViews.add(dotImage);
            dotContainer.addView(dotImage);
        }
        int offset = (PAGER_MAX_VALUE / 2) % count;//计算偏移量
        pagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(PAGER_MAX_VALUE / 2 - offset, false);//从Integer.MAX_VALUE的中间开始加载，确保左滑右滑都能ok
        if (count > 1) {
            startImageTimerTask();
        }
    }

    public void setNum(int num)
    {
        this.PAGER_MAX_VALUE = num;
    }

    private class AutoBannerChangeListener implements NoPrestrainViewPager.OnPageChangeListener {
       @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

                @Override
        public void onPageSelected(int position)
                {
           if (mImageViews.size() == 0)
           {
               return;
               }
          int pos = position % mImageViews.size();
            mImageViews.get(pos).setBackgroundResource(R.mipmap.radio_red);
            for (int i = 0; i < mImageViews.size(); i++)
            {
               if (pos != i)
               {
                   mImageViews.get(i).setBackgroundResource(R.mipmap.radio_hui);
                  }
              }
          if (onBannerChangeListener != null)
          {
               onBannerChangeListener.onCurrentItemChanged(pos);
               }
         }
        @Override
       public void onPageScrollStateChanged(int state)
        {
           if (state == NoPrestrainViewPager.SCROLL_STATE_IDLE)
            startImageTimerTask();
            }

        @Override
        public void onPageIsLastScroll(boolean isLastPageScroll)
        {

        }
    }

    /**
     * banner改变时的回调
     */
    public interface OnBannerChangeListener
    {
        void onCurrentItemChanged(int position);
    }

    private OnBannerChangeListener onBannerChangeListener = null;
    /**
     * 设置轮播图内容切换监听
     * @param onBannerChangeListener OnBannerChangeListener
     */
    public void setOnBannerChangeListener(OnBannerChangeListener onBannerChangeListener)
    {
        this.onBannerChangeListener = onBannerChangeListener;
    }

    private static int PAGER_MAX_VALUE = 10000;

    /** ViewPagerAdapter */
    private BannerPagerAdapter pagerAdapter;

    private class BannerPagerAdapter extends PagerAdapter
    {

        LinkedList<View> cacheList = new LinkedList<>();//缓存机制

        @Override
        public int getCount()
        {
            return PAGER_MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            View view = (View) object;
            container.removeView(view);
            cacheList.push(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            if (autoBannerAdapter == null || autoBannerAdapter.getCount() == 0)
            {
                return null;
            }
            int offset = position % autoBannerAdapter.getCount();
            View view;
            if (cacheList.size() == 0)
            {
                view = autoBannerAdapter.getView(null,container, offset);
            }
            else
            {
                //poll为删除list最后一个实体并取出,peek则是不删除list中对应的实体
                view = autoBannerAdapter.getView(cacheList.pollLast(),container, offset);
            }
            
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
    }

    private AutoBannerAdapter autoBannerAdapter;

    /**
     * 轮播布局适配器接口
     */
    public interface AutoBannerAdapter
    {
        int getCount();
        View getView(View convertView, ViewGroup container, int position);
    }
}

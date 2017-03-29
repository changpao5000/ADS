package com.baiyigame.adslibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.Utils.IntentUtils;
import com.baiyigame.adslibrary.Utils.Utils;
import com.baiyigame.adslibrary.animation.LayoutAnimation;
import com.baiyigame.adslibrary.manager.ImageManager;
import com.baiyigame.adslibrary.manager.VideoPlayerManager;
import com.baiyigame.adslibrary.port.IVideoPrepareComplete;
import com.baiyigame.adslibrary.port.ImageLoaderListener;
import com.baiyigame.adslibrary.view.CoopenImageView;
import com.baiyigame.adslibrary.view.video.MyVideoView;
import com.baiyigame.adslibrary.viewpager.MyViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Opening the advertising adapter
 * Created by Administrator on 2017/3/6.
 */

public class MyAutoBannerAdapter implements MyViewPager.AutoBannerAdapter
{
    private static final String TAG = MyAutoBannerAdapter.class.getSimpleName();

    private List<String> urls;
    private Context context;
    private List<String> webUriList;

    public MyAutoBannerAdapter(Context context)
    {
        this.context = context;
        this.urls = new ArrayList<>();
    }

    public void changeItems(@NonNull List<String> urls)
    {
        this.urls.clear();
        this.urls.addAll(urls);
    }

    @Override
    public int getCount()
    {
        return urls.size();
    }

    private MyVideoView videoView = null;
    private CoopenImageView imageView = null;

    Map<Integer,View> maps = new HashMap<Integer, View>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(View convertView, ViewGroup container, final int position)
    {
        String url = urls.get(position);

            if (ImageUtils.isPicture(url)) {
                //image
                imageView = (CoopenImageView) maps.get(position);
                if (imageView == null)
                {
                    imageView = new CoopenImageView(context);
                    loadImg(url, imageView);
                    imageView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            LayoutAnimation.getInstence().clickAnim(imageView);
                            IntentUtils.goWeb(context, webUriList.get(position));
                        }
                    });
                    maps.put(position,imageView);
                }
                return imageView;
            }
            else
            {
                //video
                videoView = (MyVideoView) maps.get(position);
                if (videoView == null)
                {
                    videoView = new MyVideoView(context);
                    playVideo(videoView, url);
                    videoView.setWebUrl(webUriList.get(position));
                    maps.put(position,videoView);
                }
                return videoView;
            }
    }

    private void playVideo(final MyVideoView videoView, String url)
    {
        if (Utils.isStringEmpty(url))
        {
            return;
        }
        VideoPlayerManager.getInstence(context).play(url)//
                .setiVideoPrepareComplete(new IVideoPrepareComplete()
                {
                    @Override
                    public void onComplete(final String path)
                    {
//                        Log.e("Response path:", path);
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                videoView.play(path);
                            }
                        }, 0);
                        videoView.postInvalidateDelayed(0);
                    }

                    @Override
                    public void onError(String errorMsg, int resposeCode)
                    {
                        Log.e("Response error:", errorMsg + ":" + resposeCode);
                    }
                });
    }

    Handler handler = new Handler();

    private void loadImg(String imgUrl, final CoopenImageView mImg)
    {
        ImageManager manager = new ImageManager(context);
        manager.setImageLoaderListener(new ImageLoaderListener()
        {
            @Override
            public void onSuccess(View view, final Bitmap bitmap, final String path)
            {
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mImg.setBitmap(bitmap);
                    }
                });
                mImg.postInvalidateDelayed(0);
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
        manager.request(imgUrl, null);
    }

    public void setWebUriList(List<String> webUriList)
    {
        this.webUriList = webUriList;
    }
}

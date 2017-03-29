package com.baiyigame.adslibrary.view.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/3/8.
 */

public class NormalScreenVideoView extends VideoView implements MediaPlayer.OnErrorListener {

    public NormalScreenVideoView(Context context) {
        this(context, null);
    }

    public NormalScreenVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOnErrorListener(this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getDefaultSize(0,widthMeasureSpec);
//        int hegth = getDefaultSize(0,heightMeasureSpec);
//        setMeasuredDimension(width,hegth);
    }

    public void play(final String videoPath) {
        setVideoPath(videoPath);
        start();
        postInvalidate();
        invalidate();
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                setVideoPath(videoPath);
                start();

            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    public void pauseVideo()
    {
        if (isPlaying())
        {
            this.pause();
        }
    }
}

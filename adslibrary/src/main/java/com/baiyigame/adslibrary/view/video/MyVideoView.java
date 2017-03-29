package com.baiyigame.adslibrary.view.video;//package com.baiyigame.ads.view.video;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.Utils.IntentUtils;
import com.baiyigame.adslibrary.animation.LayoutAnimation;

//import android.content.res.Resources;


/**
 * Created by Administrator on 2017/3/14.
 */

public class MyVideoView extends RelativeLayout implements View.OnClickListener {

    //url
    private String webUrl = "";

    public MyVideoView(Context context) {
        this(context, null);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(Color.BLACK);
        init();
    }

    ProgressBar loading;
    NormalScreenVideoView videoView;

    private void init() {
        View view = ContextUtils.getlayoutInfater(getContext())
                .inflate(R.layout.myvideo, this, false);
        videoView = (NormalScreenVideoView) view.findViewById(R.id.myvi);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        videoView.setVisibility(GONE);
        loading.setVisibility(VISIBLE);
        this.addView(view, -1, -1);

        this.setOnClickListener(this);
    }

    public void play(String videoPath) {
        videoView.setVisibility(VISIBLE);
        videoView.play(videoPath);
        loading.setVisibility(GONE);
    }

    public void pause() {
        synchronized (this) {
            videoView.pause();
        }
    }

    public void start() {
        synchronized (this) {
            if (!videoView.isPlaying()) {
                videoView.start();
            }
        }
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public void onClick(View v) {
        LayoutAnimation.getInstence().clickAnim(this);
        IntentUtils.goWeb(getContext(), webUrl);
    }
}

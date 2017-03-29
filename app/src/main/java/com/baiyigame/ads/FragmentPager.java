package com.baiyigame.ads;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiyigame.adslibrary.Utils.ImageUtils;
import com.baiyigame.adslibrary.imageview.RectangleImageView;
import com.baiyigame.adslibrary.view.video.NormalScreenVideoView;

/**
 * Created by Administrator on 2017/3/14.
 */

public class FragmentPager extends Fragment
{

    private RectangleImageView imageView ;
    private NormalScreenVideoView videoView ;

    private String url = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager,container,false);
        imageView = (RectangleImageView) view.findViewById(R.id.fragment_img);
        videoView = (NormalScreenVideoView) view.findViewById(R.id.fragment_video);
        Log.d("FragmentPager  url : ",url);
        if (ImageUtils.isPicture(url))
        {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
        }else
        {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

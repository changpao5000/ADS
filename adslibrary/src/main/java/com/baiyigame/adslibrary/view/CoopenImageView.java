package com.baiyigame.adslibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.ContextUtils;
import com.baiyigame.adslibrary.imageview.RectangleImageView;

/**
 * Created by Administrator on 2017/3/17.
 */

public class CoopenImageView extends RelativeLayout
{
    public CoopenImageView(Context context)
    {
        this(context,null);
    }

    public CoopenImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public CoopenImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init();
    }

    private View view;
    private RectangleImageView imageView;
    private ProgressBar loading;

    private void init()
    {
        view = ContextUtils.getlayoutInfater(getContext())
                .inflate(R.layout.coopen_img,this,false);

        imageView = (RectangleImageView) view.findViewById(R.id.img_coopen);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        loading.setVisibility(VISIBLE);
        imageView.setVisibility(GONE);

        this.addView(view,-1,-1);
    }
    public void setBitmap(Bitmap bitmap)
    {
        imageView.setVisibility(VISIBLE);
        imageView.setImageBitmap(bitmap);
        loading.setVisibility(GONE);
    }
}

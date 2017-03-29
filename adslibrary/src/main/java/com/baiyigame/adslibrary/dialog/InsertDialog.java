package com.baiyigame.adslibrary.dialog;

import android.content.Context;
import android.view.ViewGroup;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.base.BaseDialog;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.view.InsertADSView;

/**
 * Plaque advertising display box
 * Created by Administrator on 2017/3/3.
 */

public class InsertDialog extends BaseDialog implements OnCloseBtnClickListner
{

    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private MeterailModel meterailModel = null;
    private int index = 0;

    public InsertDialog(Context context)
    {
        this(context,0);
    }
    public InsertDialog(Context context, int themeResId)
    {
        super(context, R.style.Dialog_Insert_Fullscreen);
    }

    /**
     * show view
     */
    private void showView()
    {
        InsertADSView view = new InsertADSView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setBannerInfoAdvertyModel(bannerInfoAdvertyModel);
        view.setMeterailModel(meterailModel);
        view.setIndex(index);
        view.init();
        view.setListner(this);

        addContentView(view, params);
    }
    public void init()
    {
        showView();
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public void onCloseClick()
    {
        this.cancel();
    }

    public void setBannerInfoAdvertyModel(BannerInfoAdvertyModel bannerInfoAdvertyModel)
    {
        this.bannerInfoAdvertyModel = bannerInfoAdvertyModel;
    }

    public void setMeterailModel(MeterailModel meterailModel)
    {
        this.meterailModel = meterailModel;
    }
}

package com.baiyigame.adslibrary.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.baiyigame.adslibrary.R;
import com.baiyigame.adslibrary.Utils.DisplayUtils;
import com.baiyigame.adslibrary.base.BaseDialog;
import com.baiyigame.adslibrary.model.BannerInfoAdvertyModel;
import com.baiyigame.adslibrary.model.MeterailModel;
import com.baiyigame.adslibrary.port.OnCloseBtnClickListner;
import com.baiyigame.adslibrary.port.OnDialogCanaleListner;
import com.baiyigame.adslibrary.view.BannerADSView;


/**
 * Banner display dialog
 * Created by Administrator on 2017/3/6.
 */

public class BannerDialog extends BaseDialog implements OnCloseBtnClickListner
{


    private BannerInfoAdvertyModel bannerInfoAdvertyModel = null;
    private MeterailModel meterailModel = null;

    private OnDialogCanaleListner dialogCanaleListner;

    private int index = 0;

    public BannerDialog(Context context)
    {
        this(context,0);
    }

    public BannerDialog(Context context, int themeResId)
    {
        super(context,  R.style.Dialog_Banner_Fullscreen);
    }


    /**
     * show view
     */
    private void showView()
    {
        BannerADSView view = new BannerADSView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                lpHeight);
        addContentView(view, params);

        view.setBannerInfoAdvertyModel(bannerInfoAdvertyModel);
        view.setMeterailModel(meterailModel);
        view.setIndex(index);
        view.init();
        view.setListner(BannerDialog.this);
    }

    private int lpHeight;
    public void init()
    {

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        BannerInfoAdvertyModel.data.style.b style = bannerInfoAdvertyModel.getData().getStyle().getB();
        lp.height = DisplayUtils.dip2px(getContext(),style.getH());
        //Dialog dismiss focue
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        lpHeight = lp.height;
        dialogWindow.setAttributes(lp);
        showView();
        }

    @Override
    public void onCloseClick()
    {
        this.cancel();
        if (dialogCanaleListner != null)
        {
            dialogCanaleListner.onCancel();
        }
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

    public void setDialogCanaleListner(OnDialogCanaleListner dialogCanaleListner)
    {
        this.dialogCanaleListner = dialogCanaleListner;
    }
}

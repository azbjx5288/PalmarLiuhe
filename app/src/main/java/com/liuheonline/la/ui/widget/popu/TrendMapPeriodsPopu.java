package com.liuheonline.la.ui.widget.popu;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.ViewUtil;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/8/1 15:20
 * @description 选择期数
 */
public class TrendMapPeriodsPopu extends BasePopupWindow implements View.OnClickListener{

    private View periodsVeiew01;
    private View periodsVeiew02;
    private View periodsVeiew03;
    private View periodsVeiew04;

    private OnViewClickListener onViewClickListener;

    public TrendMapPeriodsPopu(Context context, OnViewClickListener onViewClickListener) {
        super(context, Dip2pxUtil.dip2px(context,180f),Dip2pxUtil.dip2px(context,160f));
        this.onViewClickListener = onViewClickListener;
        periodsVeiew01 = findViewById(R.id.periods01);
        periodsVeiew02 = findViewById(R.id.periods02);
        periodsVeiew03 = findViewById(R.id.periods03);
        periodsVeiew04 = findViewById(R.id.periods04);
        ViewUtil.setViewsClickListener(this,periodsVeiew01, periodsVeiew02,periodsVeiew03,periodsVeiew04);
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(-1f, 0, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, -1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_trendmapperiods);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_trendperiods_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.periods01:
                onViewClickListener.onVeiwCilck(50);
                break;
            case R.id.periods02:
                onViewClickListener.onVeiwCilck(100);
                break;
            case R.id.periods03:
                onViewClickListener.onVeiwCilck(200);
                break;
            case R.id.periods04:
                onViewClickListener.onVeiwCilck(500);
                break;
        }
        dismissWithOutAnima();
    }

    public interface OnViewClickListener {
        void onVeiwCilck(int periods);
    }
}

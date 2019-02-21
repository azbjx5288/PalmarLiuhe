package com.liuheonline.la.ui.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RadioGroup;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.utils.Dip2pxUtil;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/8/1 15:20
 * @description 选择期数
 */
public class PeriodsPopu extends BasePopupWindow{

    private RadioGroup rGissueView;

    public PeriodsPopu(Context context, OnViewClickListener onViewClickListener) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT,Dip2pxUtil.dip2px(context,160f));
        rGissueView = (RadioGroup) findViewById(R.id.rg_issue);
        rGissueView.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.rb_issue30:
                    onViewClickListener.onVeiwCilck(30);
                    break;
                case R.id.rb_issue50:
                    onViewClickListener.onVeiwCilck(50);
                    break;
                case R.id.rb_issue100:
                    onViewClickListener.onVeiwCilck(100);
                    break;
                case R.id.rb_issue200:
                    onViewClickListener.onVeiwCilck(200);
                    break;
            }
            dismiss();
        });
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
        return createPopupById(R.layout.popu_periods);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.rg_issue);
    }

    public interface OnViewClickListener {
        void onVeiwCilck(int issue);
    }
}

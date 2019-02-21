package com.liuheonline.la.ui.widget.popu;

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
public class DatesPopu extends BasePopupWindow{

    private RadioGroup rGissueView;

    public DatesPopu(Context context, OnViewClickListener onViewClickListener) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT,Dip2pxUtil.dip2px(context,320f));
        rGissueView = (RadioGroup) findViewById(R.id.rg_issue);
        rGissueView.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.rb_jintian:
                    onViewClickListener.onVeiwCilck("today");
                    break;
                case R.id.rb_zuotian:
                    onViewClickListener.onVeiwCilck("yesterday");
                    break;
                case R.id.rb_benzhou:
                    onViewClickListener.onVeiwCilck("week");
                    break;
                case R.id.rb_shangzhou:
                    onViewClickListener.onVeiwCilck("lastWeek");
                    break;
                case R.id.rb_benyue:
                    onViewClickListener.onVeiwCilck("month");
                    break;
                case R.id.rb_shangyue:
                    onViewClickListener.onVeiwCilck("lastMonth");
                    break;
                case R.id.rb_jinnian:
                    onViewClickListener.onVeiwCilck("year");
                    break;
                case R.id.rb_qunian:
                    onViewClickListener.onVeiwCilck("lastYear");
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
        return createPopupById(R.layout.popu_dates);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.rg_issue);
    }

    public interface OnViewClickListener {
        void onVeiwCilck(String issue);
    }
}

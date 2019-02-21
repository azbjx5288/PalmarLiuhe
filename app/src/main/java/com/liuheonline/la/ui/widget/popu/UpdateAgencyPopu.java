package com.liuheonline.la.ui.widget.popu;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.NoddEntity;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/8/1 15:20
 * @description 设置窗口
 */
public class UpdateAgencyPopu extends BasePopupWindow{

    private OnViewClickListener onViewClickListener;
    public UpdateAgencyPopu(Context context, OnViewClickListener onViewClickListener) {
        super(context);
        this.onViewClickListener = onViewClickListener;
    }


    public void showPopupWindow(View view,List<NoddEntity.ListBean> list) {
        super.showPopupWindow();
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0f, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0f, 1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popu_agencypicker);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.picker_root);
    }

    public interface OnViewClickListener {
        void onVeiwCilck(NoddEntity.ListBean listBean);
    }
}

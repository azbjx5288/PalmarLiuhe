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
 * @description 选择种类
 */
public class TrendMapTypePopu extends BasePopupWindow implements View.OnClickListener{

    private View typeSxVeiew;
    private View typeBsVeiew;
    private View typeWxVeiew;
    private View typeDxVeiew;
    private View typeDsVeiew;
    private View typeTsVeiew;
    private View typeWsVeiew;


    private OnViewClickListener onViewClickListener;

    public TrendMapTypePopu(Context context, OnViewClickListener onViewClickListener) {
        super(context, Dip2pxUtil.dip2px(context,180f),Dip2pxUtil.dip2px(context,280f));
        this.onViewClickListener = onViewClickListener;
        typeSxVeiew = findViewById(R.id.type_sx);
        typeBsVeiew = findViewById(R.id.type_bs);
        typeWxVeiew = findViewById(R.id.type_wx);
        typeDxVeiew = findViewById(R.id.type_dx);
        typeDsVeiew = findViewById(R.id.type_ds);
        typeTsVeiew = findViewById(R.id.type_ts);
        typeWsVeiew = findViewById(R.id.type_ws);
        ViewUtil.setViewsClickListener(this,typeSxVeiew, typeBsVeiew,typeWxVeiew,typeDxVeiew,typeDsVeiew,typeTsVeiew,
                typeWsVeiew);
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
        return createPopupById(R.layout.popup_trendmaptype);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_trendtype_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.type_sx:
                onViewClickListener.onVeiwCilck("zodiac");
                break;
            case R.id.type_bs:
                onViewClickListener.onVeiwCilck("color");
                break;
            case R.id.type_wx:
                onViewClickListener.onVeiwCilck("fiveelements");
                break;
            case R.id.type_dx:
                onViewClickListener.onVeiwCilck("size");
                break;
            case R.id.type_ds:
                onViewClickListener.onVeiwCilck("odevity");
                break;
            case R.id.type_ts:
                onViewClickListener.onVeiwCilck("head");
                break;
            case R.id.type_ws:
                onViewClickListener.onVeiwCilck("footer");
                break;
        }
        dismissWithOutAnima();
    }

    public interface OnViewClickListener {
        void onVeiwCilck(String type);
    }
}

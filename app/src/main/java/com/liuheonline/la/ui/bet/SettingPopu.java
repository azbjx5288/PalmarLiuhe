package com.liuheonline.la.ui.bet;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.ViewUtil;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/8/1 15:20
 * @description 设置窗口
 */
public class SettingPopu extends BasePopupWindow implements View.OnClickListener {

    private View trendView;
    private View recordingView;
    private View historyView;
    private View gameStyleView;
    private View pic;

    private OnViewClickListener onViewClickListener;

    public SettingPopu(Context context, int[] goneViewID, OnViewClickListener onViewClickListener) {
        super(context, Dip2pxUtil.dip2px(context, 120f), Dip2pxUtil.dip2px(context, setHeight(goneViewID)));
        init(context, onViewClickListener, goneViewID);
    }

    private static float setHeight(int[] goneViewID) {
        float size = 200f;
        if (goneViewID != null && goneViewID.length > 0) {
            size -= (goneViewID.length * 40f);
        }
        return size;
    }

    private void init(Context context, OnViewClickListener onViewClickListener, int... goneViewID) {
        this.onViewClickListener = onViewClickListener;
        trendView = findViewById(R.id.trend);
        recordingView = findViewById(R.id.recording);
        historyView = findViewById(R.id.history);
        gameStyleView = findViewById(R.id.game_style);
        pic = findViewById(R.id.pic);
        if (goneViewID != null && goneViewID.length > 0) {
            for (int aGoneViewID : goneViewID) {
                if (aGoneViewID == R.id.trend) {
                    trendView.setVisibility(View.GONE);
                    findViewById(R.id.trendView).setVisibility(View.GONE);
                }
                if (aGoneViewID == R.id.recording) {
                    recordingView.setVisibility(View.GONE);
                    findViewById(R.id.recordingView).setVisibility(View.GONE);
                }
                if (aGoneViewID == R.id.history) {
                    historyView.setVisibility(View.GONE);
                    findViewById(R.id.historyView).setVisibility(View.GONE);
                }
                if (aGoneViewID == R.id.game_style) {
                    gameStyleView.setVisibility(View.GONE);
                    findViewById(R.id.game_styleView).setVisibility(View.GONE);
                }
                if (aGoneViewID == R.id.pic) {
                    pic.setVisibility(View.GONE);
                }
            }
        }
        ViewUtil.setViewsClickListener(this, trendView, recordingView, historyView, gameStyleView, pic);
    }

    private void init(Context context, boolean isGONE, OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
        trendView = findViewById(R.id.trend);
        if (isGONE) {
            View v = findViewById(R.id.trendView);
            trendView.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
        }
        recordingView = findViewById(R.id.recording);
        historyView = findViewById(R.id.history);
        gameStyleView = findViewById(R.id.game_style);
        ViewUtil.setViewsClickListener(this, trendView, historyView, gameStyleView);
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
        return createPopupById(R.layout.popu_bet_setting);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_setting_container);
    }

    public void setPicText(String text) {
        ((TextView) findViewById(R.id.pic_tv)).setText(text);
    }

    @Override
    public void onClick(View v) {
        onViewClickListener.onVeiwCilck(v);
    }

    public interface OnViewClickListener {
        void onVeiwCilck(View v);
    }
}

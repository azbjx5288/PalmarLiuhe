package com.liuheonline.la.ui.widget.popu;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.utils.ViewUtil;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/7/21 17:34
 * @description 保存图片到本地
 */
public class SaveImgMenuPopup extends BasePopupWindow implements View.OnClickListener {

    private View save;
    private View cancel;

    private OnSaveImgMenuClickListener listener;

    public SaveImgMenuPopup(Activity context) {
        super(context);

        save = findViewById(R.id.save_img);
        cancel = findViewById(R.id.cancel);

        ViewUtil.setViewsClickListener(this,save, cancel);
        setBlurBackgroundEnable(true);
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_save_img);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_container);
    }

    public SaveImgMenuPopup setOnDeleteCommentMenuClickListener(OnSaveImgMenuClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.save_img) {
            if (listener != null) {
                listener.onSaveImg();
            }
            dismissWithOutAnima();

        } else if (i == R.id.cancel) {
            dismiss();

        }
    }

    public interface OnSaveImgMenuClickListener {
        void onSaveImg();
    }
}

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
 * @description 删除评论菜单
 */
public class DeleteCommentMenuPopup extends BasePopupWindow implements View.OnClickListener {

    private View delete;
    private View cancel;

    private OnDeleteCommentMenuClickListener listener;

    public DeleteCommentMenuPopup(Activity context) {
        super(context);

        delete = findViewById(R.id.delete_comment);
        cancel = findViewById(R.id.cancel);

        ViewUtil.setViewsClickListener(this,delete, cancel);
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
        return createPopupById(R.layout.popup_delete_comment);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_container);
    }

    public DeleteCommentMenuPopup setOnDeleteCommentMenuClickListener(OnDeleteCommentMenuClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.delete_comment) {
            if (listener != null) {
                listener.onDeleteClick();
            }
            dismissWithOutAnima();

        } else if (i == R.id.cancel) {
            dismiss();

        }
    }

    public interface OnDeleteCommentMenuClickListener {
        void onDeleteClick();
    }
}

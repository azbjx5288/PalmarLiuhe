package com.liuheonline.la.ui.widget.popu;

import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.liuheonline.la.utils.StringUtil;
import com.liuheonline.la.utils.ViewUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;

import razerdp.basepopup.BasePopupWindow;

public class NoticePopup extends BasePopupWindow implements View.OnClickListener {

    private View sure;
    private TextView content;
    private OnSureClickListener listener;

    public NoticePopup(Activity context) {
        super(context);

        sure = findViewById(R.id.sure);
        content = (TextView) findViewById(R.id.content);

        ViewUtil.setViewsClickListener(this, sure);

//        setBlurBackgroundEnable(true);
//        setInterceptTouchEvent(false);
//        setDismissWhenTouchOutside(true);
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
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_notice);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.notice_popup);
    }

    public NoticePopup setOnSureClickListener(OnSureClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void setContent(String content) {
        JLog.v(content);
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        String str = StringUtil.translation(content);
        JLog.v(str);
        this.content.setText(Html.fromHtml(str));
//        this.content.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure) {
//            dismissWithOutAnima();
            dismiss();
            listener.onSure();
        }
    }

    public interface OnSureClickListener {
        void onSure();
    }
}

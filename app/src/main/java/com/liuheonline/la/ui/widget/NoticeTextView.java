package com.liuheonline.la.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class NoticeTextView extends AppCompatTextView {
    public NoticeTextView(Context context) {
        super(context);
    }

    public NoticeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoticeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

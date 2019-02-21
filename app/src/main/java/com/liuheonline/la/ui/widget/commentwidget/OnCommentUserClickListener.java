package com.liuheonline.la.ui.widget.commentwidget;

/**
 * @author: aini
 * @date 2018/7/20 11:03
 * @description 评论控件点击
 */

import android.support.annotation.NonNull;

public interface OnCommentUserClickListener {
    void onCommentClicked(@NonNull IComment comment, CharSequence text);
}
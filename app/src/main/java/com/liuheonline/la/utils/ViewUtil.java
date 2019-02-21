package com.liuheonline.la.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author: aini
 * @date 2018/7/21 17:39
 * @description view事件工具类
 */
public class ViewUtil {
    public static void setViewsClickListener(@NonNull View.OnClickListener listener, View... views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public static void setViewsVisible(int visible, View... views) {
        for (View view : views) {
            if (view.getVisibility() != visible) {
                view.setVisibility(visible);
            }
        }
    }

    public static void setViewsEnable(boolean enable, View... views) {
        for (View view : views) {
            view.setEnabled(enable);
        }
    }

    public static void setViewsEnableAndClickable(boolean enable,boolean clickable, View... views) {
        for (View view : views) {
            view.setEnabled(enable);
            view.setClickable(clickable);
        }
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围（ABSListView无效）
     * 延迟300毫秒执行，使该方法可以在onCreate里面使用
     */
    public static void expandViewTouchDelegate(final View view, final int top, final int bottom, final int left, final int right) {
        if (view == null) {
            return;
        }
        if (view.getParent() != null) {

            ((View) view.getParent()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Rect bounds = new Rect();
                    view.setEnabled(true);
                    view.getHitRect(bounds);

                    bounds.top -= top;
                    bounds.bottom += bottom;
                    bounds.left -= left;
                    bounds.right += right;

                    Log.d("rect", "rect - top" + bounds.top + "  - right" + bounds.right);

                    TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                    if (View.class.isInstance(view.getParent())) {
                        ((View) view.getParent()).setTouchDelegate(touchDelegate);
                    }
                }
            }, 300);
        }
    }

    /**
     * 还原View的触摸和点击响应范围,最小不小于View自身范围
     */
    public static void restoreViewTouchDelegate(final View view) {
        if (view == null) {
            return;
        }

        if (view.getParent() != null) {

            ((View) view.getParent()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Rect bounds = new Rect();
                    bounds.setEmpty();
                    TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                    if (View.class.isInstance(view.getParent())) {
                        ((View) view.getParent()).setTouchDelegate(touchDelegate);
                    }
                }
            }, 300);
        }
    }
    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
}
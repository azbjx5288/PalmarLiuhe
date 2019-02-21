package com.yxt.itv.library.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/1
 * @Version 1.0
 * @Description 头部 绑定基类
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        creatAndBindView();


    }

    public P getmParams() {
        return mParams;
    }
    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param viewId
     * @param text
     * @return void
     * @date 2017/11/1
     * @version 1.0
     * @description 设置文字
     */
    protected void setText(int viewId, String text) {
        TextView textView = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }
    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param viewId
     * @param rightRes
     * @return void
     * @date 2017/11/1
     * @version 1.0
     * @description 设置右侧图标
     */
    protected void setRightIcon(int viewId, int rightRes) {
        ImageView imageView = findViewById(viewId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(rightRes);
    }

    protected void setLeftIconVisibility(int viewId,boolean flag){
        ImageView imageView = findViewById(viewId);
        if (flag){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    protected void setRightTextVisibility(int viewId,boolean flag){
        TextView textView = findViewById(viewId);
        if (flag){
            textView.setVisibility(View.GONE);
        }else {
            textView.setVisibility(View.VISIBLE);
        }
    }
    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param viewId
     * @param listener
     * @return void
     * @date 2017/11/1
     * @version 1.0
     * @description 设置右侧点击事件
     */
    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }
    /**
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @param viewId
     * @return T
     * @date 2017/11/1
     * @version 1.0
     * @description  通用findviewById
     */
    private <T extends View> T findViewById(int viewId) {
        return  mNavigationView.findViewById(viewId);
    }

    /**
     * @return void
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @date 2017/11/1
     * @version 1.0
     * @description 绑定和创建view
     */
    private void creatAndBindView() {
        //创建view
        if (mParams.mParent==null){
            //获取activity 的根布局  appcompatActivity
            ViewGroup viewRoot = (ViewGroup) ((Activity)mParams.mContext).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) viewRoot.getChildAt(0);
            mParams.mParent = (ViewGroup) mParams.mParent.getChildAt(1);
            mParams.mParent = (ViewGroup) mParams.mParent.getChildAt(0);
            mParams.mParent = (ViewGroup) mParams.mParent.getChildAt(1);
            mParams.mParent = (ViewGroup) mParams.mParent.getChildAt(0);
        }
        //处理activity的源码
        if (mParams.mParent==null){
            return;
        }

        View view = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);
        mNavigationView = view;
        //添加
        mParams.mParent.addView(view, 0);

        applyView();

    }

    public abstract int bindLayoutId();

    public abstract void applyView();

    public abstract static class Builder {

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }
}

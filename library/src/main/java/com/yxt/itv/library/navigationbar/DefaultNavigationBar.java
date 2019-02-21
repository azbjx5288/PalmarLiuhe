package com.yxt.itv.library.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yxt.itv.library.R;


/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/1
 * @Version 1.0
 * @Description 自定义navigationbar
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {


    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    /**
     * @return int
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @date 2017/11/1
     * @version 1.0
     * @description 绑定配置文件
     */
    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    /**
     * @return void
     * @author <font color="pink"><b>Deng.Xin</b></font>
     * @date 2017/11/1
     * @version 1.0
     * @description 绑定效果
     */
    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title, getmParams().mTitle);
        setText(R.id.right_text, getmParams().mRightText);
        setText(R.id.left_text, getmParams().mLeftText);
        setOnClickListener(R.id.right_icon, getmParams().mRightClickListener);
        setOnClickListener(R.id.right_text, getmParams().mRightClickListener);
        setOnClickListener(R.id.back, getmParams().mLeftClickListener);
        setOnClickListener(R.id.left_text, getmParams().mLeftTextClickListener);
        setOnClickListener(R.id.right_icon, getmParams().mRightClickListener);
        setRightIcon(R.id.right_icon, getmParams().mRightRes);
        setLeftIconVisibility(R.id.back, getmParams().mIsVisibility);
    }

    public DefaultNavigationBar setRightText(String text) {
        setText(R.id.right_text, text);
        return this;
    }

    public void setRightIcon(int rightRes) {
        super.setRightIcon(R.id.right_icon, rightRes);
    }

    public DefaultNavigationBar setLeftIconVisibility(boolean visibility) {
        setLeftIconVisibility(R.id.back, visibility);
        return this;
    }
    public DefaultNavigationBar setRightIconVisibility(boolean visibility) {
        setLeftIconVisibility(R.id.right_icon, visibility);
        return this;
    }
    public DefaultNavigationBar setRightTextVisibility(boolean visibility) {
        setRightTextVisibility(R.id.right_text, visibility);
        return this;
    }
    public DefaultNavigationBar setClickListener(View.OnClickListener leftTextClickListener) {
        setOnClickListener(R.id.back, leftTextClickListener);
        return this;
    }

    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams p;

        public Builder(Context context, ViewGroup parent) {

            p = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            p = new DefaultNavigationParams(context, null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar bar = new DefaultNavigationBar(p);
            return bar;
        }

        //设置效果
        public Builder setTitle(String tilte) {
            p.mTitle = tilte;
            return this;
        }

        public Builder setRightText(String rightText) {
            p.mRightText = rightText;
            return this;
        }

        public Builder setLeftText(String leftText) {
            p.mLeftText = leftText;
            return this;
        }

        public Builder setIcon(int rightRes) {
            p.mRightRes = rightRes;
            return this;
        }

        public Builder setLeftIconVisibility(boolean isVisibility) {
            p.mIsVisibility = isVisibility;
            return this;
        }

        public Builder setRightClickListener(View.OnClickListener rightClickListener) {
            p.mRightClickListener = rightClickListener;
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener leftClickListener) {
            p.mLeftClickListener = leftClickListener;
            return this;
        }

        public Builder setLeftTextClickListener(View.OnClickListener leftTextClickListener) {
            p.mLeftTextClickListener = leftTextClickListener;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationParams {
            //所有效果的设置
            public String mTitle;
            public String mRightText;
            public String mLeftText;
            public boolean mIsVisibility = false;
            public View.OnClickListener mRightClickListener;
            public View.OnClickListener mLeftTextClickListener;
            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                  //  ((Activity) mContext).overridePendingTransition(com.yxt.itv.library.R.anim.anim_out, com.yxt.itv.library.R.anim.anim_in);
                }
            };
            public int mRightRes;

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }

        }
    }
}

package com.yxt.itv.library.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by JhoneLee on 2017/10/16.
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public AlertDialog getmDialog() {
        return mDialog;
    }

    /**
     * 获取window
     *
     * @return
     */
    public Window getmWindow() {
        return mWindow;
    }


    public <T extends View> T getView(int viewId) {

        return mViewHelper.getView(viewId);
    }

    public void setText(int viewId, CharSequence charSequence) {
        mViewHelper.setText(viewId, charSequence);
    }

    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {
        mViewHelper.setOnclickListener(viewId, onClickListener);
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    public static class AlertParams {

        public Context mContext;

        public int mThemeResId;

        //点击空白是能够取消
        public boolean mCancelable = true;
        //dialog cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog dismiss 监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局view
        public View mView;
        //布局id
        public int mViewLayoutResId;
        //存放字体的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放点击事件
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();

        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mAnimations = 0;
        public int mGravity = Gravity.CENTER;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         *
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            //1.设置布局
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置setContentView");
            }

            //给dialog 设置布局
            mAlert.getmDialog().setContentView(viewHelper.getContentView());

            //设置controller的辅助类
            mAlert.setViewHelper(viewHelper);


            //2.设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //3.设置点击
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                viewHelper.setOnclickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //4.配置自定义效果
            Window window = mAlert.getmWindow();
            window.setGravity(mGravity);
            //设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }
            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }
}

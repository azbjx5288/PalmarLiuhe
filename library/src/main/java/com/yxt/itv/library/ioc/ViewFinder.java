package com.yxt.itv.library.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by JhoneLee on 2017/10/9.
 *  viewfindbyid 的辅助类
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public ViewFinder(View mView) {
        this.mView = mView;
    }
    public View findViewByid(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}

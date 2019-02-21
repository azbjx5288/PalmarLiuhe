package com.liuheonline.la.ui.widget;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.liuheonline.la.ui.base.BaseMVPActivity;

import java.lang.ref.WeakReference;

public class ProtectActivity extends BaseMVPActivity{
    public static WeakReference<ProtectActivity> weakReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_protect);
        initView();
        initData();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initView() {
        weakReference = new WeakReference<>(this);
        Log.w("portect","initview");
        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        //起始坐标
        params.x = 0;
        params.y = 0;
        //宽高设计为1个像素
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        finishSelf();
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        finishSelf();
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isScreenOn()) {
            finishSelf();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weakReference != null && weakReference.get() == this) {
            weakReference = null;
        }
        Log.w("portect","ondestory");
    }

    /**
     * 关闭自己
     */
    public void finishSelf() {
        if (!isFinishing()) {
            finish();
        }
    }

    /**
     * 判断主屏幕是否点亮
     *
     * @return
     */
    private boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return powerManager.isInteractive();
        } else {
            return powerManager.isScreenOn();
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}

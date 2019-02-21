package com.liuheonline.la.ui;

import android.view.KeyEvent;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/8/24 16:22
 * @description 地区限制页面
 */
public class ErrorActivity extends BaseFrameActivity{
    @Override
    protected void initData() {
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_error);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(true)
                .setLeftClickListener(v -> {
                    System.gc();
                    System.exit(0);
                })
                .setTitle("无法访问")
                .builder();
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.but_outlogin})
    private void onClick(){
        System.gc();
        System.exit(0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            System.gc();
            System.exit(0);
        }
        return false;
    }

    @Override
    public boolean supportSlideBack() {
        return false;
    }
}

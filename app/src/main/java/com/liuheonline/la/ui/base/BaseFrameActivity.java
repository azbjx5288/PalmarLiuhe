package com.liuheonline.la.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yxt.itv.library.base.BaseActivity;
import com.yxt.itv.library.permission.PermissionHelper;

/**
 * @author aini
 * @Date 2018/6/11
 * @Version 1.0
 * @Description  中间基本activity 只做展示作用
 */
public abstract class BaseFrameActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionsResult(this, requestCode, permissions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

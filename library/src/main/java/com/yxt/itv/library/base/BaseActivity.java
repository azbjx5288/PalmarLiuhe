package com.yxt.itv.library.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aitangba.swipeback.SwipeBackActivity;
import com.yxt.itv.library.ioc.ViewUtils;
import com.yxt.itv.library.util.ActivityManager;



/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/1
 * @Version 1.0
 * @Description 基类activity
 */
public abstract class BaseActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化界面
        setContentView();
        ViewUtils.inject(this);
        //初始化标题
        initTitle();
        //初始化数据
        initData();
        initData(savedInstanceState);
        //初始化view
        initView();
        // AlertDialog
    }



    protected abstract void initData();
    protected  void initData(Bundle savedInstanceState){};

    protected abstract void setContentView();

    protected abstract void initTitle();

    protected abstract void initView();

    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
       // overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }
    protected void startActivity(Class<?> clazz,Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
     //   overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.getActivityManager().popActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.getActivityManager().pushActivity(this);
    }

}

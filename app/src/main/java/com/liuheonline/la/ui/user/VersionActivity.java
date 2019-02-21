package com.liuheonline.la.ui.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseActivity;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author BenYanYi
 * @date 2018/12/19 10:46
 * @email ben@yanyi.red
 * @overview
 */
public class VersionActivity extends BaseActivity {
    @BindId(R.id.version)
    TextView tvVersion;

    @Override
    protected void initData() {
        tvVersion.setText(getVersionName(this));
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_version);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("关于我们")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {

    }

    public String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            @SuppressLint("WrongConstant")
            PackageInfo info = manager.getPackageInfo(packageName, 1);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }
}

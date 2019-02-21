package com.liuheonline.la.ui.user.action;

import android.view.View;

import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseActivity;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author BenYanYi
 * @date 2018/12/12 14:46
 * @email ben@yanyi.red
 * @overview 活动中心
 */
public class ActionCenter extends BaseActivity {

    private int userId;

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_actioncenter);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("活动中心")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.qianghongbao, R.id.sign})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.qianghongbao:
                if (userId != 0) {
                    startActivity(RedEnvelope.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.sign:
                if (userId != 0) {
                    startActivity(SignIn.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedperfencesUtil.getInt(this, "userId");
    }
}

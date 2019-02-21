package com.liuheonline.la.ui.user.account;

import android.view.View;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class MyAccount extends BaseMVPActivity{



    @OnClick({R.id.account_card,R.id.account_info,R.id.account_topup,R.id.account_withdraw})
    private void onClick(View v){
        switch (v.getId()){
            case R.id.account_card:

                startActivity(MyAccountCard.class);
                break;
            case R.id.account_info:
                startActivity(AccountInfo.class);
                break;
            case R.id.account_topup:
                startActivity(TopupInfo.class);
                break;
            case R.id.account_withdraw:
                startActivity(PdcashInfo.class);
                break;
        }
    }
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_myaccount);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("我的账户")
                .setLeftIconVisibility(false)
                .builder();

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}

package com.liuheonline.la.ui.user.topup;

import android.os.Bundle;
import android.view.View;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class ChoosePay extends BaseMVPActivity {

    @OnClick({R.id.saoma,R.id.wangying,R.id.sanfang})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.saoma:
                Bundle bundle = new Bundle();
                bundle.putString("type","saoma");
                startActivity(TopUp.class,bundle);
                break;
            case R.id.sanfang:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type","sanfang");
                startActivity(TopUp.class,bundle1);
                break;
            case R.id.wangying:
                startActivity(ShowTopCardActivity.class);
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
        setContentView(R.layout.activity_choosepay);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("充值")
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

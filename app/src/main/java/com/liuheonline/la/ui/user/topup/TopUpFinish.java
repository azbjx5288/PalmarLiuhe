package com.liuheonline.la.ui.user.topup;

import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class TopUpFinish extends BaseMVPActivity {
    @BindId(R.id.finish_price)
    TextView price;
    @BindId(R.id.finish_btn)
    TextView btn;
    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.finish_btn)
    private void onClick(){
        finish();
    }
    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {
        price.setText(getIntent().getExtras().getString("price"));
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topupfinish);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("充值成功")
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

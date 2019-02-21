package com.liuheonline.la.ui.user.register;

import android.view.View;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

public class DealActivity extends BaseMVPActivity<BaseView<WebEntity>,WebPresenter,WebEntity> {

    @BindId(R.id.deal_content)
    TextView content;


    @OnClick(R.id.close_img)
    private void onClick(View view){
        finish();
    }
    @Override
    protected void initPresenter() {
        presenter = new WebPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getWeb();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_deal);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(WebEntity webEntity) {
        if (webEntity!=null){
            content.setText(webEntity.getMemberagreement());
        }
    }


}

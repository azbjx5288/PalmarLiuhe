package com.liuheonline.la.ui.user.account;

import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PdCashEntity;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.DateUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class PdcashDetail extends BaseMVPActivity {
    @BindId(R.id.accountdetail_getmoney)
    TextView getmoney;
    @BindId(R.id.accountdetail_type)
    TextView type;
    @BindId(R.id.accountdetail_name)
    TextView name;
    @BindId(R.id.accountdetail_money)
    TextView money;
    @BindId(R.id.accountdetail_money2)
    TextView money2;
    @BindId(R.id.accountdetail_time)
    TextView time;
    @BindId(R.id.accountdetail_desc)
    TextView desc;
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {
        PdCashEntity.PdcashBean pdlogEntity = (PdCashEntity.PdcashBean) getIntent().getExtras().getSerializable("PdCashEntity");
        String  strgetmoney = pdlogEntity.getPdc_amount();
            getmoney.setTextColor(getResources().getColor(R.color.red));
        getmoney.setText(strgetmoney);
        type.setText("提现至银行卡");
        name.setText(pdlogEntity.getPdc_member_name());
        money.setText(pdlogEntity.getPdc_amount());
        money2.setText(pdlogEntity.getPdc_sn()+"");
        time.setText(DateUtil.timeStamp2Date(pdlogEntity.getPdc_add_time()+"",null));
        desc.setText(pdlogEntity.getPdc_note());

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pdcashdetail);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("提现详情")
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

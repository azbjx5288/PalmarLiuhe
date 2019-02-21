package com.liuheonline.la.ui.user.account;

import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PdrechargeEntity;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.CashTypeUtil;
import com.liuheonline.la.utils.DateUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class TopupDetail extends BaseMVPActivity {
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
        PdrechargeEntity.RechargeBean pdlogEntity = (PdrechargeEntity.RechargeBean) getIntent().getExtras().getSerializable("PdrechargeEntity");
        String  strgetmoney = pdlogEntity.getPdr_amount();
            getmoney.setTextColor(getResources().getColor(R.color.red));
        getmoney.setText(strgetmoney);
        type.setText(CashTypeUtil.getName(pdlogEntity.getPdr_type()+""));
        name.setText(pdlogEntity.getPdr_member_name());
        money.setText(pdlogEntity.getPdr_amount());
        money2.setText(pdlogEntity.getPdr_sn()+"");
        time.setText(DateUtil.timeStamp2Date(pdlogEntity.getPdr_add_time()+"",null));
        desc.setText(pdlogEntity.getPdr_note());

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topupdetail);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("充值详情")
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

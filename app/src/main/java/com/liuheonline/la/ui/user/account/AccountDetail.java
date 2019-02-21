package com.liuheonline.la.ui.user.account;

import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PdlogEntity;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.CashTypeUtil;
import com.liuheonline.la.utils.DateUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class AccountDetail extends BaseMVPActivity {
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
        PdlogEntity.PdlogBean pdlogEntity = (PdlogEntity.PdlogBean) getIntent().getExtras().getSerializable("PdlogEntity");
        String  strgetmoney = pdlogEntity.getLg_av_amount();
        if (strgetmoney.substring(0,1).equals("+")){
            getmoney.setTextColor(getResources().getColor(R.color.red));
        }
        getmoney.setText(strgetmoney);
        type.setText(CashTypeUtil.getName(pdlogEntity.getLg_type()));
        name.setText(pdlogEntity.getLg_member_name());
        money.setText(pdlogEntity.getLg_av_amount());
        money2.setText(pdlogEntity.getLg_freeze_amount());
        time.setText(DateUtil.timeStamp2Date(pdlogEntity.getLg_add_time()+"",null));
        desc.setText(pdlogEntity.getLg_desc());


    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_accountdetail);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("账户详情")
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

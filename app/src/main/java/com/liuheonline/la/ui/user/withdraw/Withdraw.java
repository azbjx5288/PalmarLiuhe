package com.liuheonline.la.ui.user.withdraw;

import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.GetCashPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class Withdraw extends BaseMVPActivity<BaseView<Object>,GetCashPresenter,Object> {

    @BindId(R.id.price)
    EditText price;
    @BindId(R.id.pass)
    EditText pass;


    UserInfoPresenter userInfoPresenter;

    double totalmoney;

    @OnClick(R.id.submit)
    private void onClick(){
        if (Double.parseDouble(price.getText().toString())<=0){
            Toast.makeText(getApplicationContext(),"金额填写有误",Toast.LENGTH_SHORT).show();
            return;
        }
        if (Double.parseDouble(price.getText().toString())>totalmoney){
            Toast.makeText(getApplicationContext(),"账户余额不足",Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.getText().toString().trim().length()==0){
            Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.getCash(price.getText().toString(),pass.getText().toString());
    }
    @Override
    protected void initPresenter() {
        presenter = new GetCashPresenter();
        userInfoPresenter = new UserInfoPresenter();
        userInfoPresenter.attachView(new BaseView<UserInfo>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(UserInfo userInfo) {
                totalmoney = Double.parseDouble(userInfo.getAvailable_predeposit());
            }
        });
    }

    @Override
    protected void fetchData() {
        userInfoPresenter.getUserInfo(SharedperfencesUtil.getInt(this,"userId"));
    }

    @Override
    protected void initData() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("账户提现")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_withdraw2);
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
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
    }

    @Override
    public void successed(Object o) {
        Toast.makeText(getApplicationContext(),"申请提现成功",Toast.LENGTH_SHORT).show();
        finish();
    }
}

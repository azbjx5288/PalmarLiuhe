package com.liuheonline.la.ui.user.account;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.SendMsgPresenter;
import com.liuheonline.la.mvp.presenter.VerifyNumberPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class VerifyMobile extends BaseMVPActivity<BaseView<Integer>,SendMsgPresenter,Integer> {

    @BindId(R.id.verify_edit)
    EditText edit_verify;
    @BindId(R.id.verify_number)
    TextView verify_number;

    VerifyNumberPresenter verifyNumberPresenter;
    @OnClick(R.id.verify_btn)
    private void onClick(View view){
        if (edit_verify.getText().toString().length()>0){
            verifyNumberPresenter.verifyNumber(SharedperfencesUtil.getString(this,"phoneNumber"),edit_verify.getText().toString());
        }else {
            Toast.makeText(getApplicationContext(),"验证码不能为空",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void initPresenter() {
        presenter = new SendMsgPresenter();
        verifyNumberPresenter = new VerifyNumberPresenter();
        verifyNumberPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {

            }
            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(Object o) {
                startActivity(BindCard.class);
                finish();
            }
        });
    }

    @Override
    protected void fetchData() {
        presenter.sendMsg("login",SharedperfencesUtil.getString(this,"phoneNumber"));
    }

    @Override
    protected void initData() {
        String number = SharedperfencesUtil.getString(this,"phoneNumber");
        String newnumber = number.substring(0,4)+"***"+number.substring(7,11);
        verify_number.setText(newnumber);

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_verifymobile);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("绑定银行卡")
                .builder();
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
        Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(Integer integer) {
        edit_verify.setText(integer+"");
    }

}

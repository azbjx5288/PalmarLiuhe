package com.liuheonline.la.ui.user.login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.FindPasswordPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.AuthActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/7/19 14:14
 * @description 找回密码
 */
public class FindPasswordActivity extends BaseMVPActivity<BaseView<String>,FindPasswordPresenter,String>{

    @BindId(R.id.input_pwd)
    private EditText inputPwdText;

    @BindId(R.id.input_pwds)
    private EditText inputPwdsText;

    private AlertDialog waitDialog;

    private String auth = "";

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            auth = bundle.getString("auth");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_findpassword);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("忘记密码")
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();
    }

    @OnClick({R.id.submit})
    private void sunmit(){

        String newPwd = inputPwdText.getText().toString();
        String newPwds = inputPwdsText.getText().toString();

        if(auth.equals("")){
            Toast.makeText(getApplicationContext(),"身份验证失效，请重新验证！",Toast.LENGTH_SHORT).show();
            startActivity(AuthActivity.class);
            finish();
            return;
        }

        if(newPwd.equals("")){
            Toast.makeText(getApplicationContext(),"请输入新密码哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(newPwd.length() < 6 || newPwd.length() > 20){
            Toast.makeText(getApplicationContext(),"密码格式不正确哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPwd.equals(newPwds)){
            Toast.makeText(getApplicationContext(),"两次输入密码不一致哦~",Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.findPassword(auth,newPwd,newPwds);
    }

    @Override
    protected void initPresenter() {
        presenter = new FindPasswordPresenter();
    }

    @Override
    protected void fetchData() {
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        waitDialog.cancel();
        Toast.makeText(getApplicationContext(),"提交失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(String s) {
        waitDialog.cancel();
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        finish();
    }
}

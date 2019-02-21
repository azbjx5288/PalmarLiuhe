package com.liuheonline.la.ui.user.login;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.LoginPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.main.MainActivity;
import com.liuheonline.la.ui.user.register.RegisterActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.CheckNet;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/7/17 11:57
 * @description 用户登录
 */
public class LoginActivity extends BaseMVPActivity<BaseView<UserInfo>,LoginPresenter,UserInfo>{

    @BindId(R.id.input_account)
    private EditText inputAccountText;

    @BindId(R.id.input_password)
    private EditText inputPasswordText;

    @BindId(R.id.is_show_pw)
    private CheckBox isShowPwCheck;

    @BindId(R.id.find_password)
    private TextView findPW;

    private AlertDialog wiatDialog;

    private boolean isLogin = false;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isLogin = bundle.getBoolean("isLogin");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("登录")
                .setRightText("注册")
                .setLeftIconVisibility(false)
                .setRightClickListener(v -> {
                    startActivity(RegisterActivity.class);
                    finish();
                }).builder();
    }

    @Override
    protected void initView() {
        wiatDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .create();
    }

    @OnClick({R.id.img_clear,R.id.is_show_pw,R.id.find_password})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.img_clear:
                inputPasswordText.setText("");
                break;
            case R.id.is_show_pw:
                if(isShowPwCheck.isChecked()){
                    inputPasswordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    inputPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.find_password:
                   // startActivity(AuthActivity.class);
                Toast.makeText(getApplicationContext(),"找回密码请联系在线客服",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @CheckNet(errorMsg = "网络异常")
    @OnClick({R.id.but_login})
    private void login(View view){
        if(inputAccountText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"账号不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }else if(inputPasswordText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"密码不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }else{
            presenter.login(inputAccountText.getText().toString(),inputPasswordText.getText().toString());
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter();
    }

    @Override
    protected void fetchData() {
    }

    @Override
    public void onLoading() {
        wiatDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
        wiatDialog.cancel();
    }

    @Override
    public void successed(UserInfo userInfo) {
        JLog.d(userInfo);
        wiatDialog.cancel();
        //插入到本地数据库
        SharedperfencesUtil.setInt(this,"userId",userInfo.getId());
        SharedperfencesUtil.setInt(this,"agent",userInfo.getAgent());
        SharedperfencesUtil.setString(this,"phoneNumber",userInfo.getMobile());
        SharedperfencesUtil.setString(this,"agencyusername",userInfo.getUsername());
        SharedperfencesUtil.setString(this,"zc_moshi",userInfo.getNodd_rebates()+"");
        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
        if(isLogin){
            startActivity(MainActivity.class);
        }
        finish();
    }
}

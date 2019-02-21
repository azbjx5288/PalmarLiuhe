package com.liuheonline.la.ui.user;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.FindAuthPresenter;
import com.liuheonline.la.mvp.presenter.SendMsgPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.login.FindPasswordActivity;
import com.liuheonline.la.utils.Constant;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.regex.Pattern;

/**
 * @author: aini
 * @date 2018/7/19 10:44
 * @description 找回密码auth值
 */
public class AuthActivity extends BaseMVPActivity<BaseView<Integer>,SendMsgPresenter,Integer>{

    private FindAuthPresenter findAuthPresenter;

    @BindId(R.id.send_code)
    private Button sendCodeBut;

    @BindId(R.id.input_phone)
    private EditText userPhoneText;

    @BindId(R.id.input_code)
    private EditText inputCodeText;

    private  AlertDialog wiatDialog;

    private CountDownTimer countDownTimer;

    @Override
    protected void initData() {

        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendCodeBut.setText((millisUntilFinished/1000)+"s 重新获取");
            }

            @Override
            public void onFinish() {
                sendCodeBut.setText("重新发送");
                sendCodeBut.setEnabled(true);
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_auth);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("身份验证")
                .builder();
    }

    @Override
    protected void initView() {
        wiatDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();
    }

    @OnClick({R.id.send_code,R.id.submit})
    private void onClick(View view){
        String phone = userPhoneText.getText().toString().trim();
        String code = inputCodeText.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"请输入手机号码",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Pattern.matches(Constant.PHONE_PATTERN,phone)){
            Toast.makeText(getApplicationContext(),"手机号格式不正确哦~",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){
            case R.id.send_code:
                //发送短信
                presenter.sendMsg(Constant.SENDTYPE_FIND,phone);
                sendCodeBut.setEnabled(false);
                countDownTimer.start();
                break;
            case R.id.submit:
                if(code.equals("")){
                    Toast.makeText(getApplicationContext(),"验证码不能为空哦~",Toast.LENGTH_SHORT).show();
                    return;
                }

                findAuthPresenter.findAuth(phone,code);

                break;
        }
    }


    @Override
    protected void initPresenter() {
        presenter = new SendMsgPresenter();
        findAuthPresenter = new FindAuthPresenter();
        findAuthPresenter.attachView(new BaseView<String>() {
            @Override
            public void onLoading() {
                wiatDialog.show();
            }

            @Override
            public void onLoadFailed(int code, String error) {
                wiatDialog.cancel();
                Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(String s) {
                wiatDialog.cancel();
                Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("auth",s);
                startActivity(FindPasswordActivity.class,bundle);
                finish();
            }
        });
    }

    @Override
    protected void fetchData() {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void successed(Integer integer) {
        inputCodeText.setText(integer+"");
        Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}

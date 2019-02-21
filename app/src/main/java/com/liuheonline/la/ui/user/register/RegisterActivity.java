package com.liuheonline.la.ui.user.register;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.GetVerImgPresenter;
import com.liuheonline.la.mvp.presenter.RegisterPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.ui.widget.SystemUtil;
import com.liuheonline.la.utils.GetBitmapUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/7/17 16:57
 * @description 注册
 */
public class RegisterActivity extends BaseMVPActivity<BaseView<UserInfo>,RegisterPresenter,UserInfo>{


    @BindId(R.id.input_account)
    private EditText inputAccountText;

    @BindId(R.id.input_password)
    private EditText inputPasswordText;

    @BindId(R.id.input_password3)
    private EditText inputPasswordText3;

    @BindId(R.id.input_code)
    private EditText inputCodeText;

    @BindId(R.id.input_phone)
    private EditText input_phone;

    @BindId(R.id.register_verfication)
    private ImageView imageView;
    @BindId(R.id.is_show_pw)
    private CheckBox isShowPwCheck;

    @BindId(R.id.is_show_pw3)
    private CheckBox isShowPwCheck3;

    @BindId(R.id.protocol)
    private CheckBox protocol;

    @BindId(R.id.send_code)
    private Button sendCodeBut;

    private AlertDialog waitDialog;

    private GetVerImgPresenter getVerImgPresenter;
    //private CountDownTimer countDownTimer;

    @Override
    protected void initData() {
       /* countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendCodeBut.setText((millisUntilFinished/1000)+"s 重新获取");
            }

            @Override
            public void onFinish() {
                sendCodeBut.setText("重新发送");
                sendCodeBut.setClickable(true);
            }
        };*/
        new GetBitmapUtil(imageView);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("注册")
                .setRightText("登录")
                .setRightClickListener(v -> {
                    startActivity(LoginActivity.class);
                    finish();
                })
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();

    }

    @OnClick({R.id.send_code,R.id.img_clear,R.id.img_clear3,R.id.is_show_pw,R.id.is_show_pw3,R.id.but_register,R.id.register_verfication,R.id.xieyi})
    private void onClick(View view){
        String account = inputAccountText.getText().toString();
        String password = inputPasswordText.getText().toString();
        String password3 = inputPasswordText3.getText().toString();
        String code = inputCodeText.getText().toString();
        String phone = input_phone.getText().toString();
        switch (view.getId()){
            case R.id.xieyi:
                startActivity(DealActivity.class);
                break;
            case R.id.register_verfication:
                new GetBitmapUtil(imageView);
                //sendMsgPresenter.sendMsg();
                break;
            case R.id.send_code:
               /* //发送短信
                sendMsgPresenter.sendMsg(Constant.SENDTYPE_REG,account);
                sendCodeBut.setClickable(false);
                //countDownTimer.start();*/
                break;
            case R.id.img_clear:
                inputPasswordText.setText("");
                break;
            case R.id.img_clear3:
                inputPasswordText3.setText("");
                break;
            case R.id.is_show_pw:
                if(isShowPwCheck.isChecked()){
                    inputPasswordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    inputPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;

            case R.id.is_show_pw3:
                if(isShowPwCheck3.isChecked()){
                    inputPasswordText3.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    inputPasswordText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.but_register:

                if(account.length()<4||account.length()>20){
                    Toast.makeText(getApplicationContext(),"账号输入有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(password3)){
                    Toast.makeText(getApplicationContext(),"密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if(!Pattern.matches(Constant.PHONE_PATTERN,phone)){
                    Toast.makeText(getApplicationContext(),"手机号格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }*/
                if (phone.length()!=11){
                    Toast.makeText(getApplicationContext(),"手机号输入有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6 || password.length()>15){
                    Toast.makeText(getApplicationContext(),"密码输入有误",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(code.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!protocol.isChecked()){
                    Toast.makeText(getApplicationContext(),"请阅读并同意服务协议",Toast.LENGTH_SHORT).show();
                    return;
                }
                //注册
                presenter.register(account,password,code, SystemUtil.getSystemModel(),phone);
                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new RegisterPresenter();
        getVerImgPresenter = new GetVerImgPresenter();
        getVerImgPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.w("thever",error);
            }

            @Override
            public void successed(Object o) {
                Log.w("thever",o.toString());
            }
        });
        getVerImgPresenter.getImg();
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
        sendCodeBut.setClickable(true);
        sendCodeBut.setText("重新发送");
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(UserInfo userInfo) {
        JLog.d(userInfo);
        waitDialog.cancel();
        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //countDownTimer.cancel();
    }
}

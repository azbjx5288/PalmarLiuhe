package com.liuheonline.la.ui.user.setting;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.BindPhonePresenter;
import com.liuheonline.la.mvp.presenter.SendMsgPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.Constant;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;
import com.yxt.itv.library.util.ActivityManager;

import java.util.regex.Pattern;

/**
 * @author: aini
 * @date 2018/7/18 20:06
 * @description 修改用户电话号码
 */
public class UserSetPhoneActivity extends BaseMVPActivity<BaseView<Integer>,SendMsgPresenter,Integer>{

    private BindPhonePresenter bindPhonePresenter;

    @BindId(R.id.new_phone)
    private EditText newPhoneText;

    @BindId(R.id.input_code)
    private EditText inputCodeText;

    @BindId(R.id.send_code)
    private Button sendCodeBut;

    private AlertDialog waitDialog;

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
        setContentView(R.layout.activity_usersetphone);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("修改手机号")
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();
    }

    @OnClick({R.id.submit,R.id.send_code})
    private void onClick(View view){
        String phone = newPhoneText.getText().toString();
        String code = inputCodeText.getText().toString();
        switch (view.getId()){
            case R.id.send_code:
                if(phone.length() == 0){
                    Toast.makeText(getApplicationContext(),"手机号不能为空哦~",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Pattern.matches(Constant.PHONE_PATTERN,phone)){
                    Toast.makeText(getApplicationContext(),"手机号格式不正确哦~",Toast.LENGTH_SHORT).show();
                    return;
                }
                //发送短信
                presenter.sendMsg(Constant.SENDTYPE_FIND,phone);
                sendCodeBut.setEnabled(false);
                countDownTimer.start();
                break;
            case R.id.submit:
                if(phone.length() == 0){
                    Toast.makeText(getApplicationContext(),"手机号不能为空哦~",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Pattern.matches(Constant.PHONE_PATTERN,phone)){
                    Toast.makeText(getApplicationContext(),"手机号格式不正确哦~",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(code.length() == 0){
                    Toast.makeText(getApplicationContext(),"请输入验证码哦~",Toast.LENGTH_SHORT).show();
                    return;
                }
                bindPhonePresenter.bindPhone(phone,code);
                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new SendMsgPresenter();
        bindPhonePresenter = new BindPhonePresenter();
        bindPhonePresenter.attachView(new BaseView<String>() {
            @Override
            public void onLoading() {
                waitDialog.show();
            }

            @Override
            public void onLoadFailed(int code, String error) {
                waitDialog.cancel();
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(String s) {
                waitDialog.cancel();
                Toast.makeText(getApplicationContext(),"修改成功,请重新登录!",Toast.LENGTH_SHORT).show();
                SharedperfencesUtil.setInt(UserSetPhoneActivity.this,"userId",0);
                startActivity(LoginActivity.class);
                ActivityManager.getActivityManager().destoryActivity(UserSettingActivity.class);
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
    public void onLoadFailed(int code, String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(Integer integer) {
        Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
        inputCodeText.setText(integer+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}

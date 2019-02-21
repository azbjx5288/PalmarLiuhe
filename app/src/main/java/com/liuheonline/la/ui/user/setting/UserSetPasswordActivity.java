package com.liuheonline.la.ui.user.setting;

import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.UpPwdPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;
import com.yxt.itv.library.util.ActivityManager;

/**
 * @author: aini
 * @date 2018/7/18 20:06
 * @description 修改用户密码
 */
public class UserSetPasswordActivity extends BaseMVPActivity<BaseView<Object>,UpPwdPresenter,Object>{

    @BindId(R.id.old_password)
    private EditText oldPasswordText;

    @BindId(R.id.new_password)
    private EditText newPasswordText;

    @BindId(R.id.new_passwords)
    private EditText newPasswordsText;

    private AlertDialog wiatDialog;

    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_usersetpassword);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("修改密码")
                .builder();
    }

    @Override
    protected void initView() {
        wiatDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();
    }

    @OnClick({R.id.submit})
    private void submit(){

        String oldPass = oldPasswordText.getText().toString();

        String newPass = newPasswordText.getText().toString();

        String newPassS = newPasswordsText.getText().toString();

        if(oldPass.length() == 0){
            Toast.makeText(this,"原密码不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(newPass.length() == 0){
            Toast.makeText(this,"新密码不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(newPass.length() <6  || newPass.length() > 20){
            Toast.makeText(this,"新密码格式不正确哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPass.equals(newPassS)){
            Toast.makeText(this,"两次输入密码不一致哦~",Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.upUserPwd(oldPass,newPass,newPassS);
    }
    @Override
    protected void initPresenter() {
        presenter = new UpPwdPresenter();
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
        wiatDialog.cancel();
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(Object o) {
        wiatDialog.cancel();
        SharedperfencesUtil.setInt(this,"userId",0);
        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
        startActivity(LoginActivity.class);
        ActivityManager.getActivityManager().destoryActivity(UserSettingActivity.class);
        finish();
    }

}

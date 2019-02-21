package com.liuheonline.la.ui.user.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.IsSetPWPresenter;
import com.liuheonline.la.mvp.presenter.UpPayPWdPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/7/18 20:06
 * @description 修改用户交易密码
 */
public class UserSetPayPWActivity extends BaseMVPActivity<BaseView<Object>,UpPayPWdPresenter,Object>{

    @BindId(R.id.old_password)
    private EditText oldPasswordText;

    @BindId(R.id.new_password)
    private EditText newPasswordText;

    @BindId(R.id.new_passwords)
    private EditText newPasswordsText;

    private AlertDialog wiatDialog;
    IsSetPWPresenter isSetPWPresenter;
    @BindId(R.id.oldpass)
    LinearLayout oldpass;

    int issetpw = 1;

    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_usersetpaypassword);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("修改支付密码")
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

        if(oldPass.length() == 0&&issetpw!=1){
            Toast.makeText(this,"原密码不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(newPass.length() == 0){
            Toast.makeText(this,"新密码不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(newPass.length() != 6){
            Toast.makeText(this,"新密码格式不正确哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPass.equals(newPassS)){
            Toast.makeText(this,"两次输入密码不一致哦~",Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.upPayPW(oldPass,newPass,newPassS);
    }
    @Override
    protected void initPresenter() {
        presenter = new UpPayPWdPresenter();
        isSetPWPresenter = new IsSetPWPresenter();
        isSetPWPresenter.attachView(new BaseView<Integer>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(Integer integer) {
                if (integer==1){
                    oldpass.setVisibility(View.GONE);
                    issetpw = integer;
                }

            }
        });
    }

    @Override
    protected void fetchData() {
        isSetPWPresenter.isSetPW();
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
        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
        finish();
    }

}

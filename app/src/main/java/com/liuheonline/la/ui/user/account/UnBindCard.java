package com.liuheonline.la.ui.user.account;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.DeleteCardPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class UnBindCard extends BaseMVPActivity<BaseView<Integer>,DeleteCardPresenter,Integer> {

    @BindId(R.id.passwords)
    private EditText editText;

    private AlertDialog waitDialog;

    private int cardId;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            cardId = bundle.getInt("cardId");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_unbind);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("解除绑定")
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
    private void submit(){
        String psw = editText.getText().toString();
        if(psw.length() < 6){
            Toast.makeText(LiuHeApplication.context,"请输入6位数支付密码",Toast.LENGTH_SHORT).show();
        }else{
            presenter.deleteCard(cardId,psw);
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new DeleteCardPresenter();
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
        super.onLoadFailed(code, error);
        waitDialog.cancel();
    }

    @Override
    public void successed(Integer integer) {
        waitDialog.cancel();
        if(integer == 200){
            Toast.makeText(LiuHeApplication.context,"解绑成功",Toast.LENGTH_SHORT).show();
            Log.w("thehasCard",SharedperfencesUtil.getBoolean(this,"hasCard")+" unbindcard 93");
            SharedperfencesUtil.setBoolean(UnBindCard.this,"hasCard",false);
            finish();
        }else{
            Toast.makeText(LiuHeApplication.context,"解绑失败,请联系客服。",Toast.LENGTH_SHORT).show();
        }
    }

}

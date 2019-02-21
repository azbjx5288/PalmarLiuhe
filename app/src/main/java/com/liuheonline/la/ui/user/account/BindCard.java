package com.liuheonline.la.ui.user.account;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.BankNameEntity;
import com.liuheonline.la.mvp.presenter.AddCardPresenter;
import com.liuheonline.la.mvp.presenter.GetBankNamePresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.popu.BanknamePopu;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.UIHelper;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class BindCard extends BaseMVPActivity<BaseView<Object>,AddCardPresenter,Object> {
    @BindId(R.id.bind_name)
    EditText edit_name;
    @BindId(R.id.bind_cardnum)
    EditText edit_cardnum;
    @BindId(R.id.bind_cardarea)
    EditText edit_cardarea;
    @BindId(R.id.bind_cardpass)
    EditText edit_cardpass;
    @BindId(R.id.bank_name)
    TextView bank_name;
    @BindId(R.id.bindcard_linear)
    private LinearLayout linearLayout;
    List<BankNameEntity> list;
    GetBankNamePresenter getBankNamePresenter;
    BanknamePopu banknamePopu;
    BankNameEntity bankNameEntity;
    private com.yxt.itv.library.dialog.AlertDialog waitDialog;
    @OnClick({R.id.bind_btn,R.id.bind_bankname})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.bind_btn:
                String strname = edit_name.getText().toString().trim();
                String strnum = edit_cardnum.getText().toString().trim();
                String strarea = edit_cardarea.getText().toString().trim();
                String strpass = edit_cardpass.getText().toString().trim();
                if (strname.length()<2){
                    Toast.makeText(this,"姓名填写有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (strnum.length()<15){
                    Toast.makeText(this,"账号填写有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (strpass.length()==0){
                    Toast.makeText(this,"密码填写有误",Toast.LENGTH_SHORT).show();
                    return;
                }else if (strarea.length()==0){
                    Toast.makeText(this,"开户行填写有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    presenter.addCard(strname,strnum,strarea,bankNameEntity.getId()+"",bankNameEntity.getTitle(),strpass);
                }
                break;
            case R.id.bind_bankname:
                UIHelper.hideInputMethod(view);
                banknamePopu.showPopupWindow(linearLayout,list);
                break;
        }

    }
    @Override
    protected void initPresenter() {
        presenter = new AddCardPresenter();
        getBankNamePresenter = new GetBankNamePresenter();
        getBankNamePresenter.attachView(new BaseView<List<BankNameEntity>>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(List<BankNameEntity> bankNameEntity) {
                list = bankNameEntity;
            }
        });
    }

    @Override
    protected void fetchData() {
        getBankNamePresenter.getBankName();
    }

    @Override
    protected void initData() {
        Toast.makeText(getApplicationContext(),"为保证资金安全，请输入正确的银行卡号",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_bindcard);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("绑定银行卡")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new com.yxt.itv.library.dialog.AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"正在加载……")
                .create();
         banknamePopu = new BanknamePopu(this, bankNameEntity1 -> {
             bankNameEntity = bankNameEntity1;
             bank_name.setText(bankNameEntity1.getTitle());
             banknamePopu.dismiss();
         });
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
    public void successed(Object o) {
        waitDialog.cancel();
        startActivity(BindFinish.class);
        Log.w("thehasCard",SharedperfencesUtil.getBoolean(this,"hasCard")+" bindcard 151");
        SharedperfencesUtil.setBoolean(BindCard.this,"hasCard",true);
        finish();

    }
}

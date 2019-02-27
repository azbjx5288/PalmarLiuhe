package com.liuheonline.la.ui.user.topup;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.RechargeEntity;
import com.liuheonline.la.entity.RechargeSNEntity;
import com.liuheonline.la.mvp.presenter.GetRechargeSNPresenter;
import com.liuheonline.la.mvp.presenter.RechargePresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker2;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author: gan
 * @date 2019/2/25 16:04
 * @description 提交银行卡充值信息
 */
public class TopCardCommitActivity2 extends BaseMVPActivity<BaseView<RechargeEntity>,RechargePresenter,RechargeEntity>{


/*    @BindId(R.id.sn_number)
    private EditText snNumberText;*/

    @BindId(R.id.top_time)
    private TextView topTimeText;

    @BindId(R.id.card_num)
    private TextView cardNumText;

    @BindId(R.id.user_name)
    private TextView userNameText;

    @BindId(R.id.card_name)
    private TextView cardNameText;

    @BindId(R.id.card_add)
    private TextView cardAddText;

    @BindId(R.id.sn_name)
    private EditText sn_name;

    @BindId(R.id.order_num)
    TextView order_num;

    @BindId(R.id.money)
    private TextView moneyText;

    private CustomDatePicker2 customDatePicker;

    private AlertDialog waitDialog;

    private PayMentEntity payMentEntity;

    GetRechargeSNPresenter getRechargeSNPresenter;

    //确认转账弹窗
    private AlertDialog rechargeDialog;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            payMentEntity = (PayMentEntity) bundle.getSerializable("payMentEntity");
        }

        if (payMentEntity != null) {
            cardNumText.setText(payMentEntity.getConfig().getBank_account_number());
            userNameText.setText(payMentEntity.getConfig().getBank_account_name());
            cardNameText.setText(payMentEntity.getConfig().getBank_id());
            cardAddText.setText(payMentEntity.getConfig().getBank_name());
            moneyText.setText(bundle.getString("money"));
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topcardcommit2);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("转账到银行卡")
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"提交中……")
                .create();

        initDatePicker();

        //初始化dialog
        rechargeDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_recharge)
                .setWidthAndHeight(Dip2pxUtil.dip2px(this,300f),Dip2pxUtil.dip2px(TopCardCommitActivity2.this,150f))
                .create();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker2(this, time -> { // 回调接口，获得选中的时间
            topTimeText.setText(time);
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动

        topTimeText.setText(now);
    }

    @OnClick({R.id.submit_btn,R.id.top_time})
    private void submit(View view){
        switch (view.getId()){
            case R.id.submit_btn:

                rechargeDialog.setOnclickListener(R.id.cancel,v1 -> rechargeDialog.dismiss());
                rechargeDialog.setOnclickListener(R.id.sure,v1 -> {


                    String cardNum = cardNumText.getText().toString().trim();
                    String userName = userNameText.getText().toString().trim();
                    String cardName = cardNameText.getText().toString().trim();
//                String snNumber = order_num.getText().toString().trim();
                    String topTime = topTimeText.getText().toString().trim();

                    String money=moneyText.getText().toString().trim();
                    String name=sn_name.getText().toString().trim();


                    if(money.equals("")){
                        Toast.makeText(LiuHeApplication.context,"请输入金额",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(cardNum.equals("")){
                        Toast.makeText(LiuHeApplication.context,"请输入卡号",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(userName.equals("")){
                        Toast.makeText(LiuHeApplication.context,"请输入姓名",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(cardName.equals("")){
                        Toast.makeText(LiuHeApplication.context,"请输入付款银行名称",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(name.equals("")){
                        Toast.makeText(LiuHeApplication.context,"请输入存款人姓名",Toast.LENGTH_SHORT).show();
                        return;
                    }

              /*  if(snNumber.equals("")){
                    Toast.makeText(LiuHeApplication.context,"请输入单号",Toast.LENGTH_SHORT).show();
                    return;
                }*/

                    if(topTime.equals("") || topTime.equals("点击选择转账时间")){
                        Toast.makeText(LiuHeApplication.context,"请选择转账时间",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    long time = 0;
                    try {
                        time = sdf.parse(topTime).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Map<String,Object> map = new HashMap<>();
                    map.put("pdr_amount",money);
                    map.put("payment_code",payMentEntity.getCode());
//                map.put("pdr_trade_sn",snNumber);
                    map.put("pdr_bank_account_name",userName);
                    map.put("pdr_bank_account_number",cardNum);
                    map.put("pdr_bank_id",cardName);
                    map.put("pdr_payment_time",time);
                    map.put("name",name);
                    Log.w("map", map.toString());
                    presenter.recharge(map);

                });
                rechargeDialog.show();

                break;
            case R.id.top_time:
                customDatePicker.show();
                break;
        }
    }

    @OnClick({ R.id.yhkcopy, R.id.xmcopy, R.id.yhcope, R.id.khhcpoy, R.id.ddbhcopy,R.id.moneycopy})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.yhkcopy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(cardNumText.getText());
                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.xmcopy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm1.setText(userNameText.getText());
                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.yhcope:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm2.setText(cardNameText.getText());
                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.khhcpoy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm3 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm3.setText(cardAddText.getText());
                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ddbhcopy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm4 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm4.setText(order_num.getText());
                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.moneycopy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm5 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm5.setText(moneyText.getText());
                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;


        }
    }

    @Override
    protected void initPresenter() {
        presenter = new RechargePresenter();

        getRechargeSNPresenter = new GetRechargeSNPresenter();
        getRechargeSNPresenter.attachView(new BaseView<RechargeSNEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(RechargeSNEntity rechargeSNEntity) {
                order_num.setText(rechargeSNEntity.getPay_sn());
            }
        });
    }

    @Override
    protected void fetchData() {
        getRechargeSNPresenter.getsn();
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
        rechargeDialog.dismiss();
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(RechargeEntity rechargeEntity) {
        waitDialog.cancel();
        rechargeDialog.dismiss();
        startActivity(TopCardFinishActivity.class);
        finish();
    }

}

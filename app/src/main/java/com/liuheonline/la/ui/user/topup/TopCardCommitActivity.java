package com.liuheonline.la.ui.user.topup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.RechargeEntity;
import com.liuheonline.la.mvp.presenter.RechargePresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker2;
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
 * @author: aini
 * @date 2018/8/24 16:04
 * @description 提交银行卡充值信息
 */
public class TopCardCommitActivity extends BaseMVPActivity<BaseView<RechargeEntity>,RechargePresenter,RechargeEntity>{

    @BindId(R.id.card_num)
    private EditText cardNumText;

    @BindId(R.id.user_name)
    private EditText userNameText;

    @BindId(R.id.card_name)
    private EditText cardNameText;

    @BindId(R.id.sn_number)
    private EditText snNumberText;

    @BindId(R.id.top_time)
    private TextView topTimeText;

    private CustomDatePicker2 customDatePicker;

    private AlertDialog waitDialog;

    private PayMentEntity payMentEntity;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            payMentEntity = (PayMentEntity) bundle.getSerializable("payMentEntity");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topcardcommit);
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
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker2(this, time -> { // 回调接口，获得选中的时间
            topTimeText.setText(time);
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @OnClick({R.id.submit_btn,R.id.top_time})
    private void submit(View view){
        switch (view.getId()){
            case R.id.submit_btn:
                String cardNum = cardNumText.getText().toString().trim();
                String userName = userNameText.getText().toString().trim();
                String cardName = cardNameText.getText().toString().trim();
                String snNumber = snNumberText.getText().toString().trim();
                String topTime = topTimeText.getText().toString().trim();

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

                if(snNumber.equals("")){
                    Toast.makeText(LiuHeApplication.context,"请输入单号",Toast.LENGTH_SHORT).show();
                    return;
                }

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
                map.put("pdr_amount",payMentEntity.getPrice());
                map.put("payment_code",payMentEntity.getCode());
                map.put("pdr_trade_sn",snNumber);
                map.put("pdr_bank_account_name",userName);
                map.put("pdr_bank_account_number",cardNum);
                map.put("pdr_bank_id",cardName);
                map.put("pdr_payment_time",time);
                presenter.recharge(map);
                break;
            case R.id.top_time:
                customDatePicker.show();
                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new RechargePresenter();
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
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(RechargeEntity rechargeEntity) {
        waitDialog.cancel();
        startActivity(TopCardFinishActivity.class);
        finish();
    }

}

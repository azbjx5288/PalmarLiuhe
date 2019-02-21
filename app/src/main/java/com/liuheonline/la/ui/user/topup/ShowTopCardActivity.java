package com.liuheonline.la.ui.user.topup;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.RechargeSNEntity;
import com.liuheonline.la.mvp.presenter.GetRechargeSNPresenter;
import com.liuheonline.la.mvp.presenter.PayMentPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

/**
 * @author: aini
 * @date 2018/8/24 15:45
 * @description 显示充值银行卡信息
 */
public class ShowTopCardActivity extends BaseMVPActivity<BaseView<List<PayMentEntity>>, PayMentPresenter, List<PayMentEntity>> {

    @BindId(R.id.card_num)
    private TextView cardNumText;

    @BindId(R.id.user_name)
    private TextView userNameText;

    @BindId(R.id.card_name)
    private TextView cardNameText;

    @BindId(R.id.card_add)
    private TextView cardAddText;

    @BindId(R.id.next_btn)
    private Button nextBtn;

    @BindId(R.id.order_num)
    TextView order_num;

    @BindId(R.id.money)
    private EditText moneyText;

    @BindId(R.id.yhkcopy)
    TextView yhkcopy;
    @BindId(R.id.xmcopy)
    TextView xmcopy;
    @BindId(R.id.yhcope)
    TextView yhcope;
    @BindId(R.id.khhcpoy)
    TextView khhcpoy;
    @BindId(R.id.ddbhcopy)
    TextView ddbhcopy;
    private PayMentEntity payMentEntity;

    GetRechargeSNPresenter getRechargeSNPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_showtopcard);
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

    }

    @OnClick({R.id.next_btn, R.id.yhkcopy, R.id.xmcopy, R.id.yhcope, R.id.khhcpoy, R.id.ddbhcopy})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                Log.w("moneytext", moneyText.getText().toString());
                if (moneyText.getText().toString().length() == 0 && !moneyText.getText().toString().equals("请输入转账金额")) {
                    Toast.makeText(getApplicationContext(), "金额不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                double getmoney = 0;
                try {
                    getmoney = Double.parseDouble(moneyText.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "金额输入有误", Toast.LENGTH_SHORT).show();
                }
                if (getmoney <= 0) {
                    Toast.makeText(getApplicationContext(), "金额输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                double v = getmoney % 10;
                if (v == 0) {
                    Toast.makeText(getApplicationContext(), "充值金额不能为整数", Toast.LENGTH_SHORT).show();
                    return;
                }
                next();
                break;
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
        }
    }

    private void next() {
        payMentEntity.setPrice(Double.parseDouble(moneyText.getText().toString()));
        Bundle bundle = new Bundle();
        bundle.putSerializable("payMentEntity", payMentEntity);
        startActivity(TopCardCommitActivity.class, bundle);
        finish();
    }

    @Override
    protected void initPresenter() {
        presenter = new PayMentPresenter();
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
        presenter.getPayment();
        getRechargeSNPresenter.getsn();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<PayMentEntity> payMentEntities) {
        for (int i = 0; i < payMentEntities.size(); i++) {
            if (payMentEntities.get(i).getCode().equals("offline")) {
                payMentEntity = payMentEntities.get(i);
            }
        }
        if (payMentEntity != null) {
            cardNumText.setText(payMentEntity.getConfig().getBank_account_number());
            userNameText.setText(payMentEntity.getConfig().getBank_account_name());
            cardNameText.setText(payMentEntity.getConfig().getBank_id());
            cardAddText.setText(payMentEntity.getConfig().getBank_name());
        } else {
            nextBtn.setClickable(false);
            Toast.makeText(LiuHeApplication.context, "获取银行卡信息失败，请重新操作！", Toast.LENGTH_SHORT).show();
        }
    }

}

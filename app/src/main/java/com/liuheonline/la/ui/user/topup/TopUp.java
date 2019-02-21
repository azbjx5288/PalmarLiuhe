package com.liuheonline.la.ui.user.topup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.BankCard1Entity;
import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.BankCard1Presenter;
import com.liuheonline.la.mvp.presenter.PayMentPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.BannerWebView;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class TopUp extends BaseMVPActivity<BaseView<List<PayMentEntity>>, PayMentPresenter, List<PayMentEntity>> {

    @BindId(R.id.topup_money)
    TextView money;
    @BindId(R.id.topup_edit)
    EditText editprice;

    UserInfoPresenter userInfoPresenter;

    List<PayMentEntity> list;
    List<PayMentEntity> lists;

    private BaseQuickAdapter<PayMentEntity, BaseViewHolder> baseQuickAdapter;
    @BindId(R.id.topup_recycle)
    private RecyclerView recyclerView;

    String type = "";
    int ischecked = 1;
    PayMentEntity payMentEntity;
    BankCard1Presenter bankCard1Presenter;

    @Override
    protected void initPresenter() {
        presenter = new PayMentPresenter();
        bankCard1Presenter = new BankCard1Presenter();
        bankCard1Presenter.attachView(new BaseView<BankCard1Entity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(BankCard1Entity bankCard1Entity) {
                if (bankCard1Entity != null) {
                    if (bankCard1Entity.getRespCode().equals("00")) {//支付成工
                        Bundle bundle = new Bundle();
                        bundle.putString("url", bankCard1Entity.getBarCode());
                        startActivity(BannerWebView.class, bundle);
                       /* Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(bankCard1Entity.getBarCode());
                        intent.setData(content_url);
                        try {
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                    }
                }
            }
        });
        userInfoPresenter = new UserInfoPresenter();
        userInfoPresenter.attachView(new BaseView<UserInfo>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(UserInfo userInfo) {
                money.setText(userInfo.getAvailable_predeposit() + "元");
            }
        });

    }

    @Override
    protected void fetchData() {
        presenter.getPayment();
        userInfoPresenter.getUserInfo(SharedperfencesUtil.getInt(this, "userId"));
    }


    @OnClick({R.id.topup_commit})
    private void onClick(View view) {
        Log.w("the status", ischecked + "");
        if (editprice.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "金额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Double.parseDouble(editprice.getText().toString()) <= 0) {
            Toast.makeText(getApplicationContext(), "金额输入有误", Toast.LENGTH_SHORT).show();
            return;
        }
        double v = Double.parseDouble(editprice.getText().toString()) % 10;
        if (v == 0) {
            Toast.makeText(getApplicationContext(), "充值金额不能为整数", Toast.LENGTH_SHORT).show();
            return;
        }
        if (type.equals("sanfang")) {
            if (payMentEntity.getName().equals("支付宝")) {
                bankCard1Presenter.getBankCard("alipay", editprice.getText().toString());
            } else if (payMentEntity.getName().equals("微信支付")) {
                bankCard1Presenter.getBankCard("wechat", editprice.getText().toString());
            }
        } else if (payMentEntity != null) {
            JLog.v();
            Bundle bundle = new Bundle();
            payMentEntity.setPrice(Double.parseDouble(editprice.getText().toString()));
            bundle.putSerializable("payMentEntity", payMentEntity);
            JLog.i(payMentEntity.getCode());
            if (payMentEntity.getCode().equals("offline")) {
                JLog.v();
                if (payMentEntity.getConfig() != null) {
                    startActivity(ShowTopCardActivity.class, bundle);
                }
            } else {
                JLog.v();
                if (payMentEntity.getQrcode() != null) {
                    startActivity(TopUpCommit2.class, bundle);
                }
            }

        }
    }


    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<PayMentEntity, BaseViewHolder>(R.layout.item_payment) {
            @Override
            protected void convert(BaseViewHolder helper, PayMentEntity item) {
                helper.setText(R.id.payent_name, item.getName());
                CheckBox checkBox = helper.getView(R.id.payent_checkbox);
                checkBox.setChecked(item.isSelected());
                if (item.isSelected()) {
                    payMentEntity = item;
                }

                //加载图片
                Glide.with(TopUp.this)
                        .load(item.getPic_link())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                .error(R.mipmap.jiazaishibai) //加载失败图片
                                .fallback(R.mipmap.jiazaishibai) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into((ImageView) helper.getView(R.id.payent_img));
                LinearLayout linear = helper.getView(R.id.payent_linear);
                linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelected(false);
                        }
                        list.get(helper.getAdapterPosition()).setSelected(true);
                        baseQuickAdapter.notifyDataSetChanged();
                    }
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBox.isChecked()) {
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setSelected(false);
                            }
                            list.get(helper.getAdapterPosition()).setSelected(true);
                            baseQuickAdapter.notifyDataSetChanged();
                        } else {
                            checkBox.setChecked(true);
                        }
                    }
                });


            }
        };

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topup);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("账户充值")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
        type = getIntent().getExtras().getString("type");

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<PayMentEntity> payMentEntities) {
        for (int i = 0; i < payMentEntities.size(); i++) {
            if (payMentEntities.get(i).getCode().equals("offline")) {
                payMentEntities.remove(i);
            }
        }
        payMentEntities.get(0).setSelected(true);
        list = payMentEntities;
        baseQuickAdapter.setNewData(payMentEntities);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (inputMethodManager.isActive()) {
//                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
//            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && getCurrentFocus() != null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}

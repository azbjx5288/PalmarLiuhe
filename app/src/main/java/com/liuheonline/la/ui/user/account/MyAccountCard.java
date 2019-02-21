package com.liuheonline.la.ui.user.account;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.BankCardEntity;
import com.liuheonline.la.mvp.presenter.GetCardPresenter;
import com.liuheonline.la.mvp.presenter.IsSetPWPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.user.setting.UserSetPayPWActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class MyAccountCard extends BaseMVPActivity<BaseView<List<BankCardEntity>>,GetCardPresenter,List<BankCardEntity>> implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {
    @BindId(R.id.card_recycle)
    private RecyclerView recyclerView;
    @BindId(R.id.card_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;


    AlertDialog setpwDialog;

    int issetpwd = 0;
    IsSetPWPresenter isSetPWPresenter;
    @BindId(R.id.linear_hasdata)
    private LinearLayout hasdata;
    @BindId(R.id.linear_nodata)
    private LinearLayout nodata;
    private BaseQuickAdapter<BankCardEntity,BaseViewHolder> baseQuickAdapter;
    int p = 1;
    List<BankCardEntity> data;
    DefaultNavigationBar defaultNavigationBar;

    @OnClick({R.id.text_addcard})
    private void onClick(View view){
        if (issetpwd==1){
            setpwDialog.show();
        }else {
            startActivity(BindCard.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCard(1,10);
    }

    @Override
    protected void initPresenter() {
        presenter = new GetCardPresenter();
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
                issetpwd = integer;
            }
        });
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getCard(1,10);
        isSetPWPresenter.isSetPW();
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<BankCardEntity, BaseViewHolder>(R.layout.item_accountcard) {
            @Override
            protected void convert(BaseViewHolder helper, BankCardEntity item) {
                String strname = item.getBank_account_name();
                String newname = "*"+strname.substring(1,strname.length());
                helper.setText(R.id.card_name,newname);
                String strbank = item.getBank_name();
                helper.setText(R.id.card_bank,strbank);
                String strnum = item.getBank_account_number();
                String newnum = strnum.substring(0,6)+"******"+strnum.substring(12);
                helper.setText(R.id.card_num,newnum);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Bundle bundle = new Bundle();
                        bundle.putSerializable("BankCardEntity",item);
                        startActivity(BankService.class,bundle);*/
                    }
                });
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_myaccountcard);
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("我的账户卡")
                .setLeftIconVisibility(false)
                .setRightClickListener(view -> {
                    switch (view.getId()){
                        case R.id.right_icon:
                            if (issetpwd==1){
                                setpwDialog.show();
                            }else {
                                startActivity(BindCard.class);
                            }
                            break;
                        case R.id.right_text:
                            Bundle bundle = new Bundle();
                            bundle.putInt("cardId",data.get(0).getId());
                            startActivity(UnBindCard.class,bundle);
                            break;
                    }
                })
                .builder();

    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        baseQuickAdapter.setOnLoadMoreListener(this,recyclerView);
        baseQuickAdapter.disableLoadMoreIfNotFullPage();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
        setpwDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.setpwdialog)
                .setCancelable(false)
                .create();
        setpwDialog.setOnclickListener(R.id.btn_cancle, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpwDialog.dismiss();
            }
        });
        setpwDialog.setOnclickListener(R.id.btn_sure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpwDialog.dismiss();
                startActivity(UserSetPayPWActivity.class);
            }
        });
    }

    @Override
    public void onLoading() {

    }


    @Override
    public void successed(List<BankCardEntity> bankCardEntities) {
        data = bankCardEntities;
        swipeRefreshLayout.setRefreshing(false);

        if(p == 1){
            if(bankCardEntities != null && bankCardEntities.size() > 0){
                baseQuickAdapter.setNewData(bankCardEntities);
                defaultNavigationBar.setRightIconVisibility(true);
                defaultNavigationBar.setRightText("解除绑定");
                hasdata.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
                if(bankCardEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                //空数据
                defaultNavigationBar.setRightTextVisibility(true);
                defaultNavigationBar.setRightIcon(R.mipmap.tianjia);
                hasdata.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        }else{
            if(bankCardEntities != null && bankCardEntities.size() == 10){

                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(bankCardEntities);
        }
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.getCard(1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        if (data.size()==10){
            p++;
            presenter.getCard(p,10);
        }
    }
}

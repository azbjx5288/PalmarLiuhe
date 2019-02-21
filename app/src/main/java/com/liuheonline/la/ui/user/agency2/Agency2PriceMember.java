package com.liuheonline.la.ui.user.agency2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PriceMemberEntity;
import com.liuheonline.la.mvp.presenter.PriceMemberPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class Agency2PriceMember extends BaseMVPActivity<BaseView<List<PriceMemberEntity>>,PriceMemberPresenter,List<PriceMemberEntity>> {
    @BindId(R.id.pricememberrecycle)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<PriceMemberEntity,BaseViewHolder> baseQuickAdapter;
    int id = 0;
    @Override
    protected void initPresenter() {
        presenter = new PriceMemberPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.priceMember(id);
    }

    @Override
    protected void initData() {
       id =  getIntent().getExtras().getInt("id");
        baseQuickAdapter = new BaseQuickAdapter<PriceMemberEntity, BaseViewHolder>(R.layout.item_pricemember) {
            @Override
            protected void convert(BaseViewHolder helper, PriceMemberEntity item) {
                helper.setText(R.id.yonghuxingming,item.getUsername());
                helper.setText(R.id.touzhujine,item.getBetting());
                helper.setText(R.id.yongjin,item.getCommission());
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pricemember);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("详情")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<PriceMemberEntity> priceMemberEntities) {
        baseQuickAdapter.setNewData(priceMemberEntities);
    }
}

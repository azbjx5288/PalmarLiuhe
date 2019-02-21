package com.liuheonline.la.ui.main.notice;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.NoticeEntity;
import com.liuheonline.la.mvp.presenter.NoticePresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class NoticeActivity extends BaseMVPActivity<BaseView<List<NoticeEntity>>,NoticePresenter,List<NoticeEntity>> implements  SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    @BindId(R.id.notice_recycle)
    private RecyclerView recyclerView;
    @BindId(R.id.notice_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;
    private BaseQuickAdapter<NoticeEntity,BaseViewHolder> baseQuickAdapter;
    int p = 1;
    @Override
    protected void initPresenter() {
        presenter  = new NoticePresenter();
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getNotice(1,10);
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<NoticeEntity, BaseViewHolder>(R.layout.item_notice) {
            @Override
            protected void convert(BaseViewHolder helper, NoticeEntity item) {
                helper.setText(R.id.notice_text,item.getContent())
                        .setText(R.id.time,item.getCreate_time());

                helper.itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("notice",item);
                    startActivity(NoticeDtailsActivity.class,bundle);
                });
            }
        };

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_notice);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("公告列表")
                .setLeftIconVisibility(false)
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
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void successed(List<NoticeEntity> noticeEntities) {
        swipeRefreshLayout.setRefreshing(false);
        if(p == 1){
            if(noticeEntities != null && noticeEntities.size() > 0){
                baseQuickAdapter.setNewData(noticeEntities);
                // baseQuickAdapter.replaceData(footerEntities);
                if(noticeEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                baseQuickAdapter.setNewData(noticeEntities);//空数据
            }
        }else{
            if(noticeEntities != null && noticeEntities.size() == 10){

                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(noticeEntities);
        }
    }

    @Override
    public void onRefresh() {
        p = 1;
        presenter.getNotice(p,10);
    }

    @Override
    public void onLoadMoreRequested() {
    p++;
    presenter.getNotice(p,10);
    }
}

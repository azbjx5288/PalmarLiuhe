package com.liuheonline.la.ui.user.lotterylog;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.LotteryLogDetaisEntity;
import com.liuheonline.la.mvp.presenter.CancelOtherPresenter;
import com.liuheonline.la.mvp.presenter.LotteryLogDetalsPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

/**
 * @author: aini
 * @date 2018/8/2 15:00
 * @description 投注记录
 */
public class LotteryLogDetailsActivity extends BaseMVPActivity<BaseView<List<LotteryLogDetaisEntity>>,
        LotteryLogDetalsPresenter,List<LotteryLogDetaisEntity>> implements SwipeRefreshLayout.OnRefreshListener
,BaseQuickAdapter.RequestLoadMoreListener{

    @BindId(R.id.log_refresh)
    private SwipeRefreshLayout logRefreshView;

    @BindId(R.id.log_recycler)
    private RecyclerView logRecyclerView;

    @BindId(R.id.but_cancel)
    private Button butCancel;

    private CancelOtherPresenter cancelOtherPresenter;

    private BaseQuickAdapter<LotteryLogDetaisEntity,BaseViewHolder> baseViewHolderBaseQuickAdapter;

    private int otherType = 0;

    private int otherId = 0;

    private int page = 1;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            otherType = bundle.getInt("type");
            otherId = bundle.getInt("otherId");
        }

        baseViewHolderBaseQuickAdapter = new BaseQuickAdapter<LotteryLogDetaisEntity, BaseViewHolder>(R.layout.item_lottery_log) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryLogDetaisEntity item) {
                helper.setText(R.id.log_name,item.getLottery_class_name())
                        .setText(R.id.log_time,"共"+item.getLottery_num_total()+"注")
                        .setText(R.id.money,item.getLottery_price_total()+"元");
                switch (otherType){
                    case 20:
                        helper.setText(R.id.state,"待开奖");
                        break;
                    case 30:
                        helper.setText(R.id.state,"已中奖");
                        break;
                    case 40:
                        helper.setText(R.id.state,"未中奖");
                        break;
                    case 0:
                        helper.setText(R.id.state,"已取消");
                        break;
                }
                helper.itemView.setOnClickListener(v -> {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("otherId",item.getOrder_id());
                    bundle1.putInt("otherClassId",item.getLottery_class_id());
                    startActivity(BetLotteryLogDetailsActivity.class,bundle1);
                });
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lottery_log_details);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("订单详情")
                .builder();
    }

    @Override
    protected void initView() {
        if(otherType == 20){
            butCancel.setVisibility(View.VISIBLE);
        }
        logRefreshView.setOnRefreshListener(this);
        baseViewHolderBaseQuickAdapter.setOnLoadMoreListener(this,logRecyclerView);
        baseViewHolderBaseQuickAdapter.disableLoadMoreIfNotFullPage();
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logRecyclerView.setAdapter(baseViewHolderBaseQuickAdapter);
        logRecyclerView.setHasFixedSize(true);
    }

    @OnClick({R.id.but_cancel})
    private void cancelOther(){
        //取消订单
        cancelOtherPresenter.cancelOther(otherId);
    }


    @Override
    protected void initPresenter() {
        presenter = new LotteryLogDetalsPresenter();
        cancelOtherPresenter = new CancelOtherPresenter();
    }

    @Override
    protected void fetchData() {
        logRefreshView.setRefreshing(true);
        presenter.lotteryLogDetails(otherId,page);
        cancelOtherPresenter.attachView(new BaseView<Integer>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(Integer integer) {
                if(integer == 200){
                    Toast.makeText(LiuHeApplication.context,"已取消",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.lotteryLogDetails(otherId,page);
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        logRefreshView.setRefreshing(false);
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(List<LotteryLogDetaisEntity> lotteryLogDetaisEntities) {
        logRefreshView.setRefreshing(false);
        if(page == 1){
            if(lotteryLogDetaisEntities != null && lotteryLogDetaisEntities.size() > 0){
                baseViewHolderBaseQuickAdapter.setNewData(lotteryLogDetaisEntities);
                if(lotteryLogDetaisEntities.size()  < 10){
                    baseViewHolderBaseQuickAdapter.loadMoreEnd();
                }
            }
        }else{
            if(lotteryLogDetaisEntities != null && lotteryLogDetaisEntities.size() == 10){
                baseViewHolderBaseQuickAdapter.loadMoreComplete();
            }else{
                baseViewHolderBaseQuickAdapter.loadMoreEnd();
            }
            baseViewHolderBaseQuickAdapter.addData(lotteryLogDetaisEntities);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.lotteryLogDetails(otherId,page);
    }
}

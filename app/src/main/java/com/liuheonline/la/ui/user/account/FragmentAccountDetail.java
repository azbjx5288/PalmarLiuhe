package com.liuheonline.la.ui.user.account;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PdlogEntity;
import com.liuheonline.la.event.StartTimeEvent;
import com.liuheonline.la.mvp.presenter.PdlogPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.utils.CashTypeUtil;
import com.liuheonline.la.utils.DateUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class FragmentAccountDetail extends BaseMvpFragment<BaseView<PdlogEntity>,PdlogPresenter,PdlogEntity> implements  SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    @BindId(R.id.fragment_time)
    TextView time;
    @BindId(R.id.fragment_in)
    TextView in;
    @BindId(R.id.fragment_out)
    TextView out;

    @BindId(R.id.refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.recycler)
    private RecyclerView recyclerView;

    int year = Calendar.getInstance().get(Calendar.YEAR);

    private BaseQuickAdapter<PdlogEntity.PdlogBean,BaseViewHolder> baseQuickAdapter;

    private int page = 1;

    String starttime = "";
    @Override
    protected void initPresenter() {
        presenter = new PdlogPresenter();
    }

    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<PdlogEntity.PdlogBean, BaseViewHolder>(R.layout.item_account_detail) {
            @Override
            protected void convert(BaseViewHolder helper, PdlogEntity.PdlogBean item) {

                helper.setText(R.id.detail_time, CashTypeUtil.getTime((long) item.getLg_add_time()));
                helper.setImageResource(R.id.detail_img,item.getLg_spending()==1?R.mipmap.zhichu:R.mipmap.tixian);
                helper.setText(R.id.detail_type,item.getLg_type());
                helper.setText(R.id.detail_price,item.getLg_av_amount().equals("0.00")?item.getLg_freeze_amount():item.getLg_av_amount());
                String format = "yyyy-MM";
                time.setText(DateUtil.timeStamp2Date(item.getLg_add_time()+"",format));
                helper.setOnClickListener(R.id.accountinfo_linear, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("PdlogEntity",item);
                        startActivity(AccountDetail.class,bundle);
                    }
                });
            }
        };
    }

    @Override
    protected void initViews() {
        swipeRefreshLayout.setOnRefreshListener(this);
        baseQuickAdapter.setOnLoadMoreListener(this,recyclerView);
        baseQuickAdapter.disableLoadMoreIfNotFullPage();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_accountdetail;
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.pdlog(page,10,starttime);
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
    public void successed(PdlogEntity pdlogEntities) {
        swipeRefreshLayout.setRefreshing(false);
        if(page == 1){
            if(pdlogEntities != null && pdlogEntities.getPdlog().size() > 0){
                baseQuickAdapter.setNewData(pdlogEntities.getPdlog());
                if(pdlogEntities.getPdlog().size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                //空数据
                baseQuickAdapter.setNewData(null);
            }
        }else{
            if(pdlogEntities != null && pdlogEntities.getPdlog().size() == 10){

                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(pdlogEntities.getPdlog());
        }
        in.setText("收入"+pdlogEntities.getMember().getIncome()+""+"元");
        out.setText("支出"+pdlogEntities.getMember().getSpending()+"元");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startTimeEvent(StartTimeEvent startTimeEvent) {
        Log.w("JpushEvent","true");
        starttime = startTimeEvent.getStarttime();
        presenter.pdlog(page,10,starttime);
    }
    @Override
    public void onRefresh() {
        page = 1;
        presenter.pdlog(page,10,starttime);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.pdlog(page,10,starttime);
    }
}

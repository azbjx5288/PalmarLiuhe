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
import com.liuheonline.la.entity.PdCashEntity;
import com.liuheonline.la.event.StartTimeEvent;
import com.liuheonline.la.mvp.presenter.PdCashPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.utils.CashTypeUtil;
import com.liuheonline.la.utils.DateUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class FragmentWithdrawDetail extends BaseMvpFragment<BaseView<PdCashEntity>, PdCashPresenter, PdCashEntity> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindId(R.id.fragment_time)
    TextView time;
    @BindId(R.id.fragment_in)
    TextView in;
    @BindId(R.id.fragment_out)
    TextView out;

    String starttime = "";
    @BindId(R.id.refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.recycler)
    private RecyclerView recyclerView;

    int year = Calendar.getInstance().get(Calendar.YEAR);

    private BaseQuickAdapter<PdCashEntity.PdcashBean, BaseViewHolder> baseQuickAdapter;

    private int page = 1;

    @Override
    protected void initPresenter() {
        presenter = new PdCashPresenter();
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<PdCashEntity.PdcashBean, BaseViewHolder>(R.layout.item_account_detail) {
            @Override
            protected void convert(BaseViewHolder helper, PdCashEntity.PdcashBean item) {
                helper.setText(R.id.detail_time, CashTypeUtil.getTime((long) item.getPdc_add_time()));
                helper.setImageResource(R.id.detail_img, CashTypeUtil.getWithdrawImg(item.getPdc_payment_state()));
                helper.setText(R.id.detail_type, CashTypeUtil.getWithdrawDes(item.getPdc_payment_state()));
                helper.setText(R.id.detail_price, item.getPdc_amount() + "");
                String format = "yyyy-MM";
                time.setText(DateUtil.timeStamp2Date(item.getPdc_add_time() + "", format));
                helper.setOnClickListener(R.id.accountinfo_linear, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("PdCashEntity", item);
                        startActivity(PdcashDetail.class, bundle);
                    }
                });
            }
        };
    }

    @Override
    protected void initViews() {
        swipeRefreshLayout.setOnRefreshListener(this);
        baseQuickAdapter.setOnLoadMoreListener(this, recyclerView);
        baseQuickAdapter.disableLoadMoreIfNotFullPage();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_accountdetail;
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.pdcash(page, 10, starttime);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startTimeEvent(StartTimeEvent startTimeEvent) {
        Log.w("JpushEvent", "true");
        starttime = startTimeEvent.getStarttime();
        presenter.pdcash(page, 10, starttime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void successed(PdCashEntity pdlogEntities) {
        if (pdlogEntities != null) {
            in.setText("收入" + pdlogEntities.getMember().getIncome() + "元");
            out.setText("支出" + pdlogEntities.getMember().getSpending() + "元");
        }
        swipeRefreshLayout.setRefreshing(false);
        if (page == 1) {
            if (pdlogEntities != null && pdlogEntities.getPdcash().size() > 0) {
                baseQuickAdapter.setNewData(pdlogEntities.getPdcash());
                if (pdlogEntities.getPdcash().size() < 10) {
                    baseQuickAdapter.loadMoreEnd();
                }
            } else {
                //空数据
                baseQuickAdapter.setNewData(null);
            }
        } else {
            if (pdlogEntities != null && pdlogEntities.getPdcash().size() == 10) {

                baseQuickAdapter.loadMoreComplete();
            } else {
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(pdlogEntities.getPdcash());
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.pdcash(page, 10, starttime);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.pdcash(page, 10, starttime);
    }
}

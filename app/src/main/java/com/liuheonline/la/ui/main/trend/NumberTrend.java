package com.liuheonline.la.ui.main.trend;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.mvp.presenter.HistoryLotteryPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NumberTrend extends BaseMVPActivity<BaseView<List<LotteryEntity.LotteryBean>>,
        HistoryLotteryPresenter, List<LotteryEntity.LotteryBean>> implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindId(R.id.numbertrend_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.numbertrend_recycler)
    private RecyclerView recyclerView;

    private BaseQuickAdapter<LotteryEntity.LotteryBean, BaseViewHolder> baseQuickAdapter;

    CustomDatePicker customDatePicker;
    private int page = 1;
    int year = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    protected void initPresenter() {
        presenter = new HistoryLotteryPresenter();
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<LotteryEntity.LotteryBean, BaseViewHolder>(R.layout.item_number_trend) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryEntity.LotteryBean data) {

                helper.setText(R.id.trend_periods, data.getPeriods() + " 期");
                helper.setText(R.id.trend_num1, data.getNum1() + "");
                helper.setBackgroundRes(R.id.trend_num1, LotteryTypeUtil.getBallBG(data.getNum1_color_id()));
                helper.setText(R.id.trend_num2, data.getNum2() + "");
                helper.setBackgroundRes(R.id.trend_num2, LotteryTypeUtil.getBallBG(data.getNum2_color_id()));
                helper.setText(R.id.trend_num3, data.getNum3() + "");
                helper.setBackgroundRes(R.id.trend_num3, LotteryTypeUtil.getBallBG(data.getNum3_color_id()));
                helper.setText(R.id.trend_num4, data.getNum4() + "");
                helper.setBackgroundRes(R.id.trend_num4, LotteryTypeUtil.getBallBG(data.getNum4_color_id()));
                helper.setText(R.id.trend_num5, data.getNum5() + "");
                helper.setBackgroundRes(R.id.trend_num5, LotteryTypeUtil.getBallBG(data.getNum5_color_id()));
                helper.setText(R.id.trend_num6, data.getNum6() + "");
                helper.setBackgroundRes(R.id.trend_num6, LotteryTypeUtil.getBallBG(data.getNum6_color_id()));
                helper.setText(R.id.trend_num7, data.getNum_s() + "");
                helper.setBackgroundRes(R.id.trend_num7, LotteryTypeUtil.getBallBG(data.getNum_s_color_id()));

            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_numbertrend);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        new DefaultNavigationBar.Builder(this)
                .setTitle("号码走势")
                .setLeftIconVisibility(false)
                .setRightText("年份")
                .setRightClickListener(view -> customDatePicker.show())
                .builder();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String stryear = time.split(" ")[0];
                year = Integer.parseInt(stryear.substring(0, 4));
                JLog.w("the year", year + "");
                page = 1;
                presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        baseQuickAdapter.setOnLoadMoreListener(this, recyclerView);
        baseQuickAdapter.disableLoadMoreIfNotFullPage();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<LotteryEntity.LotteryBean> lotteryBeans) {
        swipeRefreshLayout.setRefreshing(false);
        if (page == 1) {
            if (lotteryBeans != null && lotteryBeans.size() > 0) {
                baseQuickAdapter.setNewData(lotteryBeans);
                if (lotteryBeans.size() < 10) {
                    baseQuickAdapter.loadMoreEnd();
                }
            } else {
                baseQuickAdapter.setNewData(lotteryBeans);//空数据
            }
        } else {
            if (lotteryBeans != null && lotteryBeans.size() == 10) {
                baseQuickAdapter.loadMoreComplete();
            } else {
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(lotteryBeans);
        }
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }
}

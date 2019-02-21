package com.liuheonline.la.ui.history;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.mvp.presenter.HistoryLotteryPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author: aini
 * @date 2018/7/16 11:55
 * @description 历史开奖
 */
public class HistoryActivity extends BaseMVPActivity<BaseView<List<LotteryEntity.LotteryBean>>,
        HistoryLotteryPresenter, List<LotteryEntity.LotteryBean>> implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindId(R.id.history_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.history_recycler)
    private RecyclerView recyclerView;

    CustomDatePicker customDatePicker;
    int year = Calendar.getInstance().get(Calendar.YEAR);

    private BaseQuickAdapter<LotteryEntity.LotteryBean, BaseViewHolder> baseQuickAdapter;

    private int page = 1;

    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<LotteryEntity.LotteryBean, BaseViewHolder>(R.layout.item_history) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryEntity.LotteryBean data) {
                if (helper.getAdapterPosition() == 0) {
                    helper.setVisible(R.id.is_new, false);
                }
                helper.setText(R.id.color_type, data.getSpecies_name());
                helper.setText(R.id.issue, data.getPeriods() + "");
                //根据彩种不停加载不同的布局--------布局是同一个布局   只是分别显示隐藏
                if (data.getLottery_id() == 7) {//六合
                    helper.setText(R.id.time, data.getLottery_time());
                    helper.getView(R.id.title_top).setVisibility(View.VISIBLE);
                    helper.getView(R.id.ssc_top).setVisibility(View.GONE);
                    helper.getView(R.id.number_liuhe).setVisibility(View.VISIBLE);
                    helper.getView(R.id.number_ssc).setVisibility(View.GONE);
                    helper.getView(R.id.number_other).setVisibility(View.GONE);
                    helper.setText(R.id.number01, data.getNum1() + "");
                    helper.setBackgroundRes(R.id.number01, LotteryTypeUtil.getBallBG(data.getNum1_color_id()));
                    helper.setText(R.id.number02, data.getNum2() + "");
                    helper.setBackgroundRes(R.id.number02, LotteryTypeUtil.getBallBG(data.getNum2_color_id()));
                    helper.setText(R.id.number03, data.getNum3() + "");
                    helper.setBackgroundRes(R.id.number03, LotteryTypeUtil.getBallBG(data.getNum3_color_id()));
                    helper.setText(R.id.number04, data.getNum4() + "");
                    helper.setBackgroundRes(R.id.number04, LotteryTypeUtil.getBallBG(data.getNum4_color_id()));
                    helper.setText(R.id.number05, data.getNum5() + "");
                    helper.setBackgroundRes(R.id.number05, LotteryTypeUtil.getBallBG(data.getNum5_color_id()));
                    helper.setText(R.id.number06, data.getNum6() + "");
                    helper.setBackgroundRes(R.id.number06, LotteryTypeUtil.getBallBG(data.getNum6_color_id()));
                    helper.setText(R.id.number07, data.getNum_s() + "");
                    helper.setBackgroundRes(R.id.number07, LotteryTypeUtil.getBallBG(data.getNum_s_color_id()));
                    int sp = SharedperfencesUtil.getInt(HistoryActivity.this, "zodiacid");
                    if (sp < 1) {
                        sp = 1;
                    }
                    if (sp == 2) {
                        //设置生肖背景图
                        helper.setBackgroundColor(R.id.animal01, Color.parseColor("#00000000"));
                        helper.setBackgroundColor(R.id.animal02, Color.parseColor("#00000000"));
                        helper.setBackgroundColor(R.id.animal03, Color.parseColor("#00000000"));
                        helper.setBackgroundColor(R.id.animal04, Color.parseColor("#00000000"));
                        helper.setBackgroundColor(R.id.animal05, Color.parseColor("#00000000"));
                        helper.setBackgroundColor(R.id.animal06, Color.parseColor("#00000000"));
                        helper.setBackgroundColor(R.id.animal07, Color.parseColor("#00000000"));
                        helper.setText(R.id.animal01, data.getNum1_zodiac());
                        helper.setText(R.id.animal02, data.getNum2_zodiac());
                        helper.setText(R.id.animal03, data.getNum3_zodiac());
                        helper.setText(R.id.animal04, data.getNum4_zodiac());
                        helper.setText(R.id.animal05, data.getNum5_zodiac());
                        helper.setText(R.id.animal06, data.getNum6_zodiac());
                        helper.setText(R.id.animal07, data.getNum_s_zodiac());
                    } else {
                        //设置生肖背景图
                        helper.setBackgroundRes(R.id.animal01, LotteryTypeUtil.getZodiac(data.getNum1_zodiac() + ""));
                        helper.setBackgroundRes(R.id.animal02, LotteryTypeUtil.getZodiac(data.getNum2_zodiac() + ""));
                        helper.setBackgroundRes(R.id.animal03, LotteryTypeUtil.getZodiac(data.getNum3_zodiac() + ""));
                        helper.setBackgroundRes(R.id.animal04, LotteryTypeUtil.getZodiac(data.getNum4_zodiac() + ""));
                        helper.setBackgroundRes(R.id.animal05, LotteryTypeUtil.getZodiac(data.getNum5_zodiac() + ""));
                        helper.setBackgroundRes(R.id.animal06, LotteryTypeUtil.getZodiac(data.getNum6_zodiac() + ""));
                        helper.setBackgroundRes(R.id.animal07, LotteryTypeUtil.getZodiac(data.getNum_s_zodiac() + ""));
                        helper.setText(R.id.animal01, "");
                        helper.setText(R.id.animal02, "");
                        helper.setText(R.id.animal03, "");
                        helper.setText(R.id.animal04, "");
                        helper.setText(R.id.animal05, "");
                        helper.setText(R.id.animal06, "");
                        helper.setText(R.id.animal07, "");
                    }
                } else if (data.getLottery_id() == 8) {//北京PK拾
                    helper.setText(R.id.time, data.getLottery_time());
                    helper.getView(R.id.title_top).setVisibility(View.VISIBLE);
                    helper.getView(R.id.ssc_top).setVisibility(View.GONE);
                    helper.getView(R.id.number_liuhe).setVisibility(View.GONE);
                    helper.getView(R.id.number_ssc).setVisibility(View.GONE);
                    helper.getView(R.id.number_other).setVisibility(View.VISIBLE);
                    String[] strs = data.getNumber().split("\\|");
                    helper.setText(R.id.number21, strs[0]);
                    helper.setText(R.id.number22, strs[1]);
                    helper.setText(R.id.number23, strs[2]);
                    helper.setText(R.id.number24, strs[3]);
                    helper.setText(R.id.number25, strs[4]);
                    helper.setText(R.id.number26, strs[5]);
                    helper.setText(R.id.number27, strs[6]);
                    helper.setText(R.id.number28, strs[7]);
                    helper.setText(R.id.number29, strs[8]);
                    helper.setText(R.id.number210, strs[9]);
                    helper.setBackgroundRes(R.id.number21, LotteryTypeUtil.getBallBG(strs[0]))
                            .setBackgroundRes(R.id.number22, LotteryTypeUtil.getBallBG(strs[1]))
                            .setBackgroundRes(R.id.number23, LotteryTypeUtil.getBallBG(strs[2]))
                            .setBackgroundRes(R.id.number24, LotteryTypeUtil.getBallBG(strs[3]))
                            .setBackgroundRes(R.id.number25, LotteryTypeUtil.getBallBG(strs[4]))
                            .setBackgroundRes(R.id.number26, LotteryTypeUtil.getBallBG(strs[5]))
                            .setBackgroundRes(R.id.number27, LotteryTypeUtil.getBallBG(strs[6]))
                            .setBackgroundRes(R.id.number28, LotteryTypeUtil.getBallBG(strs[7]))
                            .setBackgroundRes(R.id.number29, LotteryTypeUtil.getBallBG(strs[8]))
                            .setBackgroundRes(R.id.number210, LotteryTypeUtil.getBallBG(strs[9]));
                } else if (data.getLottery_id() == 9) {//时时彩
                    helper.setText(R.id.ssc_time, data.getLottery_time());
                    helper.setText(R.id.number, data.getPeriods() + "");
                    helper.getView(R.id.title_top).setVisibility(View.GONE);
                    helper.getView(R.id.ssc_top).setVisibility(View.VISIBLE);
                    helper.getView(R.id.number_liuhe).setVisibility(View.GONE);
                    helper.getView(R.id.number_ssc).setVisibility(View.VISIBLE);
                    helper.getView(R.id.number_other).setVisibility(View.GONE);
                    String[] strs = data.getNumber().split("\\|");
                    helper.setText(R.id.number11, strs[0]);
                    helper.setText(R.id.number12, strs[1]);
                    helper.setText(R.id.number13, strs[2]);
                    helper.setText(R.id.number14, strs[3]);
                    helper.setText(R.id.number15, strs[4]);
                    helper.setBackgroundRes(R.id.number11, LotteryTypeUtil.getPureBallBG(8))
                            .setBackgroundRes(R.id.number12, LotteryTypeUtil.getPureBallBG(8))
                            .setBackgroundRes(R.id.number13, LotteryTypeUtil.getPureBallBG(8))
                            .setBackgroundRes(R.id.number14, LotteryTypeUtil.getPureBallBG(8))
                            .setBackgroundRes(R.id.number15, LotteryTypeUtil.getPureBallBG(8));
                }
               /* helper.setImageResource(R.id.animal01,LotteryTypeUtil.getZodiac(data.getNum1_zodiac()));
                helper.setImageResource(R.id.animal02,LotteryTypeUtil.getZodiac(data.getNum2_zodiac()));
                helper.setImageResource(R.id.animal03,LotteryTypeUtil.getZodiac(data.getNum3_zodiac()));
                helper.setImageResource(R.id.animal04,LotteryTypeUtil.getZodiac(data.getNum4_zodiac()));
                helper.setImageResource(R.id.animal05,LotteryTypeUtil.getZodiac(data.getNum5_zodiac()));
                helper.setImageResource(R.id.animal06,LotteryTypeUtil.getZodiac(data.getNum6_zodiac()));
                helper.setImageResource(R.id.animal07,LotteryTypeUtil.getZodiac(data.getNum_s_zodiac()));*/
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        new DefaultNavigationBar.Builder(this)
                .setTitle("历史开奖")
                .setLeftIconVisibility(false)
                .setRightText("年份")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDatePicker.show();
                    }
                })
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
                Log.w("the year", year + "");
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
    protected void initPresenter() {
        presenter = new HistoryLotteryPresenter();
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
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
    public void successed(List<LotteryEntity.LotteryBean> lotteryBeans) {
        JLog.e("历史开奖", lotteryBeans);
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
    public void onRefresh() {
        page = 1;
        presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        JLog.e("历史开奖", page);
        presenter.getHistoryLottery(year, page, 10, getIntent().getExtras().getInt("lotteryid") == 0 ? 7 : getIntent().getExtras().getInt("lotteryid"));
    }
}

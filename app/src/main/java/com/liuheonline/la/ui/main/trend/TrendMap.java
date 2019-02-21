package com.liuheonline.la.ui.main.trend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.TrendMapEntity;
import com.liuheonline.la.mvp.presenter.TrendMapPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.popu.TrendMapPeriodsPopu;
import com.liuheonline.la.ui.widget.popu.TrendMapTypePopu;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class TrendMap extends BaseMVPActivity<BaseView<List<TrendMapEntity>>,TrendMapPresenter,List<TrendMapEntity>> implements SwipeRefreshLayout.OnRefreshListener,OnChartValueSelectedListener {

    @BindId(R.id.type)
    private TextView typeView;//类型

    @BindId(R.id.periods)
    private TextView periodsView;//期数

    @BindId(R.id.trendmap_swipe)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.mPieChart)
    private PieChart mPieChart;

    private TrendMapTypePopu trendMapTypePopu;

    private TrendMapPeriodsPopu trendMapPeriodsPopu;

    private int limit = 50;

    private String types = "zodiac";

    private int sid;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            sid = bundle.getInt("lotteryid");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_trendmap);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("热图走势")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        trendMapTypePopu = new TrendMapTypePopu(this, type -> {
            types = type;
            switch (type){
                case "zodiac":
                    typeView.setText("生肖");
                    break;
                case "color":
                    typeView.setText("波色");
                    break;
                case "fiveelements":
                    typeView.setText("五行");
                    break;
                case "size":
                    typeView.setText("大小");
                    break;
                case "odevity":
                    typeView.setText("单双");
                    break;
                case "head":
                    typeView.setText("头数");
                    break;
                case "footer":
                    typeView.setText("尾数");
                    break;
            }
            fetchData();
        });

        trendMapPeriodsPopu = new TrendMapPeriodsPopu(this, periods1 -> {
            limit = periods1;
            periodsView.setText("近"+periods1+"期");
            fetchData();
        });
    }


    @OnClick({R.id.map_type,R.id.map_periods})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.map_type:
                trendMapTypePopu.showPopupWindow(R.id.map_type);
                break;
            case R.id.map_periods:
                trendMapPeriodsPopu.showPopupWindow(R.id.map_periods);
                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new TrendMapPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getTrendMap(limit,types,sid);
    }


    @Override
    public void onLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void successed(List<TrendMapEntity> trendMapEntities) {
        swipeRefreshLayout.setRefreshing(false);
        //饼状图
        mPieChart = findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i=0;i<trendMapEntities.size();i++){
            entries.add(new PieEntry((float) trendMapEntities.get(i).getPercent(), LotteryTypeUtil.getChinese(trendMapEntities.get(i).getWin_result())));
        }



        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);

        //设置数据
        setData(entries);

    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
       /* for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);*/
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("");
        return s;
    }
    @Override
    public void onRefresh() {
        presenter.getTrendMap(limit,types,getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}

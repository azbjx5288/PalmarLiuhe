package com.liuheonline.la.ui.main.trend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class LotteryTrend extends BaseMVPActivity {

    int lotteryId = 0;

    Bundle bundle = new Bundle();

    @OnClick({R.id.trend_number, R.id.trend_zodiac, R.id.trend_headage, R.id.trend_Mantissa, R.id.trend_double, R.id.trend_big, R.id.trend_five, R.id.trend_boson, R.id.trend_composite, R.id.trend_hot})
    private void onClick(View view) {
        Bundle bundle = getIntent().getExtras();
        switch (view.getId()) {
            case R.id.trend_number://号码走势
                startActivity(NumberTrend.class, bundle);
                break;
            case R.id.trend_zodiac://生肖走势
                startActivity(ZodiacTrend.class, bundle);
                break;
            case R.id.trend_headage://头数走势
                startActivity(HeadageTrend.class, bundle);
                break;
            case R.id.trend_Mantissa://尾数走势
                startActivity(FooterTrend.class, bundle);
                break;
            case R.id.trend_double://单双走势
                startActivity(OdevityTrend.class, bundle);
                break;
            case R.id.trend_big://大小走势
                startActivity(SizeTrend.class, bundle);
                break;
            case R.id.trend_five://五行走势
                startActivity(FiveElementsTrend.class, bundle);
                break;
            case R.id.trend_boson://波色走势
                startActivity(ColorTrend.class, bundle);
                break;
            case R.id.trend_composite://合数走势
                startActivity(AddTrend.class, bundle);
                break;
            case R.id.trend_hot://热图走势
                startActivity(TrendMap.class, bundle);
                break;
        }
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lotterytrend);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("彩票走势")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        lotteryId = intent.getExtras().getInt("lotteryid");
        bundle.putInt("lotteryid", lotteryId);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}

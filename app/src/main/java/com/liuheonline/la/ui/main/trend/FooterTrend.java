package com.liuheonline.la.ui.main.trend;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.FooterEntity;
import com.liuheonline.la.mvp.presenter.FooterPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FooterTrend extends BaseMVPActivity<BaseView<List<FooterEntity>>,FooterPresenter,List<FooterEntity>> implements  SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @BindId(R.id.zodiac_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;
    @BindId(R.id.zodiac_recycler)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<FooterEntity,BaseViewHolder> baseQuickAdapter;
    int p = 1;
    CustomDatePicker customDatePicker;
    int year = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    protected void initPresenter() {
        presenter = new FooterPresenter();
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getFooter(year,p,10,getIntent().getExtras().getInt("lotteryid"));
    }


    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<FooterEntity, BaseViewHolder>(R.layout.item_footer_trend) {
            @Override
            protected void convert(BaseViewHolder helper, FooterEntity data) {
                helper.setText(R.id.footer_periods,data.getPeriods()+"");
                helper.setText(R.id.footer_num0,data.getFooter0()+"");
                helper.setText(R.id.footer_num1,data.getFooter1()+"");
                helper.setText(R.id.footer_num2,data.getFooter2()+"");
                helper.setText(R.id.footer_num3,data.getFooter3()+"");
                helper.setText(R.id.footer_num4,data.getFooter4()+"");
                helper.setText(R.id.footer_num5,data.getFooter5()+"");
                helper.setText(R.id.footer_num6,data.getFooter6()+"");
                helper.setText(R.id.footer_num7,data.getFooter7()+"");
                helper.setText(R.id.footer_num8,data.getFooter8()+"");
                helper.setText(R.id.footer_num9,data.getFooter9()+"");
                helper.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                if (helper.getAdapterPosition()%2==0){
                    helper.itemView.setBackgroundColor(Color.parseColor("#efefef"));
                }

                helper.setBackgroundRes(R.id.footer_num0,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num0,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num1,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num1,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num2,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num2,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num3,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num3,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num4,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num4,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num5,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num5,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num6,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num6,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num7,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num7,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num8,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num8,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.footer_num9,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.footer_num9,getResources().getColor(R.color.deep_gray));
                switch (data.getWin_result()){
                    case "footer0":
                        helper.setBackgroundRes(R.id.footer_num0,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num0,getResources().getColor(R.color.white));
                        break;
                    case "footer1":
                        helper.setBackgroundRes(R.id.footer_num1,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num1,getResources().getColor(R.color.white));
                        break;
                    case "footer2":
                        helper.setBackgroundRes(R.id.footer_num2,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num2,getResources().getColor(R.color.white));
                        break;
                    case "footer3":
                        helper.setBackgroundRes(R.id.footer_num3,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num3,getResources().getColor(R.color.white));
                        break;
                    case "footer4":
                        helper.setBackgroundRes(R.id.footer_num4,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num4,getResources().getColor(R.color.white));
                        break;
                    case "footer5":
                        helper.setBackgroundRes(R.id.footer_num5,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num5,getResources().getColor(R.color.white));
                        break;
                    case "footer6":
                        helper.setBackgroundRes(R.id.footer_num6,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num6,getResources().getColor(R.color.white));
                        break;
                    case "footer7":
                        helper.setBackgroundRes(R.id.footer_num7,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num7,getResources().getColor(R.color.white));
                        break;
                    case "footer8":
                        helper.setBackgroundRes(R.id.footer_num8,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num8,getResources().getColor(R.color.white));
                        break;
                    case "footer9":
                        helper.setBackgroundRes(R.id.footer_num9,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.footer_num9,getResources().getColor(R.color.white));
                        break;
                }
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_footertrend);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        new DefaultNavigationBar.Builder(this)
                .setTitle("尾数走势")
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

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
              String stryear = time.split(" ")[0];
              year = Integer.parseInt(stryear.substring(0,4));
                Log.w("the year",year+"");
                p = 1;
                presenter.getFooter(year,p,10,getIntent().getExtras().getInt("lotteryid"));

            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }
    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void successed(List<FooterEntity> footerEntities) {
        swipeRefreshLayout.setRefreshing(false);
        if(p == 1){
            if(footerEntities != null && footerEntities.size() > 0){
                baseQuickAdapter.setNewData(footerEntities);
                if(footerEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                baseQuickAdapter.setNewData(footerEntities);//空数据
            }
        }else{
            if(footerEntities != null && footerEntities.size() == 10){

                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(footerEntities);
        }

    }


    @Override
    public void onRefresh() {
        p = 1;
        presenter.getFooter(year,p,10,getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    public void onLoadMoreRequested() {
        p ++;
        presenter.getFooter(year,p,10,getIntent().getExtras().getInt("lotteryid"));
    }
}

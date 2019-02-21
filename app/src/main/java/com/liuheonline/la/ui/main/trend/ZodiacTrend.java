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
import com.liuheonline.la.entity.ZodiacTrendEntity;
import com.liuheonline.la.mvp.presenter.ZodiacTrendPresenter;
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

public class ZodiacTrend extends BaseMVPActivity<BaseView<List<ZodiacTrendEntity>>,ZodiacTrendPresenter,List<ZodiacTrendEntity>> implements  SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @BindId(R.id.zodiac_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;
    @BindId(R.id.zodiac_recycler)
    private RecyclerView recyclerView;
    private BaseQuickAdapter<ZodiacTrendEntity,BaseViewHolder> baseQuickAdapter;
    int p = 1;
    @Override
    protected void initPresenter() {
        presenter = new ZodiacTrendPresenter();
    }
    CustomDatePicker customDatePicker;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getZodiacTrend(year,p,10,getIntent().getExtras().getInt("lotteryid"));
    }


    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<ZodiacTrendEntity, BaseViewHolder>(R.layout.item_zodiac_trend) {
            @Override
            protected void convert(BaseViewHolder helper, ZodiacTrendEntity data) {
                helper.setText(R.id.zodiac_periods,data.getPeriods()+"");
                helper.setText(R.id.zodiac_rat,data.getRat()+"");
                helper.setText(R.id.zodiac_cow,data.getCow()+"");
                helper.setText(R.id.zodiac_tiger,data.getTiger()+"");
                helper.setText(R.id.zodiac_rabbit,data.getRabbit()+"");
                helper.setText(R.id.zodiac_dragon,data.getDragon()+"");
                helper.setText(R.id.zodiac_snake,data.getSnake()+"");
                helper.setText(R.id.zodiac_horse,data.getHorse()+"");
                helper.setText(R.id.zodiac_sheep,data.getSheep()+"");
                helper.setText(R.id.zodiac_monkey,data.getMonkey()+"");
                helper.setText(R.id.zodiac_chicken,data.getChicken()+"");
                helper.setText(R.id.zodiac_dog,data.getDog()+"");
                helper.setText(R.id.zodiac_pig,data.getPig()+"");
                helper.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                if (helper.getAdapterPosition()%2==0){
                    helper.itemView.setBackgroundColor(Color.parseColor("#efefef"));
                }

                helper.setBackgroundRes(R.id.zodiac_rat,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_rat,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_cow,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_cow,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_tiger,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_tiger,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_rabbit,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_rabbit,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_dragon,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_dragon,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_snake,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_snake,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_horse,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_horse,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_sheep,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_sheep,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_monkey,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_monkey,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_chicken,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_chicken,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_dog,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_dog,getResources().getColor(R.color.deep_gray));
                helper.setBackgroundRes(R.id.zodiac_pig,R.drawable.text_solid_none_shape);
                helper.setTextColor(R.id.zodiac_pig,getResources().getColor(R.color.deep_gray));

                switch (data.getWin_result()){
                    case "rat":
                        helper.setBackgroundRes(R.id.zodiac_rat,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_rat,getResources().getColor(R.color.white));
                        break;
                    case "cow":
                        helper.setBackgroundRes(R.id.zodiac_cow,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_cow,getResources().getColor(R.color.white));
                        break;
                    case "tiger":
                        helper.setBackgroundRes(R.id.zodiac_tiger,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_tiger,getResources().getColor(R.color.white));
                        break;
                    case "rabbit":
                        helper.setBackgroundRes(R.id.zodiac_rabbit,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_rabbit,getResources().getColor(R.color.white));
                        break;
                    case "dragon":
                        helper.setBackgroundRes(R.id.zodiac_dragon,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_dragon,getResources().getColor(R.color.white));
                        break;
                    case "snake":
                        helper.setBackgroundRes(R.id.zodiac_snake,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_snake,getResources().getColor(R.color.white));
                        break;
                    case "horse":
                        helper.setBackgroundRes(R.id.zodiac_horse,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_horse,getResources().getColor(R.color.white));
                        break;
                    case "sheep":
                        helper.setBackgroundRes(R.id.zodiac_sheep,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_sheep,getResources().getColor(R.color.white));
                        break;
                    case "monkey":
                        helper.setBackgroundRes(R.id.zodiac_monkey,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_monkey,getResources().getColor(R.color.white));
                        break;
                    case "chicken":
                        helper.setBackgroundRes(R.id.zodiac_chicken,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_chicken,getResources().getColor(R.color.white));
                        break;
                        case "dog":
                             helper.setBackgroundRes(R.id.zodiac_dog,R.drawable.text_solid_blue_shape);
                             helper.setTextColor(R.id.zodiac_dog,getResources().getColor(R.color.white));
                        break;
                    case "pig":
                        helper.setBackgroundRes(R.id.zodiac_pig,R.drawable.text_solid_blue_shape);
                        helper.setTextColor(R.id.zodiac_pig,getResources().getColor(R.color.white));
                        break;

                }
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_zodiactrend);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        new DefaultNavigationBar.Builder(this)
                .setTitle("生肖走势")
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
                year = Integer.parseInt(stryear.substring(0,4));
                Log.w("the year",year+"");
                p = 1;
                presenter.getZodiacTrend(year,p,10,getIntent().getExtras().getInt("lotteryid"));

            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
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
    public void successed(List<ZodiacTrendEntity> zodiacTrendEntities) {
        swipeRefreshLayout.setRefreshing(false);
        if(p == 1){
            if(zodiacTrendEntities != null && zodiacTrendEntities.size() > 0){
                baseQuickAdapter.setNewData(zodiacTrendEntities);
                if(zodiacTrendEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                //空数据
                baseQuickAdapter.setNewData(zodiacTrendEntities);
            }
        }else{
            if(zodiacTrendEntities != null && zodiacTrendEntities.size() == 10){
                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(zodiacTrendEntities);
        }

    }


    @Override
    public void onRefresh() {
        p = 1;
        presenter.getZodiacTrend(year,p,10,getIntent().getExtras().getInt("lotteryid"));
    }

    @Override
    public void onLoadMoreRequested() {
        p ++;
        presenter.getZodiacTrend(year,p,10,getIntent().getExtras().getInt("lotteryid"));
    }
}

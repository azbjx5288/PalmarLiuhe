package com.liuheonline.la.ui.main.statistics;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.ZodiacStatisticsEntity;
import com.liuheonline.la.mvp.presenter.ZodiacStatisticsPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.PeriodsPopu;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class ZodicStatisticsInfo2 extends BaseMVPActivity<BaseView<ZodiacStatisticsEntity>,ZodiacStatisticsPresenter,ZodiacStatisticsEntity> {
    @BindId(R.id.num_info_recycle)
    private RecyclerView recyclerView;

    private BaseQuickAdapter<ZodiacStatisticsEntity.DoubleBean,BaseViewHolder> baseQuickAdapter;

    private DefaultNavigationBar defaultNavigationBar;

    private PeriodsPopu periodsPopu;

    @Override
    protected void initPresenter() {
        presenter = new ZodiacStatisticsPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getZodiacStatistics(30,2,getIntent().getExtras().getInt("lotteryid")==0?7+"":getIntent().getExtras().getInt("lotteryid")+"");
    }

    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<ZodiacStatisticsEntity.DoubleBean, BaseViewHolder>(R.layout.item_num_info) {
            @Override
            protected void convert(BaseViewHolder helper, ZodiacStatisticsEntity.DoubleBean item) {
                helper.setBackgroundRes(R.id.num_ball_info, LotteryTypeUtil.getZodiac(item.getDouble_attr()));
                helper.setText(R.id.num_index_info,item.getDouble_num()+"");
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_num_statistics_info);
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("数据统计")
                .setLeftIconVisibility(false)
                .setRightText("近30期")
                .setRightClickListener(view -> {
                    periodsPopu.showPopupWindow(R.id.right_text);
                })
                .builder();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);

        periodsPopu = new PeriodsPopu(this, issue -> {
            defaultNavigationBar.setRightText("近"+issue+"期");
            presenter.getZodiacStatistics(issue,2,getIntent().getExtras().getInt("lotteryid")==0?7+"":getIntent().getExtras().getInt("lotteryid")+"");
        });
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void successed(ZodiacStatisticsEntity zodiacStatisticsEntity) {
        if(zodiacStatisticsEntity != null && zodiacStatisticsEntity.getSingle().size() > 0){
            baseQuickAdapter.setNewData(zodiacStatisticsEntity.getDoubleX());
        }
    }

}

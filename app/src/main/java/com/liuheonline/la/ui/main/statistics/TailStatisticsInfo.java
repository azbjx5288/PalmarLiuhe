package com.liuheonline.la.ui.main.statistics;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.TailStatisticsEntity;
import com.liuheonline.la.mvp.presenter.TailStatisticsPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.PeriodsPopu;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class TailStatisticsInfo extends BaseMVPActivity<BaseView<TailStatisticsEntity>,TailStatisticsPresenter,TailStatisticsEntity> {
    @BindId(R.id.num_info_recycle)
    private RecyclerView recyclerView;

    private BaseQuickAdapter<TailStatisticsEntity.DoubleBean,BaseViewHolder> baseQuickAdapter;

    private DefaultNavigationBar defaultNavigationBar;

    private PeriodsPopu periodsPopu;

    @Override
    protected void initPresenter() {
        presenter = new TailStatisticsPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getTailStatistics(30,2,getIntent().getExtras().getInt("lotteryid")==0?7+"":getIntent().getExtras().getInt("lotteryid")+"");
    }

    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<TailStatisticsEntity.DoubleBean,BaseViewHolder>(R.layout.item_tail_info) {
            @Override
            protected void convert(BaseViewHolder helper, TailStatisticsEntity.DoubleBean item) {
                //helper.setBackgroundRes(R.id.num_ball_info, LotteryTypeUtil.getStasisticsBallBG(item.getTema_color_id()));
                helper.setText(R.id.num_ball_info,item.getDoubleX()+"");
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
            presenter.getTailStatistics(issue,2,getIntent().getExtras().getInt("lotteryid")==0?7+"":getIntent().getExtras().getInt("lotteryid")+"");
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(TailStatisticsEntity tailStatisticsEntity) {
        if(tailStatisticsEntity != null && tailStatisticsEntity.getDoubleX().size() > 0){
            baseQuickAdapter.setNewData(tailStatisticsEntity.getDoubleX());
        }
    }
}

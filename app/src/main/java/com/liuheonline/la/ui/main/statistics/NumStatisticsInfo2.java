package com.liuheonline.la.ui.main.statistics;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.NumStatisticsEntity;
import com.liuheonline.la.mvp.presenter.NumStatisticsPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.PeriodsPopu;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * 正码
 */

public class NumStatisticsInfo2 extends BaseMVPActivity<BaseView<NumStatisticsEntity>,NumStatisticsPresenter,NumStatisticsEntity> {
    @BindId(R.id.num_info_recycle)
    private RecyclerView recyclerView;

    private DefaultNavigationBar defaultNavigationBar;

    private PeriodsPopu periodsPopu;

    private BaseQuickAdapter<NumStatisticsEntity.CodeBean,BaseViewHolder> baseQuickAdapter;

    @Override
    protected void initPresenter() {
        presenter = new NumStatisticsPresenter();
    }


    @Override
    protected void fetchData() {
        presenter.getNumStatistics(30,2,getIntent().getExtras().getInt("lotteryid")==0?7+"":getIntent().getExtras().getInt("lotteryid")+"");
    }

    @Override
    protected void initData() {

        baseQuickAdapter = new BaseQuickAdapter<NumStatisticsEntity.CodeBean, BaseViewHolder>(R.layout.item_num_info) {
            @Override
            protected void convert(BaseViewHolder helper, NumStatisticsEntity.CodeBean item) {
                helper.setBackgroundRes(R.id.num_ball_info, LotteryTypeUtil.getStasisticsBallBG(item.getCode_color_id()));
                helper.setText(R.id.num_ball_info,item.getCode()+"");
                helper.setText(R.id.num_index_info,item.getCode_num()+"");
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
            presenter.getNumStatistics(issue,2,getIntent().getExtras().getInt("lotteryid")==0?7+"":getIntent().getExtras().getInt("lotteryid")+"");
        });
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void successed(NumStatisticsEntity numStatisticsEntity) {
        if(numStatisticsEntity != null && numStatisticsEntity.getTema().size() > 0){
            baseQuickAdapter.setNewData(numStatisticsEntity.getCode());
        }
    }




}

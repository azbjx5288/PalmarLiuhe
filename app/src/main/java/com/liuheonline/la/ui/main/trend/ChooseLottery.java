package com.liuheonline.la.ui.main.trend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.LotteryCategoryEntity;
import com.liuheonline.la.mvp.presenter.CategoryPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.history.HistoryActivity;
import com.liuheonline.la.ui.main.property.LotteryProperty;
import com.liuheonline.la.ui.main.statistics.LotteryStatistics;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class ChooseLottery extends BaseMVPActivity<BaseView<List<LotteryCategoryEntity>>,CategoryPresenter,List<LotteryCategoryEntity>> implements SwipeRefreshLayout.OnRefreshListener{

    @BindId(R.id.choose_recycle)
    private RecyclerView recyclerView;

    @BindId(R.id.choose_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private BaseQuickAdapter<LotteryCategoryEntity,BaseViewHolder> baseQuickAdapter;
    Class<?> toActivity = null;
    @Override
    protected void initPresenter() {

        presenter = new CategoryPresenter();

    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getLotteryCategory(1);
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<LotteryCategoryEntity, BaseViewHolder>(R.layout.item_chooselottery) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryCategoryEntity item) {
                helper.setText(R.id.choose_text,item.getTitle());
                //加载图片
                Glide.with(ChooseLottery.this)
                        .load(item.getPic_link())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                .error(R.mipmap.ic_launcher_round) //加载失败图片
                                .fallback(R.mipmap.ic_launcher_round) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into((ImageView) helper.getView(R.id.choose_img));
                helper.setOnClickListener(R.id.choose_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toActivity!=null){
                            Log.w("helper",item.getId()+"");
                            Bundle bundle = new Bundle();
                            bundle.putInt("lotteryid",item.getId());
                            startActivity(toActivity,bundle);
                            finish();
                        }
                    }
                });
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_chooselottery);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("选择彩种")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        switch (intent.getExtras().getString("fromactivity")){
            case "trend":
                toActivity = LotteryTrend.class;
                break;
            case "statistics":
                toActivity = LotteryStatistics.class;
                break;
            case "property":
                toActivity = LotteryProperty.class;
                break;
            case "history":
                toActivity = HistoryActivity.class;
                break;
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void successed(List<LotteryCategoryEntity> lotteryCategoryEntities) {
        swipeRefreshLayout.setRefreshing(false);
        if (lotteryCategoryEntities!=null&&lotteryCategoryEntities.size()>0){
            baseQuickAdapter.setNewData(lotteryCategoryEntities);
        }

    }

    @Override
    public void onRefresh() {
        presenter.getLotteryCategory(1);

    }
}

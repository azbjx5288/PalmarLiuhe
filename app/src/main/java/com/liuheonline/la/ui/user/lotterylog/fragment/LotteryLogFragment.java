package com.liuheonline.la.ui.user.lotterylog.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.LotteryLogEntity;
import com.liuheonline.la.mvp.presenter.LotteryLogPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.main.MainActivity;
import com.liuheonline.la.ui.user.lotterylog.BetLotteryLogDetailsActivity;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.util.ActivityManager;

import java.util.List;

/**
 * @author: aini
 * @date 2018/8/2 15:00
 * @description 投注记录
 */
public class LotteryLogFragment extends BaseMvpFragment<BaseView<List<LotteryLogEntity>>,
        LotteryLogPresenter, List<LotteryLogEntity>> implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    @BindId(R.id.log_refresh)
    private SwipeRefreshLayout logRefreshView;

    @BindId(R.id.log_recycler)
    private RecyclerView logRecyclerView;

    @BindId(R.id.notdata_linear)
    private LinearLayout notDataLinear;

    private BaseQuickAdapter<LotteryLogEntity, BaseViewHolder> baseViewHolderBaseQuickAdapter;

    private int otherType = 1;
    private String lotteryid = "";

    private int page = 1;

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            otherType = bundle.getInt("type");
            lotteryid = bundle.getString("lotteryid", "");
            JLog.d("type", otherType + "");
            JLog.d("lotteryid", lotteryid);
        }

        baseViewHolderBaseQuickAdapter = new BaseQuickAdapter<LotteryLogEntity, BaseViewHolder>(R.layout.item_lottery_log) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryLogEntity item) {
                helper.setText(R.id.log_name, item.getSpecies_name() + "-" + item.getLottery_class_name())
                        .setText(R.id.log_time, item.getIssue() + "期")
                        .setText(R.id.money, item.getLottery_amount() + "元");

                ImageView imageView = helper.getView(R.id.log_img);
                Glide.with(helper.itemView)
                        .load(ImageUrlUtil.getImgUrl(item.getImage_link(), 50))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                .error(R.mipmap.jiazaishibai) //加载失败图片
                                .fallback(R.mipmap.jiazaishibai) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(imageView);
                switch (item.getOrder_state()) {
                    case 20:
                        helper.setText(R.id.state, "待开奖");
                        break;
                    case 30:
                        helper.setText(R.id.state, "已中奖");
                        break;
                    case 40:
                        helper.setText(R.id.state, "未中奖");
                        break;
                    case 50:
                        helper.setText(R.id.state, "已取消");
                        break;
                }

                helper.itemView.setOnClickListener(v -> {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("otherId", item.getOrder_id());
                    startActivity(BetLotteryLogDetailsActivity.class, bundle1);
                });
            }
        };
    }

    @Override
    protected void initViews() {
        logRefreshView.setOnRefreshListener(this);
        baseViewHolderBaseQuickAdapter.setOnLoadMoreListener(this, logRecyclerView);
        baseViewHolderBaseQuickAdapter.disableLoadMoreIfNotFullPage();
        logRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        logRecyclerView.setAdapter(baseViewHolderBaseQuickAdapter);
        logRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initTitle(View view) {
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_lottery_log;
    }

    @OnClick({R.id.goto_bet})
    private void goToBet() {
        ActivityManager.getActivityManager().popAllActivity();
        Bundle bundle = new Bundle();
        bundle.putInt("index", 2);
        startActivity(MainActivity.class, bundle);
        getActivity().finish();
    }

    @Override
    protected void initPresenter() {
        presenter = new LotteryLogPresenter();
    }

    @Override
    protected void fetchData() {
        logRefreshView.setRefreshing(true);
        presenter.lotteryLog(otherType, lotteryid, page);
    }

    @Override
    public void onResume() {
        super.onResume();
        logRefreshView.setRefreshing(true);
        presenter.lotteryLog(otherType, lotteryid, page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.lotteryLog(otherType, lotteryid, page);
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        logRefreshView.setRefreshing(false);
        Toast.makeText(LiuHeApplication.context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(List<LotteryLogEntity> lotteryLogEntities) {
        logRefreshView.setRefreshing(false);
        notDataLinear.setVisibility(View.GONE);
        if (page == 1) {
            if (lotteryLogEntities != null && lotteryLogEntities.size() > 0) {
                baseViewHolderBaseQuickAdapter.setNewData(lotteryLogEntities);
                if (lotteryLogEntities.size() < 10) {
                    baseViewHolderBaseQuickAdapter.loadMoreEnd();
                }
            } else {
                //空数据
                notDataLinear.setVisibility(View.VISIBLE);
            }
        } else {
            if (lotteryLogEntities != null && lotteryLogEntities.size() == 10) {
                baseViewHolderBaseQuickAdapter.loadMoreComplete();
            } else {
                baseViewHolderBaseQuickAdapter.loadMoreEnd();
            }
            baseViewHolderBaseQuickAdapter.addData(lotteryLogEntities);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.lotteryLog(otherType, lotteryid, page);
    }
}

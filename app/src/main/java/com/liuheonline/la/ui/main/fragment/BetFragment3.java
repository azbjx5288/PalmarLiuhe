package com.liuheonline.la.ui.main.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.LotteryCategoryEntity;
import com.liuheonline.la.event.BetEvent;
import com.liuheonline.la.mvp.presenter.CategoryPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.bet.BetLottery3Activity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.TimeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author: aini
 * @date 2018/7/18 20:06
 * @description 投注
 */
public class BetFragment3 extends BaseMvpFragment<BaseView<List<LotteryCategoryEntity>>,
        CategoryPresenter, List<LotteryCategoryEntity>> implements SwipeRefreshLayout.OnRefreshListener {

    @BindId(R.id.bet_refresh)
    private SwipeRefreshLayout betRefreshLayout;

    @BindId(R.id.bet_recycler)
    private RecyclerView betRecyclerView;

    @BindId(R.id.checkbox)
    private CheckBox checkBox;

    private int id = 0;

    private CategoryPresenter categoryPresenter;

    private List<LotteryCategoryEntity> lotteryCategoryEntities;

    //菜单显示模式
    private boolean isLinear = false;

    //是否在开奖
    private boolean isOpen = false;

    private LinearLayoutManager linearLayoutManager;

    private GridLayoutManager gridLayoutManager;

    private SparseArray<CountDownTimer> countDownMap = new SparseArray<>();

    private BaseQuickAdapter<LotteryCategoryEntity, BaseViewHolder> baseViewHolderBaseQuickAdapter;

    public static BetFragment3 newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        BetFragment3 fragment = new BetFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initPresenter() {
        presenter = new CategoryPresenter();
        categoryPresenter = new CategoryPresenter();
    }

    @Override
    protected void initData() {
        baseViewHolderBaseQuickAdapter = new BaseQuickAdapter<LotteryCategoryEntity, BaseViewHolder>(R.layout.item_bet3) {
            @Override
            protected void convert(BaseViewHolder helper, LotteryCategoryEntity item) {
                helper.setIsRecyclable(false);
                RelativeLayout linear = helper.getView(R.id.linear);
                LinearLayout grid = helper.getView(R.id.grid);
                linear.setVisibility(!isLinear ? View.VISIBLE : View.GONE);
                grid.setVisibility(isLinear ? View.VISIBLE : View.GONE);
                helper.itemView.setOnClickListener(v -> {
                    if (SharedperfencesUtil.getInt(mContext, "userId") == 0) {
                        startActivity(LoginActivity.class);
                    } else {
                        SharedperfencesUtil.setInt(mContext, "checkSid", item.getId());
                        Bundle bundle = new Bundle();
                        bundle.putInt("sid", item.getLottery_id());
                        bundle.putLong("closeTime", item.getFengpan());
                        bundle.putString("rulesContent", item.getRules_content());
                        startActivity(BetLottery3Activity.class, bundle);
                    }
                });
                if (!isLinear) {
                    ImageView imageView = helper.getView(R.id.img);
                    Glide.with(helper.itemView)
                            .load(ImageUrlUtil.getImgUrl(item.getPic_link(), 50))
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                    .error(R.mipmap.jiazaishibai) //加载失败图片
                                    .fallback(R.mipmap.jiazaishibai) //url为空图片
                                    .centerCrop() // 填充方式
                                    .priority(Priority.HIGH) //优先级
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .into(imageView);
                    helper.setText(R.id.color_name, item.getTitle());

                    LotteryCategoryEntity.LotteryBean lotteryBean = item.getLottery();
                    if (lotteryBean != null) {
                        long periods12 = lotteryBean.getPeriods();
                        String periodsstr2 = periods12 + "";
                        long periodsint2 = 0;
                        if (periodsstr2.length() < 10) {
                            periodsint2 = lotteryBean.getPeriods();
                        } else {
                            periodsint2 = Integer.parseInt(periodsstr2.substring(8, periodsstr2.length()));
                        }
                        long finalPeriodsint2 = periodsint2;
                        String[] strs = lotteryBean.getNumber().split("\\|");
                        helper.setText(R.id.issue, "第" + finalPeriodsint2 + "期");
                        helper.setText(R.id.number01, strs[0]);
                        helper.setText(R.id.number02, strs[1]);
                        helper.setText(R.id.number03, strs[2]);
                        helper.setText(R.id.number04, strs[3]);
                        helper.setText(R.id.number05, strs[4]);


                        helper.setBackgroundRes(R.id.number01, LotteryTypeUtil.getPureBallBG(8))
                                .setBackgroundRes(R.id.number02, LotteryTypeUtil.getPureBallBG(8))
                                .setBackgroundRes(R.id.number03, LotteryTypeUtil.getPureBallBG(8))
                                .setBackgroundRes(R.id.number04, LotteryTypeUtil.getPureBallBG(8))
                                .setBackgroundRes(R.id.number05, LotteryTypeUtil.getPureBallBG(8));


                        long endTime = item.getLottery().getNext_time();
                        long startTime = Integer.parseInt(item.getHeaderTime());
                        long time = TimeUtil.getNextDate(startTime, endTime);
                        long periods1 = lotteryBean.getNext_periods();
                        String periodsstr = periods1 + "";
                        long periodsint = 0;
                        if (periodsstr.length() < 10) {
                            periodsint = lotteryBean.getNext_periods();
                        } else {
                            periodsint = Integer.parseInt(periodsstr.substring(8, periodsstr.length()));
                        }
                        long finalPeriodsint = periodsint;
                        CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long day = millisUntilFinished / 1000 / 60 / 60 / 24;
                                long h = millisUntilFinished / 1000 / 60 / 60 % 24;
                                long m = millisUntilFinished / 1000 / 60 % 60;
                                long s = millisUntilFinished / 1000 % 60;

                                helper.setText(R.id.next_issue, "第"
                                        + finalPeriodsint + "期"
                                        + "开奖剩余" + day + "天" + h + "时" + m + "分" + s + "秒");
                            }

                            @Override
                            public void onFinish() {
                                helper.setText(R.id.next_issue, "第"
                                        + finalPeriodsint + "期"
                                        + "开奖中");
                                refreshLotteryData(item.getId(), lotteryBean.getPeriods());
                            }
                        }.start();
                        countDownMap.put(helper.hashCode(), countDownTimer);
                    }
                } else {
                    ImageView imageView = helper.getView(R.id.img_grid);
                    Glide.with(helper.itemView)
                            .load(ImageUrlUtil.getImgUrl(item.getPic_link(), 50))
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                    .error(R.mipmap.jiazaishibai) //加载失败图片
                                    .fallback(R.mipmap.jiazaishibai) //url为空图片
                                    .centerCrop() // 填充方式
                                    .priority(Priority.HIGH) //优先级
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .into(imageView);
                    helper.setText(R.id.color_name_grid, item.getTitle());
                }
            }
        };
    }

    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id", 0);
        }
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        betRefreshLayout.setOnRefreshListener(this);
        betRecyclerView.setItemViewCacheSize(0);
        betRecyclerView.setLayoutManager(linearLayoutManager);
        betRecyclerView.setAdapter(baseViewHolderBaseQuickAdapter);
    }

    @Override
    protected void initTitle(View view) {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_bet;
    }


    private void refreshLotteryData(int sid, long issue) {
        categoryPresenter.attachView(new BaseView<List<LotteryCategoryEntity>>() {
            @Override
            public void onLoading() {
                Log.d("开奖", "获取最新开奖~~");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("开奖", "获取最新开奖失败~~");
            }

            @Override
            public void successed(List<LotteryCategoryEntity> lotteryCategoryEntities) {
                if (lotteryCategoryEntities != null && lotteryCategoryEntities.size() > 0) {
                    for (LotteryCategoryEntity lotteryCategoryEntity : lotteryCategoryEntities) {
                        if (lotteryCategoryEntity.getId() == sid) {
                            if (lotteryCategoryEntity.getLottery().getPeriods() == issue) {
                                refreshLotteryData(sid, issue);
                            } else {
                                cancelAllTimers();
                               /* //由于彩种都在一个接口，这里排除掉 六合彩 的彩种
                                for (int i=0;i<lotteryCategoryEntities.size();i++){
                                    if (lotteryCategoryEntities.get(i).getLottery_id()!=8){
                                        Log.w("theid2",i+"  :"+lotteryCategoryEntities.get(i).getLottery_id());
                                        lotteryCategoryEntities.remove(i);
                                       i-=1;
                                    }
                                }*/
                                BetFragment3.this.lotteryCategoryEntities = lotteryCategoryEntities;
                                baseViewHolderBaseQuickAdapter.setNewData(lotteryCategoryEntities);
                            }
                        }

                    }
                } else {
                    refreshLotteryData(sid, issue);
                }
            }
        });
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            categoryPresenter.getLotteryCategory(id);
        }).start();
    }

    @Override
    public void onRefresh() {
        presenter.getLotteryCategory(id);
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if(isVisible()){
            fetchData();
        }*/
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        //cancelAllTimers();
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
       /* if(!hidden){
            fetchData();
        }else{
            cancelAllTimers();
        }*/
    }

    @Override
    protected void fetchData() {
        presenter.getLotteryCategory(id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(BetEvent betEvent) {
        betRecyclerView.setLayoutManager(betEvent.isChecked() ? gridLayoutManager : linearLayoutManager);
        betRecyclerView.invalidate();
        isLinear = betEvent.isChecked();
        baseViewHolderBaseQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoading() {
        betRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        betRefreshLayout.setRefreshing(false);
    }

    @Override
    public void successed(List<LotteryCategoryEntity> lotteryCategoryEntities) {
        betRefreshLayout.setRefreshing(false);
        cancelAllTimers();
        this.lotteryCategoryEntities = lotteryCategoryEntities;
       /* //由于彩种都在一个接口，这里排除掉 六合彩 的彩种
        for (int i=0;i<lotteryCategoryEntities.size();i++){
            if (lotteryCategoryEntities.get(i).getLottery_id()!=8){
                lotteryCategoryEntities.remove(i);
                Log.w("theid2",i+"");
                i-=1;
            }
        }*/
        baseViewHolderBaseQuickAdapter.setNewData(lotteryCategoryEntities);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAllTimers();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
                cdt = null;
            }
        }
    }
}

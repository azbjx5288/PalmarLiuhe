package com.liuheonline.la.ui.infomation.xuanji;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.XuanJiEntity;
import com.liuheonline.la.mvp.presenter.XuanjiListPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

//玄机锦囊结合
public class XuanjiListActivity extends BaseMVPActivity<BaseView<List<XuanJiEntity>>, XuanjiListPresenter, List<XuanJiEntity>> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private AlertDialog waitDialog;

    @BindId(R.id.joker_refresh)
    private SwipeRefreshLayout jokerRefresh;

    @BindId(R.id.joker_recycler)
    private RecyclerView jokerRecycler;

    private BaseQuickAdapter<XuanJiEntity, BaseViewHolder> baseQuickAdapter;
    private int page = 1;


    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<XuanJiEntity, BaseViewHolder>(R.layout.item_joker_list) {
            @Override
            protected void convert(BaseViewHolder helper, XuanJiEntity item) {
                helper.setText(R.id.tv_joker_title, String.format("%s期", item.getPeriod() - 1));
                ImageView ivIsShow = helper.getView(R.id.iv_joker_isshow);
                ivIsShow.setBackgroundResource(R.drawable.svg_down);
                TextView tvContent = helper.getView(R.id.tv_joker_content);
                tvContent.setGravity(Gravity.CENTER);
                LinearLayout llJoker = helper.getView(R.id.ll_joker);
                tvContent.setText(item.getContent());
                if (ivIsShow.getTag() == null) {
                    ivIsShow.setTag(-1);
                    llJoker.setVisibility(View.GONE);
                } else {
                    if ((int) ivIsShow.getTag() != helper.getLayoutPosition()) {
                        llJoker.setVisibility(View.GONE);
                        ivIsShow.setBackgroundResource(R.drawable.svg_down);
                    } else {
                        llJoker.setVisibility(View.VISIBLE);
                        ivIsShow.setBackgroundResource(R.drawable.svg_up);
                    }
                }
                helper.getView(R.id.tv_joker_title).setOnClickListener(v -> {
                    if ((int) ivIsShow.getTag() != helper.getLayoutPosition()) {
                        ivIsShow.setTag(helper.getLayoutPosition());
                        ivIsShow.setBackgroundResource(R.drawable.svg_up);
                        llJoker.setVisibility(View.VISIBLE);
                    } else {
                        ivIsShow.setTag(-1);
                        ivIsShow.setBackgroundResource(R.drawable.svg_down);
                        llJoker.setVisibility(View.GONE);
                    }
                });
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_joker_list);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("玄机锦囊")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {

        //初始化adapter
        baseQuickAdapter.setOnLoadMoreListener(this, jokerRecycler);
        baseQuickAdapter.disableLoadMoreIfNotFullPage(jokerRecycler);
        //初始化recycler
        jokerRecycler.setLayoutManager(new LinearLayoutManager(this));
        jokerRecycler.setAdapter(baseQuickAdapter);
        jokerRecycler.setHasFixedSize(true);
        jokerRefresh.setOnRefreshListener(this);
        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "提交中……")
                .create();
    }

    @Override
    protected void initPresenter() {
        presenter = new XuanjiListPresenter();
    }

    @Override
    protected void fetchData() {
        jokerRefresh.setRefreshing(true);
        presenter.getInfomationDetail(2018, page, 20);
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void successed(List<XuanJiEntity> jokerEntities) {
        jokerRefresh.setRefreshing(false);
        if (page == 1) {
            if (jokerEntities != null && jokerEntities.size() > 0) {
                baseQuickAdapter.setNewData(jokerEntities);
                if (jokerEntities.size() < 20) {
                    baseQuickAdapter.loadMoreEnd();
                }
            } else {
                baseQuickAdapter.setNewData(jokerEntities); //空数据
            }
        } else {
            if (jokerEntities != null && jokerEntities.size() == 20) {
                baseQuickAdapter.loadMoreComplete();
            } else {
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(jokerEntities);
        }
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        jokerRefresh.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.getInfomationDetail(2018, page, 20);
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getInfomationDetail(2018, page, 20);
    }
}

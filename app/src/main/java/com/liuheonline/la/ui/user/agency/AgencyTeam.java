package com.liuheonline.la.ui.user.agency;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.AgentListEntity;
import com.liuheonline.la.mvp.presenter.AgentListPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

public class AgencyTeam extends BaseMVPActivity<BaseView<List<AgentListEntity>>,AgentListPresenter,List<AgentListEntity>>  implements
        SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    private int page = 1;
    @BindId(R.id.team_swipe)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.team_recycle)
    private RecyclerView recyclerView;

    private BaseQuickAdapter<AgentListEntity,BaseViewHolder> baseQuickAdapter;
    @Override
    protected void initPresenter() {
        presenter = new AgentListPresenter();
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
      //  presenter.agentList(0,page,10);
    }

    @Override
    protected void initData() {
       /* baseQuickAdapter = new BaseQuickAdapter<AgentListEntity, BaseViewHolder>(R.layout.item_agentlist) {
            @Override
            protected void convert(BaseViewHolder helper, AgentListEntity item) {
                //显示图片
                Glide.with(AgencyTeam.this)
                        .load(ImageUrlUtil.getImgUrl(item.getHead_link(),100,100))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.touxiang) //加载中图片
                                .error(R.mipmap.touxiang) //加载失败图片
                                .fallback(R.mipmap.touxiang) //url为空图片
                                .override(100,100)
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into((ImageView) helper.getView(R.id.agentlist_circle));

                helper.setText(R.id.agentlist_name,item.getNickname());
                helper.setText(R.id.agentlist_money,item.getAvailable_predeposit());
                helper.setText(R.id.agentlist_get,"我的收益："+item.getEarnings());
                if (item.getAgent()==1){
                    helper.setText(R.id.agentlist_type,"会员");
                }else {
                    helper.setText(R.id.agentlist_type,"代理");
                }
                helper.setOnClickListener(R.id.agentlist_linear, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",item.getId());
                        startActivity(MemberInfo.class,bundle);
                    }
                });

            }
        };*/
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agencyteam);
    }

    @Override
    protected void initTitle() {

        new DefaultNavigationBar.Builder(this)
                .setTitle("团队人数")
                .setLeftIconVisibility(false)
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

    @Override
    public void successed(List<AgentListEntity> agentListEntities) {

        swipeRefreshLayout.setRefreshing(false);
        if(page == 1){
            if(agentListEntities != null && agentListEntities.size() > 0){
                baseQuickAdapter.setNewData(agentListEntities);
                if(agentListEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                baseQuickAdapter.setNewData(agentListEntities);//空数据
            }
        }else{
            if(agentListEntities != null && agentListEntities.size() == 10){
                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(agentListEntities);
        }
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        swipeRefreshLayout.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void onRefresh() {
        //presenter.agentList(0,1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
       // presenter.agentList(0,page,10);
    }
}

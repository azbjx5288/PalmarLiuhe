package com.liuheonline.la.ui.user.account;

import com.liuheonline.la.ui.base.BaseMVPActivity;

public class TopupInfo extends BaseMVPActivity{

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}/*<BaseView<List<PdrechargeEntity>>,PdrechargePresenter,List<PdrechargeEntity>> implements
        SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    @BindId(R.id.account_total)
    TextView total;
    @BindId(R.id.account_out)
    TextView out;
    @BindId(R.id.account_in)
    TextView in;
    UserInfoPresenter userInfoPresenter;
    @BindId(R.id.accountinfo_nodata)
    private LinearLayout nodata;
    @BindId(R.id.accountinfo_hasdata)
    private LinearLayout hasdata;

    @BindId(R.id.accountinfo_swipe)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindId(R.id.accountinfo_recycle)
    private RecyclerView recyclerView;

    private BaseQuickAdapter<PdrechargeEntity,BaseViewHolder> baseQuickAdapter;

    private int page = 1;
    @Override
    protected void initPresenter() {
        presenter = new PdrechargePresenter();
        userInfoPresenter = new UserInfoPresenter();
        userInfoPresenter.attachView(new BaseView<UserInfo>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String ErrorActivity) {

            }

            @Override
            public void successed(UserInfo userInfo) {
                total.setText(userInfo.getAvailable_predeposit()+"元");
                out.setText(userInfo.getSpending()+"元");
                in.setText(userInfo.getIncome()+"元");
            }
        });
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.pdrecharge(page,10);
        userInfoPresenter.getUserInfo(SharedperfencesUtil.getInt(this,"userId"));
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<PdrechargeEntity, BaseViewHolder>(R.layout.item_accountinfo) {
            @Override
            protected void convert(BaseViewHolder helper, PdrechargeEntity item) {
                helper.setText(R.id.accountinfo_title, CashTypeUtil.getName(item.getPdr_type()+""));
                helper.setText(R.id.accountinfo_time, DateUtil.timeStamp2Date(item.getPdr_add_time()+"",null));
                helper.setText(R.id.accountinfo_money,item.getPdr_amount());
                helper.setOnClickListener(R.id.accountinfo_linear, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("PdrechargeEntity",item);
                        startActivity(TopupDetail.class,bundle);
                    }
                });
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_accountinfo);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("充值信息")
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
    public void onLoadFailed(int code, String ErrorActivity) {
        super.onLoadFailed(code, ErrorActivity);
        swipeRefreshLayout.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void successed(List<PdrechargeEntity> pdlogEntities) {
        swipeRefreshLayout.setRefreshing(false);
        if(page == 1){
            if(pdlogEntities != null && pdlogEntities.size() > 0){
                baseQuickAdapter.setNewData(pdlogEntities);
                if(pdlogEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                //空数据
            }
        }else{
            if(pdlogEntities != null && pdlogEntities.size() == 10){
                baseQuickAdapter.addData(pdlogEntities);
                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
        }
    }


    @Override
    public void onRefresh() {
        page = 1;
        presenter.pdrecharge(page,10);

    }

    @Override
    public void onLoadMoreRequested() {
        page ++;
        presenter.pdrecharge(page,10);
    }
}
*/
package com.liuheonline.la.ui.user.agency2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.AgencyTableEntity;
import com.liuheonline.la.mvp.presenter.AgentInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CircleImageView;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class Agency2Center extends BaseMVPActivity<BaseView<AgencyTableEntity>,AgentInfoPresenter,AgencyTableEntity>{

    @BindId(R.id.usre_name)
    TextView username;
    @BindId(R.id.user_fandian)
    TextView user_fandian;
    @BindId(R.id.user_price)
    TextView user_price;
    @BindId(R.id.userheadimg)
    CircleImageView userheadimg;
    @Override
    protected void initPresenter() {
        presenter = new AgentInfoPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.agentInfo(SharedperfencesUtil.getInt(this,"userId"),"","","");
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.agency_desc,R.id.agency_next,R.id.agency_manager,R.id.agency_price,R.id.agency_table})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.agency_desc://代理说明
                startActivity(Agency2Desc.class);
                break;
            case R.id.agency_next://下级开户
                startActivity(Agency2Next.class);
                break;
            case R.id.agency_manager://用户管理
                startActivity(Agency2Manager.class);
                break;
            case R.id.agency_price://代理佣金
                startActivity(Agency2Price.class);
                break;
            case R.id.agency_table://团队报表
                Bundle bundle = new Bundle();
                bundle.putInt("userId",SharedperfencesUtil.getInt(this,"userId"));
                startActivity(Agency2Table.class,bundle);
                break;
        }
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agency2);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("代理中心")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(AgencyTableEntity agencyTableEntity) {
        username.setText(agencyTableEntity.getUsername());
        SharedperfencesUtil.setString(this,"agencyusername",agencyTableEntity.getUsername());
        user_price.setText(agencyTableEntity.getAvailable_predeposit());
        user_fandian.setText(agencyTableEntity.getMember_rebates()==0?"1980":agencyTableEntity.getMember_rebates()+"");

        String headurl = SharedperfencesUtil.getString(getApplicationContext(),"userheadimg");
        Glide.with(this)
                .load(ImageUrlUtil.getImgUrl(headurl,100,100))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.touxiang) //加载中图片
                        .error(R.mipmap.touxiang) //加载失败图片
                        .fallback(R.mipmap.touxiang) //url为空图片
                        .centerCrop() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(userheadimg);
    }


}

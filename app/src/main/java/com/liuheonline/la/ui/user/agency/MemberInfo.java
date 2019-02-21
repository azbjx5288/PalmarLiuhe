package com.liuheonline.la.ui.user.agency;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.MemberInfoEntity;
import com.liuheonline.la.mvp.presenter.GetMemberInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CircleImageView;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

//会员报表
public class MemberInfo  extends BaseMVPActivity<BaseView<MemberInfoEntity>,GetMemberInfoPresenter,MemberInfoEntity>{
    @BindId(R.id.memberinfo_circle)
    CircleImageView circleImageView;
    @BindId(R.id.memberinfo_name)
    TextView name;
    @BindId(R.id.memberinfo_money)
    TextView money;
    @BindId(R.id.memberinfo_get)
    TextView get;

    @BindId(R.id.zhlx)
    TextView zhlx;
    @BindId(R.id.zcsj)
    TextView zcsj;
    @BindId(R.id.zhye)
    TextView zhye;
    @BindId(R.id.zhdlsj)
    TextView zhdlsj;
    @BindId(R.id.zxzje)
    TextView zxzje;
    @BindId(R.id.xzbs)
    TextView xzbs;
    @BindId(R.id.zjje)
    TextView zjje;
    @BindId(R.id.pjje)
    TextView pjje;
    @BindId(R.id.zyk)
    TextView zyk;
    @BindId(R.id.hdzj)
    TextView hdzj;


    @Override
    protected void initPresenter() {
        presenter = new GetMemberInfoPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getMemberInfo(getIntent().getExtras().getInt("id"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_memberinfo);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("会员报表")
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
    public void successed(MemberInfoEntity memberInfoEntity) {
        //显示图片
        Glide.with(MemberInfo.this)
                .load(ImageUrlUtil.getImgUrl(memberInfoEntity.getHead_link(),100,100))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.touxiang) //加载中图片
                        .error(R.mipmap.touxiang) //加载失败图片
                        .fallback(R.mipmap.touxiang) //url为空图片
                        .override(100,100)
                        .centerCrop() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(circleImageView);
        name.setText(memberInfoEntity.getNickname()+"");
        money.setText(memberInfoEntity.getAvailable_predeposit()+"");

        if (memberInfoEntity.getAgent()==1){
            zhlx.setText("会员");
        }else {
            zhlx.setText("代理");
        }
        zcsj.setText(memberInfoEntity.getRegdate()+"");
        zhye.setText(memberInfoEntity.getAvailable_predeposit()+"");
        zhdlsj.setText(memberInfoEntity.getLastdate()+"");
        zxzje.setText(memberInfoEntity.getTeam_total()+"");
        xzbs.setText(memberInfoEntity.getTeam_orders()+"");
        zjje.setText(memberInfoEntity.getTeam_winning()+"");
        pjje.setText(memberInfoEntity.getTeam_led()+"");
        zyk.setText(memberInfoEntity.getTeam_orders()-memberInfoEntity.getTeam_total()+"");
        hdzj.setText(memberInfoEntity.getLottery_rebates_price()+"");
        get.setText("我的收益："+memberInfoEntity.getEarnings());

    }


}

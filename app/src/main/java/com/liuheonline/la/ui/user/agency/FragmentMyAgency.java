package com.liuheonline.la.ui.user.agency;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.AgentInfoPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

//我的代理
public class FragmentMyAgency extends BaseMvpFragment {


    @BindId(R.id.tdrs)
    TextView tdrs;
    @BindId(R.id.zxrs)
    TextView zxrs;
    @BindId(R.id.tdje)
    TextView tdje;
    @BindId(R.id.xyhzc)
    TextView xyhzc;
    @BindId(R.id.sjyk)
    TextView sjyk;
    @BindId(R.id.tze)
    TextView tze;
    @BindId(R.id.fd)
    TextView fd;
    @BindId(R.id.hdpf)
    TextView hdpf;
    @BindId(R.id.tdzs)
    TextView tdzs;
    @BindId(R.id.zjje)
    TextView zjje;
    @BindId(R.id.pjje)
    TextView pjje;
    @BindId(R.id.ykje)
    TextView ykje;
    @BindId(R.id.czje)
    TextView czje;
    @BindId(R.id.txje)
    TextView txje;
    @BindId(R.id.zsy)
    TextView zsy;

    double fandian = 0;

    @OnClick(R.id.linear_team)
    private void onClick(View view){
        Bundle bundle = new Bundle();
        bundle.putDouble("fandian",fandian);
        startActivity(AgencyTeam.class,bundle);
    }


    @Override
    protected void initPresenter() {
        presenter = new AgentInfoPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_myagency;
    }

    @Override
    protected void fetchData() {
       // presenter.agentInfo(SharedperfencesUtil.getInt(getContext(),"userId"));

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
/*
    @Override
    public void successed(TeamAgent teamAgent) {
        if (teamAgent!=null){
            zsy.setText(teamAgent.getAvailable_predeposit()+"");
            tdrs.setText(teamAgent.getTeam()+"");
            zxrs.setText(teamAgent.getTeam_online()+"");
            tdje.setText(teamAgent.getAvailable_predeposit()+"");
            xyhzc.setText(teamAgent.getTeam_new()+"");
            fd.setText(teamAgent.getZc_moshi()+"");
            fandian = teamAgent.getZc_moshi();
            tdzs.setText(teamAgent.getZc_moshi()*teamAgent.getTeam_winning()+"");
            zjje.setText(teamAgent.getTeam_winning()+"");
            czje.setText(teamAgent.getTeam_topup()+"");
            txje.setText(teamAgent.getTeam_withdrawal()+"");
            hdpf.setText(teamAgent.getTeam_led()+"");
            sjyk.setText(new DecimalFormat("#.00").format(teamAgent.getTeam_rebates_profit())+"");
            tze.setText(teamAgent.getTeam_betting_price()+"");
            pjje.setText(teamAgent.getTeam_rebates_price()+"");
            ykje.setText(new DecimalFormat("#.00").format(teamAgent.getTeam_rebates_profit())+"");

        }

    }*/

}

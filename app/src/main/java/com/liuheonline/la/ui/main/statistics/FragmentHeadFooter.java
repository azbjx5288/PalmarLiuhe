package com.liuheonline.la.ui.main.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.TailStatisticsEntity;
import com.liuheonline.la.event.PeriodsEvent;
import com.liuheonline.la.mvp.presenter.TailStatisticsPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentHeadFooter extends BaseMvpFragment<BaseView<TailStatisticsEntity>,TailStatisticsPresenter,TailStatisticsEntity> {
    @BindId(R.id.num_linear_t)
    LinearLayout linearLayout_t;
    @BindId(R.id.num1_ball_t)
    TextView num1_ball_t;
    @BindId(R.id.num2_ball_t)
    TextView num2_ball_t;
    @BindId(R.id.num3_ball_t)
    TextView num3_ball_t;
    @BindId(R.id.num4_ball_t)
    TextView num4_ball_t;
    @BindId(R.id.num5_ball_t)
    TextView num5_ball_t;
    @BindId(R.id.num1_ball_z)
    TextView num1_ball_z;
    @BindId(R.id.num2_ball_z)
    TextView num2_ball_z;
    @BindId(R.id.num3_ball_z)
    TextView num3_ball_z;
    @BindId(R.id.num4_ball_z)
    TextView num4_ball_z;
    @BindId(R.id.num5_ball_z)
    TextView num5_ball_z;
    @BindId(R.id.num_linear_z)
    LinearLayout linearLayout_z;

    @BindId(R.id.num1_index_t)
    TextView num1_index_t;
    @BindId(R.id.num2_index_t)
    TextView num2_index_t;
    @BindId(R.id.num3_index_t)
    TextView num3_index_t;
    @BindId(R.id.num4_index_t)
    TextView num4_index_t;
    @BindId(R.id.num5_index_t)
    TextView num5_index_t;
    @BindId(R.id.num1_index_z)
    TextView num1_index_z;
    @BindId(R.id.num2_index_z)
    TextView num2_index_z;
    @BindId(R.id.num3_index_z)
    TextView num3_index_z;
    @BindId(R.id.num4_index_z)
    TextView num4_index_z;
    @BindId(R.id.num5_index_z)
    TextView num5_index_z;

    @OnClick({R.id.num_linear_t,R.id.num_linear_z})
    private void onClick(View view){
        Bundle bundle = getActivity().getIntent().getExtras();
        switch (view.getId()){
            case R.id.num_linear_t:
                startActivity(TailStatisticsInfo.class,bundle);
                break;
            case R.id.num_linear_z:
               /* startActivity(NumStatisticsInfo2.class);*/
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new TailStatisticsPresenter();

    }

    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(PeriodsEvent periodsEvent) {
        presenter.getTailStatistics(periodsEvent.getPeriods(),1,getActivity().getIntent().getExtras().getInt("lotteryid")==0?7+"":getActivity().getIntent().getExtras().getInt("lotteryid")+"");
    }
    @Override
    protected void initViews() {

    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tail;
    }

    @Override
    protected void fetchData() {
        presenter.getTailStatistics(30,1,getActivity().getIntent().getExtras().getInt("lotteryid")==0?7+"":getActivity().getIntent().getExtras().getInt("lotteryid")+"");
    }

    @Override
    public void onLoading() {

    }



    @Override
    public void successed(TailStatisticsEntity tailStatisticsEntity) {
        num1_ball_t.setText(tailStatisticsEntity.getSingle().get(0).getSingle()+"");
        num2_ball_t.setText(tailStatisticsEntity.getSingle().get(1).getSingle()+"");
        num3_ball_t.setText(tailStatisticsEntity.getSingle().get(2).getSingle()+"");
        num4_ball_t.setText(tailStatisticsEntity.getSingle().get(3).getSingle()+"");
        num5_ball_t.setText(tailStatisticsEntity.getSingle().get(4).getSingle()+"");
        /*num1_ball_t.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getTema().get(0).getTema_color_id()));
        num2_ball_t.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getTema().get(1).getTema_color_id()));
        num3_ball_t.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getTema().get(2).getTema_color_id()));
        num4_ball_t.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getTema().get(3).getTema_color_id()));
        num5_ball_t.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getTema().get(4).getTema_color_id()));*/
        num1_index_t.setText(tailStatisticsEntity.getSingle().get(0).getSingle_num()+"");
        num2_index_t.setText(tailStatisticsEntity.getSingle().get(1).getSingle_num()+"");
        num3_index_t.setText(tailStatisticsEntity.getSingle().get(2).getSingle_num()+"");
        num4_index_t.setText(tailStatisticsEntity.getSingle().get(3).getSingle_num()+"");
        num5_index_t.setText(tailStatisticsEntity.getSingle().get(4).getSingle_num()+"");

        num1_ball_z.setText(tailStatisticsEntity.getDoubleX().get(0).getDoubleX()+"");
        num2_ball_z.setText(tailStatisticsEntity.getDoubleX().get(1).getDoubleX()+"");
        num3_ball_z.setText(tailStatisticsEntity.getDoubleX().get(2).getDoubleX()+"");
        num4_ball_z.setText(tailStatisticsEntity.getDoubleX().get(3).getDoubleX()+"");
        num5_ball_z.setText(tailStatisticsEntity.getDoubleX().get(4).getDoubleX()+"");
        /*num1_ball_z.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getCode().get(0).getCode_color_id()));
        num2_ball_z.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getCode().get(1).getCode_color_id()));
        num3_ball_z.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getCode().get(2).getCode_color_id()));
        num4_ball_z.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getCode().get(3).getCode_color_id()));
        num5_ball_z.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(numStatisticsEntity.getCode().get(4).getCode_color_id()));*/
        num1_index_z.setText(tailStatisticsEntity.getDoubleX().get(0).getDouble_num()+"");
        num2_index_z.setText(tailStatisticsEntity.getDoubleX().get(1).getDouble_num()+"");
        num3_index_z.setText(tailStatisticsEntity.getDoubleX().get(2).getDouble_num()+"");
        num4_index_z.setText(tailStatisticsEntity.getDoubleX().get(3).getDouble_num()+"");
        num5_index_z.setText(tailStatisticsEntity.getDoubleX().get(4).getDouble_num()+"");
    }


    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().unregister(this);
    }
}

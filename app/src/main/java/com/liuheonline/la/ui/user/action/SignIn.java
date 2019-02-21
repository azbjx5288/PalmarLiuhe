package com.liuheonline.la.ui.user.action;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.SignDataEntity;
import com.liuheonline.la.entity.SignEntity;
import com.liuheonline.la.entity.SignRewardEntity;
import com.liuheonline.la.mvp.presenter.SignInPresenter;
import com.liuheonline.la.mvp.presenter.SignPresenter;
import com.liuheonline.la.mvp.presenter.SignRewardPresenter;
import com.liuheonline.la.mvp.presenter.SignStatusPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.DateUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

/**
 * @author BenYanYi
 * @date 2018/12/13 16:00
 * @email ben@yanyi.red
 * @overview 签到
 */
public class SignIn extends BaseMVPActivity<BaseView<List<SignDataEntity>>, SignInPresenter, List<SignDataEntity>> {

    @BindId(R.id.recycler)
    RecyclerView recyclerView;
    @BindId(R.id.sign_recycler1)
    RecyclerView signRecycler;
    @BindId(R.id.sign)
    TextView tvSign;

    private BaseQuickAdapter<SignDataEntity, BaseViewHolder> baseQuickAdapter;
    private BaseQuickAdapter<SignRewardEntity, BaseViewHolder> rewardAdapter;
    private List<SignDataEntity> oList;

    private SignRewardPresenter rewardPresenter;
    private SignStatusPresenter signStatusPresenter;
    private SignPresenter signPresenter;
    private boolean signBoo = false;

    @Override
    protected void initPresenter() {
        presenter = new SignInPresenter();
        presenter.attachView(this);
        rewardPresenter = new SignRewardPresenter();
        rewardPresenter.attachView(new BaseView<List<SignRewardEntity>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(List<SignRewardEntity> signRewardEntities) {
                rewardAdapter.setNewData(signRewardEntities);
            }
        });
        signStatusPresenter = new SignStatusPresenter();
        signStatusPresenter.attachView(new BaseView<Integer>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(Integer s) {
                JLog.d(s);
                if (s == 2) {
                    signBoo = false;
                    tvSign.setText("签到");
                } else {
                    signBoo = true;
                    tvSign.setText("已签到");
                }
//                Toast.makeText(SignIn.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        signStatusPresenter.signStatus();
        signPresenter = new SignPresenter();
        signPresenter.attachView(new BaseView<SignEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(SignIn.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(SignEntity signEntity) {
                if (signEntity != null) {
                    Toast.makeText(SignIn.this, "签到成功", Toast.LENGTH_SHORT).show();
                }
                fetchData();
            }
        });
    }

    @Override
    protected void fetchData() {
        presenter.getData();
        rewardPresenter.getReward();
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<SignDataEntity, BaseViewHolder>(R.layout.item_sign_data) {
            @Override
            protected void convert(BaseViewHolder helper, SignDataEntity item) {
                helper.setText(R.id.time, DateUtil.timeStamp2Date(item.getAtime() + "", ""));
                String message = "连续签到" + item.getDays() + "天" + "\n" + "签到奖励+" + item.getScore() + "元";
                helper.setText(R.id.message, message);
                JLog.v();
            }
        };
        rewardAdapter = new BaseQuickAdapter<SignRewardEntity, BaseViewHolder>(R.layout.item_sign_reward) {
            @Override
            protected void convert(BaseViewHolder helper, SignRewardEntity item) {
                helper.setText(R.id.days, item.getDays() + "天");
                helper.setText(R.id.reward, item.getTotal());
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_sign);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("签到")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        signRecycler.setLayoutManager(manager);
        signRecycler.setAdapter(rewardAdapter);
        signRecycler.setHasFixedSize(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<SignDataEntity> signDataEntities) {
        JLog.v(signDataEntities);
        oList = signDataEntities;
        baseQuickAdapter.setNewData(signDataEntities);
    }

    @OnClick({R.id.sign})
    private void onClick(View view) {
        if (!signBoo) {
            signPresenter.sign();
        }
    }
}

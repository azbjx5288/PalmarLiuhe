package com.liuheonline.la.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.liuheonline.la.entity.BankCardEntity;
import com.liuheonline.la.entity.SignEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.GetCardPresenter;
import com.liuheonline.la.mvp.presenter.SignPresenter;
import com.liuheonline.la.mvp.presenter.SignStatusPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.main.web.ServiceChat;
import com.liuheonline.la.ui.user.FeedbackActivity;
import com.liuheonline.la.ui.user.Individuality;
import com.liuheonline.la.ui.user.UserActiveActivity;
import com.liuheonline.la.ui.user.account.AccountInfo;
import com.liuheonline.la.ui.user.account.MyAccountCard;
import com.liuheonline.la.ui.user.agency2.Agency2Center;
import com.liuheonline.la.ui.user.agency2.Agency2Manager;
import com.liuheonline.la.ui.user.agency2.Agency2Price;
import com.liuheonline.la.ui.user.agency2.Agency2Table;
import com.liuheonline.la.ui.user.forummanager.ForumManagerActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.ui.user.lotterylog.LotteryLogActivity;
import com.liuheonline.la.ui.user.register.RegisterActivity;
import com.liuheonline.la.ui.user.setting.UserSettingActivity;
import com.liuheonline.la.ui.user.share.ShareActivity;
import com.liuheonline.la.ui.user.topup.TopUp;
import com.liuheonline.la.ui.user.withdraw.Withdraw;
import com.liuheonline.la.ui.widget.CircleImageView;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import java.util.List;

public class UserFragment extends BaseMvpFragment<BaseView<UserInfo>, UserInfoPresenter, UserInfo> implements SwipeRefreshLayout.OnRefreshListener {

    private UserInfo userInfo;

    @BindId(R.id.user_name)
    private TextView userNameText;


    @BindId(R.id.user_icon)
    private CircleImageView userIconImg;

    @BindId(R.id.myprice)
    LinearLayout myprice;
    @BindId(R.id.login_register)
    LinearLayout login_register;
    @BindId(R.id.user_refresh)
    private SwipeRefreshLayout userRefreshView;

    @BindId(R.id.show_linear)
    LinearLayout show;
    @BindId(R.id.money)
    TextView money;
    String price = "";
    //用户ID
    private int userId;
    @BindId(R.id.checkprice)
    CheckBox checkprice;

    @BindId(R.id.user_nikename)
    TextView nikename;
    @BindId(R.id.isdaili)
    LinearLayout daililinear;
    @BindId(R.id.sign)
    TextView tvSign;

    GetCardPresenter getCardPresenter;
    SignPresenter signPresenter;
    SignStatusPresenter signStatusPresenter;

    @Override
    protected void initPresenter() {
        presenter = new UserInfoPresenter();

        getCardPresenter = new GetCardPresenter();
        getCardPresenter.attachView(new BaseView<List<BankCardEntity>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(List<BankCardEntity> bankCardEntities) {
                if (bankCardEntities != null && bankCardEntities.size() > 0) {
                    Log.w("thehasCard", SharedperfencesUtil.getBoolean(getContext(), "hasCard") + " userfragment 88");
                    SharedperfencesUtil.setBoolean(getContext(), "hasCard", true);
                } else {
                    Log.w("thehasCard", SharedperfencesUtil.getBoolean(getContext(), "hasCard") + " userfragment 91");
                    SharedperfencesUtil.setBoolean(getContext(), "hasCard", false);
                }
            }
        });
        getCardPresenter.getCardUserLoad(1, 1);
        signPresenter = new SignPresenter();
        signPresenter.attachView(new BaseView<SignEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(getContext(), "您已签到过，明天再来哦！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(SignEntity signEntity) {
                if (signEntity != null) {
                    Toast.makeText(getContext(), "连续签到" + signEntity.getDays() + "天,奖励" + signEntity.getScore(), Toast.LENGTH_SHORT).show();
                }
                presenter.getUserInfo(userId);
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
            public void successed(Integer integer) {
                JLog.v(integer);
                if (integer == 2) {
                    tvSign.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                    tvSign.setBackgroundResource(R.drawable.radius_solid_red2);
                } else {
                    tvSign.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    tvSign.setBackgroundResource(R.drawable.radis_solid_white5);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        userRefreshView.setOnRefreshListener(this);
    }

    @Override
    protected void initTitle(View view) {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user3;
    }

    @OnClick({R.id.setting, R.id.message, R.id.user_feedback, R.id.forum_linear, R.id.user_myaccount,
            R.id.user_individuality, R.id.user_agency, R.id.add_money, R.id.get_money, R.id.log_linear, R.id.active_linear, R.id.tologin, R.id.toregister, R.id.user_share,
            R.id.checkprice, R.id.user_manager, R.id.user_table, R.id.user_price, R.id.refresh, R.id.clear_data, R.id.sign})
    private void onClick(View view) {
        switch (view.getId()) {

            case R.id.user_share:
                if (SharedperfencesUtil.getInt(getContext(), "userId") != 0) {
                    startActivity(ShareActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.clear_data:
                Toast.makeText(getContext(), "清理成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:
                if (userId != 0) {
                    presenter.getUserInfo(userId);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_manager://用户管理
                if (userId != 0) {
                    startActivity(Agency2Manager.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_price://代理佣金
                if (userId != 0) {
                    startActivity(Agency2Price.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_table://团队报表
                if (userId != 0) {
                    Bundle bundletable = new Bundle();
                    bundletable.putInt("userId", SharedperfencesUtil.getInt(getContext(), "userId"));
                    startActivity(Agency2Table.class, bundletable);
                } else {
                    startActivity(LoginActivity.class);
                }

                break;
            case R.id.checkprice:
                if (checkprice.isChecked()) {
                    money.setText(price);
                } else {
                    money.setText("******");
                }
                break;
           /* case R.id.user_share:
                if (SharedperfencesUtil.getInt(getContext(),"userId")!=0){
                    startActivity(ShareActivity.class);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
                break;*/
            case R.id.tologin:
                startActivity(LoginActivity.class);
                break;
            case R.id.toregister:
                startActivity(RegisterActivity.class);
                break;
            case R.id.message:
                startActivity(ServiceChat.class);
                break;
            case R.id.setting:
                if (userId != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userInfo", userInfo);
                    startActivity(UserSettingActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_feedback:
                if (userId != 0) {
                    startActivity(FeedbackActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.forum_linear:
                if (userId != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userInfo", userInfo);
                    startActivity(ForumManagerActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.log_linear:
                if (userId != 0) {
                    startActivity(LotteryLogActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_myaccount:
                if (userId != 0) {
                    //startActivity(MyAccount.class);
                    startActivity(AccountInfo.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_individuality:
                startActivity(Individuality.class);
                break;
            case R.id.user_agency:
                if (userId != 0) {
                    /*if(userInfo.getAgent() > 1){
                       // startActivity(AgencyCenter.class);
                        startActivity(Agency2Center.class);
                    }else{
                        Toast.makeText(LiuHeApplication.context,"当前账户权限不足",Toast.LENGTH_SHORT).show();
                    }*/
                    startActivity(Agency2Center.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.add_money://充值
                if (userId != 0) {
//                    startActivity(ChoosePay.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "saoma");
                    startActivity(TopUp.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.get_money:
                if (userId != 0) {
                    Log.w("thehasCard", SharedperfencesUtil.getBoolean(getContext(), "hasCard") + " userfragment 198");
                    if (SharedperfencesUtil.getBoolean(getContext(), "hasCard")) {
                        startActivity(Withdraw.class);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "请先绑定银行卡", Toast.LENGTH_SHORT).show();
                        startActivity(MyAccountCard.class);
                    }
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.active_linear://活动中心
                if (userId != 0) {
                    startActivity(UserActiveActivity.class);
//                    startActivity(ActionCenter.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.sign:
                if (userId != 0) {
//                    startActivity(UserActiveActivity.class);
//                    startActivity(SignIn.class);
                    signPresenter.sign();
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCardPresenter.getCard(1, 1);
        userId = SharedperfencesUtil.getInt(getContext(), "userId");
        if (userId != 0) {
            presenter.getUserInfo(userId);
            login_register.setVisibility(View.GONE);
            myprice.setVisibility(View.VISIBLE);
            tvSign.setVisibility(View.VISIBLE);
            signStatusPresenter.signStatus();
        } else {
            login_register.setVisibility(View.VISIBLE);
            show.setVisibility(View.GONE);
            tvSign.setVisibility(View.GONE);
            userNameText.setText("登录");
            userIconImg.setImageResource(R.mipmap.touxiang);
            money.setText("");
            myprice.setVisibility(View.GONE);
        }

        if (SharedperfencesUtil.getInt(getContext(), "agent") == 1) {//1为成员
            Log.w("thevisib", SharedperfencesUtil.getInt(getContext(), "agent") + "");
            daililinear.setVisibility(View.GONE);
        } else {
            daililinear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            getCardPresenter.getCard(1, 1);
            userId = SharedperfencesUtil.getInt(getContext(), "userId");
            if (userId != 0) {
                login_register.setVisibility(View.GONE);
                presenter.getUserInfo(userId);
                signStatusPresenter.signStatus();
            } else {
                show.setVisibility(View.GONE);
                tvSign.setVisibility(View.GONE);
                userNameText.setText("登录");
                userIconImg.setImageResource(R.mipmap.touxiang);
            }
        }
    }


    @Override
    protected void fetchData() {

    }

    @Override
    public void onRefresh() {
        userRefreshView.setRefreshing(true);
        userId = SharedperfencesUtil.getInt(getContext(), "userId");
        if (userId != 0) {
            presenter.getUserInfo(userId);
            signStatusPresenter.signStatus();
        } else {
            show.setVisibility(View.GONE);
            tvSign.setVisibility(View.GONE);
            userNameText.setText("登录");
            userIconImg.setImageResource(R.mipmap.touxiang);
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        userRefreshView.setRefreshing(false);
    }

    @Override
    public void successed(UserInfo userInfo) {
        userRefreshView.setRefreshing(false);
        this.userInfo = userInfo;
        userNameText.setText("用户名：" + userInfo.getUsername());
        nikename.setText("昵     称：" + userInfo.getNickname());
        show.setVisibility(View.VISIBLE);
        tvSign.setVisibility(View.VISIBLE);
        signStatusPresenter.signStatus();
        money.setText(userInfo.getAvailable_predeposit());
        price = userInfo.getAvailable_predeposit();
        SharedperfencesUtil.setString(getContext(), "userheadimg", userInfo.getHead_link());
        Glide.with(this)
                .load(ImageUrlUtil.getImgUrl(userInfo.getHead_link(), 100, 100))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.touxiang) //加载中图片
                        .error(R.mipmap.touxiang) //加载失败图片
                        .fallback(R.mipmap.touxiang) //url为空图片
                        .centerCrop() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(userIconImg);
    }
}

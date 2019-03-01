package com.liuheonline.la.ui.main.fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.liuheonline.la.entity.BankCardEntity;
import com.liuheonline.la.entity.ImageEntity;
import com.liuheonline.la.entity.LotteryCategoryEntity;
import com.liuheonline.la.entity.SignEntity;
import com.liuheonline.la.entity.UpdateEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.GetCardPresenter;
import com.liuheonline.la.mvp.presenter.LoginPresenter;
import com.liuheonline.la.mvp.presenter.PostFilePresenter;
import com.liuheonline.la.mvp.presenter.SignPresenter;
import com.liuheonline.la.mvp.presenter.SignStatusPresenter;
import com.liuheonline.la.mvp.presenter.UpUserInfoPresenter;
import com.liuheonline.la.mvp.presenter.UpdatePresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.forum.holder.GlideImageLoader;
import com.liuheonline.la.ui.main.web.ServiceChat;
import com.liuheonline.la.ui.user.FeedbackActivity;
import com.liuheonline.la.ui.user.Individuality;
import com.liuheonline.la.ui.user.UserActiveActivity;
import com.liuheonline.la.ui.user.UserActiveActivity2;
import com.liuheonline.la.ui.user.VersionActivity;
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
import com.liuheonline.la.utils.StringUtil;
import com.liuheonline.la.utils.UpdateMannger;
import com.mylove.loglib.JLog;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.CheckNet;
import com.yxt.itv.library.ioc.OnClick;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class UserFragment2 extends BaseMvpFragment<BaseView<UserInfo>, UserInfoPresenter, UserInfo> implements SwipeRefreshLayout.OnRefreshListener {

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
    @BindId(R.id.show_sign)
    LinearLayout showSign;
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
    @BindId(R.id.user_manager)
    LinearLayout managerLinear;
    @BindId(R.id.sign)
    TextView tvSign;
    @BindId(R.id.login_relative)
    RelativeLayout loginRelative;

    @BindId(R.id.input_account)
    private EditText inputAccountText;

    @BindId(R.id.input_password)
    private EditText inputPasswordText;

    @BindId(R.id.is_show_pw)
    private CheckBox isShowPwCheck;

    @BindId(R.id.find_password)
    private TextView findPW;

    @BindId(R.id.user_id)
    TextView userID;

    private AlertDialog wiatDialog;

    UpdateMannger updateMannger;
    private AlertDialog updateDialog;

    //图片选择器
    private GalleryConfig galleryConfig;

    //图片选择器回调
    private IHandlerCallBack iHandlerCallBack;

//    private AlertDialog waitDialog;
    private com.yxt.itv.library.dialog.DialogLoadding waitDialog;

    private String userImgPath = "";

    GetCardPresenter getCardPresenter;
    SignPresenter signPresenter;
    SignStatusPresenter signStatusPresenter;
    UpdatePresenter updatePresenter;
    LoginPresenter loginPresenter;
    PostFilePresenter postFilePresenter;
    UpUserInfoPresenter upUserInfoPresenter;

    private boolean hasCheakced=false;

    @Override
    protected void initPresenter() {
        if(hasCheakced) {
            return;
        }else{
            hasCheakced = true;
        }
        presenter = new UserInfoPresenter();
        getCardPresenter = new GetCardPresenter();
        getCardPresenter.attachView(new BaseView<List<BankCardEntity>>() {
            @Override
            public void onLoading() {
                waitDialog.showDialog();
            }

            @Override
            public void onLoadFailed(int code, String error) {
                waitDialog.closeDialog();
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
                waitDialog.closeDialog();
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
                    tvSign.setText("签到");
                } else {
                    tvSign.setText("已签到");
                }
            }
        });
        updatePresenter = new UpdatePresenter();
        updatePresenter.attachView(new BaseView<List<UpdateEntity>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(List<UpdateEntity> updateEntities) {
                if (updateEntities != null && updateEntities.size() > 0) {
                    if (updateDialog.isShowing()) {
                        return;
                    }
                    updateDialog.setText(R.id.message_text, updateEntities.get(0).getRemark());
                    if (updateEntities.get(0).getIs_updata() == 1) {
                        updateDialog.getView(R.id.cancel).setVisibility(View.GONE);
                    }
                    updateDialog.setOnclickListener(R.id.cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateDialog.dismiss();
                        }
                    });
                    updateDialog.setOnclickListener(R.id.sure, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateMannger = new UpdateMannger(getContext());
                            String url = updateEntities.get(0).getUrl();//外链优先
                            if (!url.equals("")) {
                                updateMannger.showUpdateProgress(url);
                            } else {
                                updateMannger.showUpdateProgress(updateEntities.get(0).getFile_url_link());
                            }
                            updateDialog.dismiss();
                        }
                    });
                    updateDialog.show();
                } else {
                    Toast.makeText(getContext(), "当前已经是最新版本了！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(new BaseView<UserInfo>() {
            @Override
            public void onLoading() {
                wiatDialog.show();
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context, error, Toast.LENGTH_SHORT).show();
                wiatDialog.cancel();
            }

            @Override
            public void successed(UserInfo userInfo) {
                wiatDialog.cancel();
                JLog.v("userId", userInfo.getId());
                //插入到本地数据库
                SharedperfencesUtil.setInt(getContext(), "userId", userInfo.getId());
                SharedperfencesUtil.setInt(getContext(), "agent", userInfo.getAgent());
                SharedperfencesUtil.setString(getContext(), "phoneNumber", userInfo.getMobile());
                SharedperfencesUtil.setString(getContext(), "agencyusername", userInfo.getUsername());
                SharedperfencesUtil.setString(getContext(), "zc_moshi", userInfo.getNodd_rebates() + "");
                Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                JLog.v("userId");
                setUI();
            }
        });
        postFilePresenter = new PostFilePresenter();
        postFilePresenter.attachView(new BaseView<List<ImageEntity>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                waitDialog.closeDialog();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(List<ImageEntity> imageEntities) {
                userImgPath = imageEntities.get(0).getLink();
                Map<String, Object> map = new HashMap<>();
                map.put("head", imageEntities.get(0).getPath());
                upUserInfoPresenter.upUserInfo(userInfo.getId(), map);
            }
        });
        upUserInfoPresenter = new UpUserInfoPresenter();
        upUserInfoPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                waitDialog.closeDialog();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(Object s) {
                waitDialog.closeDialog();
                //显示图片
                Glide.with(getActivity())
                        .load(ImageUrlUtil.getImgUrl(userImgPath, 100, 100))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.touxiang) //加载中图片
                                .error(R.mipmap.touxiang) //加载失败图片
                                .fallback(R.mipmap.touxiang) //url为空图片
                                .override(100, 100)
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(userIconImg);
                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        initGallery();
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider(getContext().getPackageName())   // provider(必填)
                .crop(true, 1, 1, 1000, 1000)
                .isShowCamera(true)
                .multiSelect(false)
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
    }

    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<String> photoList) {
                waitDialog.showDialog();
                Flowable.just(photoList)
                        .observeOn(Schedulers.io())
                        .map(list -> {
                            // 同步方法直接返回压缩后的文件
                            return Luban.with(getContext()).load(list).get();
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(files -> {
                            if (files != null && files.size() > 0) {
                                //本地文件
                                File imgFile = files.get(0);
                                //上传图片
                                postFilePresenter.postFile("", imgFile);
                            } else {
                                waitDialog.closeDialog();
                            }
                        });
            }

            @Override
            public void onCancel() {
                Log.i("AddForumActivity", "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i("AddForumActivity", "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i("AddForumActivity", "onError: 出错");
            }
        };

    }

    @Override
    protected void initViews() {
        userRefreshView.setOnRefreshListener(this);
        updateDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_up)
                .setCancelable(false)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        wiatDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_wait)
                .create();
       /* waitDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "提交中……")
                .create();*/
        waitDialog = new com.yxt.itv.library.dialog.DialogLoadding(getContext());
    }

    @Override
    protected void initTitle(View view) {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user4;
    }

    @OnClick({R.id.setting, R.id.message, R.id.user_feedback, R.id.forum_linear, R.id.user_myaccount,
            R.id.user_individuality, R.id.user_agency, R.id.add_money, R.id.get_money, R.id.log_linear,
            R.id.active_linear, R.id.tologin, R.id.toregister, R.id.user_share, R.id.checkprice,
            R.id.user_manager, R.id.user_table, R.id.user_price, R.id.refresh, R.id.clear_data,
            R.id.sign, R.id.user_update, R.id.user_version, R.id.img_clear, R.id.is_show_pw,
            R.id.find_password, R.id.but_register, R.id.show_sign, R.id.user_icon})
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
//                startActivity(ServiceChat.class);

                WebPresenter   webPresenter= new WebPresenter();
                webPresenter.attachView(new BaseView<WebEntity>() {
                    @Override
                    public void onLoading() {
                        Log.d("开奖", "获取最新开奖~~");
                    }

                    @Override
                    public void onLoadFailed(int code, String error) {
                        Log.d("开奖", "获取最新开奖失败~~");
                    }

                    @Override
                    public void successed(WebEntity webEntity) {
                       String strurl = StringUtil.translation(webEntity.getService_url());

                        Uri CONTENT_URI_BROWSERS = Uri.parse(strurl);


                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(CONTENT_URI_BROWSERS);//Url 就是你要打开的网址x
                        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
                        // 官方解释 : Name of the component implementing an activity that can display the intent
                        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                            final ComponentName componentName = intent.resolveActivity(getContext().getPackageManager());
                            // 打印Log   ComponentName到底是什么
                            Log.e("ServiceChat", "componentName = " + componentName.getClassName());
                            startActivity(Intent.createChooser(intent, "请选择浏览器"));
                        } else {
                            Toast.makeText(getContext().getApplicationContext(), "没有匹配的程序", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                webPresenter.getWeb();
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
                    startActivity(UserActiveActivity2.class);
//                    startActivity(ActionCenter.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.show_sign:
            case R.id.sign:
                if (userId != 0) {
//                    startActivity(UserActiveActivity.class);
//                    startActivity(SignIn.class);
                    signPresenter.sign();
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.user_update:
                updatePresenter.getUpdate(getVersionName(getContext()), 1);
                break;
            case R.id.user_version:
                startActivity(VersionActivity.class);
                break;
            case R.id.img_clear:
                inputPasswordText.setText("");
                break;
            case R.id.is_show_pw:
                if (isShowPwCheck.isChecked()) {
                    inputPasswordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    inputPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.find_password:
                // startActivity(AuthActivity.class);
                Toast.makeText(getContext(), "找回密码请联系在线客服", Toast.LENGTH_SHORT).show();
                break;
            case R.id.but_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.user_icon:
                if (userId != 0) {
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
        }
    }

    @CheckNet(errorMsg = "网络异常")
    @OnClick({R.id.but_login})
    private void login(View view) {
        if (inputAccountText.getText().toString().equals("")) {
            Toast.makeText(getContext().getApplicationContext(), "账号不能为空哦~", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputPasswordText.getText().toString().equals("")) {
            Toast.makeText(getContext().getApplicationContext(), "密码不能为空哦~", Toast.LENGTH_SHORT).show();
            return;
        } else {
            loginPresenter.login(inputAccountText.getText().toString(), inputPasswordText.getText().toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUI();

        setUI();

        setUI() ;
        setUI() ;
        setUI() ;
    }

    private void setUI() {
        getCardPresenter.getCard(1, 1);
        userId = SharedperfencesUtil.getInt(getContext(), "userId");
        JLog.v("userId", userId);
        if (userId != 0) {
            presenter.getUserInfo(userId);
            login_register.setVisibility(View.GONE);
//            loginRelative.setVisibility(View.GONE);
//            userRefreshView.setVisibility(View.VISIBLE);
            myprice.setVisibility(View.VISIBLE);
            showSign.setVisibility(View.VISIBLE);
            signStatusPresenter.signStatus();
        } else {
            login_register.setVisibility(View.VISIBLE);
//            loginRelative.setVisibility(View.VISIBLE);
//            userRefreshView.setVisibility(View.GONE);
//            show.setVisibility(View.GONE);
            show.setVisibility(View.INVISIBLE);
            showSign.setVisibility(View.GONE);
            userNameText.setText("登录");
            userIconImg.setImageResource(R.mipmap.touxiang);
            money.setText("");
            myprice.setVisibility(View.GONE);
        }

        if (SharedperfencesUtil.getInt(getContext(), "agent") == 1) {//1为成员
            Log.w("thevisib", SharedperfencesUtil.getInt(getContext(), "agent") + "");
            daililinear.setVisibility(View.GONE);
            managerLinear.setVisibility(View.INVISIBLE);
        } else {
            daililinear.setVisibility(View.VISIBLE);
            managerLinear.setVisibility(View.VISIBLE);
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
//                loginRelative.setVisibility(View.GONE);
//                userRefreshView.setVisibility(View.VISIBLE);
                presenter.getUserInfo(userId);
                signStatusPresenter.signStatus();
            } else {
//                loginRelative.setVisibility(View.VISIBLE);
//                userRefreshView.setVisibility(View.GONE);
//                show.setVisibility(View.GONE);
                show.setVisibility(View.INVISIBLE);
                showSign.setVisibility(View.GONE);
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
            userRefreshView.setRefreshing(false);
//            show.setVisibility(View.GONE);
            show.setVisibility(View.INVISIBLE);
            showSign.setVisibility(View.GONE);
            userNameText.setText("登录");
            userIconImg.setImageResource(R.mipmap.touxiang);
        }
    }

    @Override
    public void onLoading() {
        waitDialog.showDialog();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        userRefreshView.setRefreshing(false);
        waitDialog.closeDialog();
    }

    @Override
    public void successed(UserInfo userInfo) {
        userRefreshView.setRefreshing(false);
        this.userInfo = userInfo;
//        userNameText.setText(userInfo.getUsername());
//        nikename.setText(userInfo.getNickname());
        userNameText.setText("用户名：" + userInfo.getUsername());
        nikename.setText("昵     称：" + userInfo.getNickname());
        userID.setText("ID：" + userInfo.getId());
        show.setVisibility(View.VISIBLE);
        showSign.setVisibility(View.VISIBLE);
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
        waitDialog.closeDialog();
    }

    public String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            @SuppressLint("WrongConstant")
            PackageInfo info = manager.getPackageInfo(packageName, 1);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

    @Override
    public void onDestroy() {
        if (updateMannger != null) {
            updateMannger.cancelDown();
        }
        super.onDestroy();
    }

    public void checkUser() {
        if(hasCheakced){
            setUI() ;

            setUI() ;
            setUI() ;
            setUI() ;
            setUI() ;
        }

    }
}

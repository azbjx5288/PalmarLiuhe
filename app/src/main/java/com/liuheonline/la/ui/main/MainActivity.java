package com.liuheonline.la.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.liuheonline.la.entity.BankCardEntity;
import com.liuheonline.la.entity.UpdateEntity;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.GetCardPresenter;
import com.liuheonline.la.mvp.presenter.UpdatePresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.main.fragment.BetFrameFragment;
import com.liuheonline.la.ui.main.fragment.ForumFragment;
import com.liuheonline.la.ui.main.fragment.IndexFragment;
import com.liuheonline.la.ui.main.fragment.InfoNewFragment;
import com.liuheonline.la.ui.main.fragment.UserFragment2;
import com.liuheonline.la.ui.widget.popu.NoticePopup;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.KeyboardControlMnanager;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.UpdateMannger;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseFragment;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import java.util.List;

public class MainActivity extends BaseMVPActivity<BaseView<List<UpdateEntity>>, UpdatePresenter, List<UpdateEntity>> {

    @BindId(R.id.menu_rg)
    private RadioGroup radioGroup;
    @BindId(R.id.rb_bet0)
    private ImageView bet0;

    @BindId(R.id.main_line)
    TextView main_line;
    @BindId(R.id.rb_bet0)
    ImageView tb_bet;
    @BindId(R.id.menu_rg)
    RadioGroup menu_rg;
    @BindId(R.id.rb_main)
    RadioButton rbMain;

    private AlertDialog updateDialog;
    private AlertDialog noticedialog;
    private NoticePopup noticePopup;

    private BaseFragment isShowFragment;
    private IndexFragment indexFragment;
    private InfoNewFragment infoFragment;
    //    private InfoListFragment infoFragment;
    private BetFrameFragment betFragment;
    private ForumFragment forumFragment;
    //    private UserFragment userFragment;
    private UserFragment2 userFragment;

    UpdateMannger updateMannger;
    private int index = 0;
    private boolean isShow = false;
    private static final String INDEX_FRAGMENT_KEY = "indexFragment";
    private static final String INFO_FRAGMENT_KEY = "infoFragment";
    private static final String BET_FRAGMENT_KEY = "betFragment";
    private static final String FORUM_FRAGMENT_KEY = "forumFragment";
    private static final String USER_FRAGMENT_KEY = "userFragment";
    GetCardPresenter getCardPresenter;
    WebPresenter webPresenter;

    private boolean boo = false;

    @Override
    protected void initData() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            indexFragment = (IndexFragment) getSupportFragmentManager().getFragment(savedInstanceState, INDEX_FRAGMENT_KEY);
            infoFragment = (InfoNewFragment) getSupportFragmentManager().getFragment(savedInstanceState, INFO_FRAGMENT_KEY);
//            infoFragment = (InfoListFragment) getSupportFragmentManager().getFragment(savedInstanceState, INFO_FRAGMENT_KEY);
            betFragment = (BetFrameFragment) getSupportFragmentManager().getFragment(savedInstanceState, BET_FRAGMENT_KEY);
            forumFragment = (ForumFragment) getSupportFragmentManager().getFragment(savedInstanceState, FORUM_FRAGMENT_KEY);
//            userFragment = (UserFragment) getSupportFragmentManager().getFragment(savedInstanceState, USER_FRAGMENT_KEY);
            userFragment = (UserFragment2) getSupportFragmentManager().getFragment(savedInstanceState, USER_FRAGMENT_KEY);
            //isShowFragment = (BaseFragment) getSupportFragmentManager().getFragment(savedInstanceState,SHOW_FRAGMENT_KEY);
        } else {
            indexFragment = new IndexFragment();
            infoFragment = new InfoNewFragment();
//            infoFragment = new InfoListFragment();
            betFragment = new BetFrameFragment();
            forumFragment = new ForumFragment();
//            userFragment = new UserFragment();
            userFragment = new UserFragment2();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            index = bundle.getInt("index");
        }

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {

        //状态栏设置为红色
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initView() {

        updateDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_up)
                .setCancelable(false)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();

        noticePopup = new NoticePopup(this);
//        noticePopup.setOnSureClickListener(this::showFloatWindow);
        noticePopup.setOnSureClickListener(new NoticePopup.OnSureClickListener() {
            @Override
            public void onSure() {

            }
        });
        noticedialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_notice)
                .setCancelable(false)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Dip2pxUtil.dip2px(this, 600f))
                .create();
        //初始化首页
        bet0.setImageResource(R.mipmap.touzhunews1);
        switch (index) {
            case 0:
                isShowFragment = indexFragment;
                radioGroup.check(R.id.rb_main);
                break;
            case 1:
                isShowFragment = infoFragment;
                radioGroup.check(R.id.rb_info);
                break;
            case 2:
                isShowFragment = betFragment;
                radioGroup.check(R.id.rb_bet1);
                bet0.setImageResource(R.mipmap.touzhunews2);
                break;
            case 3:
                isShowFragment = forumFragment;
                radioGroup.check(R.id.rb_forum);
                break;
            case 4:
                isShowFragment = userFragment;
                radioGroup.check(R.id.rb_user);
                break;
        }

        if (!isShowFragment.isAdded())
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, isShowFragment).commit();
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(this, new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
            View anchorView;

            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                if (isVisible) {
                    //定位评论框到view
                    main_line.setVisibility(View.GONE);
                    tb_bet.setVisibility(View.GONE);
                    menu_rg.setVisibility(View.GONE);
                } else {
                    //定位到底部
                    main_line.setVisibility(View.VISIBLE);
                    tb_bet.setVisibility(View.VISIBLE);
                    menu_rg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.getActivityManager().destoryActivity(LauncherActivity.class);
    }*/

    @OnClick({R.id.rb_info, R.id.rb_bet0, R.id.rb_bet1, R.id.rb_main, R.id.rb_forum, R.id.rb_user})
    private void onClick(View view) {
        radioGroup.check(view.getId());
        bet0.setImageResource(R.mipmap.touzhunews1);
        switch (view.getId()) {
            case R.id.rb_main:
                if (indexFragment == null) {
                    indexFragment = new IndexFragment();
                }
                switchContent(isShowFragment, indexFragment);
                break;
            case R.id.rb_info:
                if (infoFragment == null) {
                    infoFragment = new InfoNewFragment();
//                    infoFragment = new InfoListFragment();
                }
                switchContent(isShowFragment, infoFragment);
                break;
            case R.id.rb_forum:
                if (forumFragment == null) {
                    forumFragment = new ForumFragment();
                }
                switchContent(isShowFragment, forumFragment);
                break;
            case R.id.rb_user:
                if (userFragment == null) {
//                    userFragment = new UserFragment();
                    userFragment = new UserFragment2();
                }
                switchContent(isShowFragment, userFragment);
                break;
            case R.id.rb_bet0:
            case R.id.rb_bet1:
                bet0.setImageResource(R.mipmap.touzhunews2);
                if (betFragment == null) {
                    betFragment = new BetFrameFragment();
                }
                switchContent(isShowFragment, betFragment);
                break;
        }
    }

    private void switchContent(BaseFragment from, BaseFragment to) {
        if (isShowFragment != to) {
            isShowFragment = to;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                fragmentTransaction.hide(from).add(R.id.fragment_content, to).commit();
            } else {
                fragmentTransaction.hide(from).show(to).commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (indexFragment != null && indexFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, INDEX_FRAGMENT_KEY, indexFragment);
        }
        if (infoFragment != null && infoFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, INFO_FRAGMENT_KEY, infoFragment);
        }
        if (betFragment != null && betFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, BET_FRAGMENT_KEY, betFragment);
        }
        if (forumFragment != null && forumFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FORUM_FRAGMENT_KEY, forumFragment);
        }
        if (userFragment != null && userFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, USER_FRAGMENT_KEY, userFragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initPresenter() {
        presenter = new UpdatePresenter();
        presenter.attachView(this);
        presenter.getUpdate(getVersionName(this), 1);
        Log.w("theupdate", getVersionName(this));
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
                    Log.w("thehasCard", SharedperfencesUtil.getBoolean(MainActivity.this, "hasCard") + " Main 262");
                    SharedperfencesUtil.setBoolean(MainActivity.this, "hasCard", true);
                } else {
                    Log.w("thehasCard", SharedperfencesUtil.getBoolean(MainActivity.this, "hasCard") + " Main 265");
                    SharedperfencesUtil.setBoolean(MainActivity.this, "hasCard", false);
                }
            }
        });
        getCardPresenter.getCard(1, 1);
        webPresenter = new WebPresenter();
        webPresenter.attachView(new BaseView<WebEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(WebEntity webEntity) {
                JLog.v(webEntity.getAnnouncement().getAnnouncement());
                if (webEntity != null) {
                    String content = webEntity.getAnnouncement().getAnnouncement();
                    noticedialog.setText(R.id.content, content);
                    noticePopup.setContent(content);
                    if (!isShow && webEntity.getAnnouncement().getStatus().equals("1")) {
//                        noticedialog.show();
                        noticePopup.showPopupWindow();
                        isShow = true;
                    }
                    SharedperfencesUtil.setBoolean(MainActivity.this, "noticeIsShow", isShow);
                    noticedialog.setOnclickListener(R.id.sure, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JLog.v("弹窗关闭");
                            noticedialog.dismiss();
                        }
                    });
                }
            }
        });
        webPresenter.getWeb();
    }

    @Override
    protected void fetchData() {

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
    public void onLoading() {

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
                    updateMannger = new UpdateMannger(MainActivity.this);
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
        }
    }

    @Override
    public boolean supportSlideBack() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (updateMannger != null) {
            updateMannger.cancelDown();
        }
        JLog.w("Activity弹窗销毁");
//        FloatWindow.destroy();
        super.onDestroy();
    }
}

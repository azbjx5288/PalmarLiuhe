package com.liuheonline.la.ui.user.account;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.event.StartTimeEvent;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.main.statistics.ViewPageAdapter;
import com.liuheonline.la.ui.widget.CustomDatePicker3;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AccountInfo extends BaseMVPActivity<BaseView<UserInfo>,UserInfoPresenter,UserInfo>{

    @BindId(R.id.info_total)
    TextView total;
    @BindId(R.id.info_out)
    TextView out;
    @BindId(R.id.info_in)
    TextView in;

    private TabLayout tabs;
    private ViewPager mPager;
    CustomDatePicker3 customDatePicker;
    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    @Override
    protected void initPresenter() {
        presenter  = new UserInfoPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getUserInfo(SharedperfencesUtil.getInt(this,"userId"));
    }

    @Override
    protected void initData() {

    }
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker3(this, new CustomDatePicker3.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                Log.w("the year",time.substring(0,7));
                StartTimeEvent startTimeEvent = new StartTimeEvent();
                startTimeEvent.setStarttime(time.substring(0,7));
                EventBus.getDefault().post(startTimeEvent);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_accountinfo);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        new DefaultNavigationBar.Builder(this)
                .setTitle("账户信息")
                .setLeftIconVisibility(false)
                .setIcon(R.mipmap.rili)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDatePicker.show();
                    }
                })
                .builder();
    }

    private void initViewPager() {
        mPager = findViewById(R.id.viewPager);
        tabs =  findViewById(R.id.tabs);
        mTitle.add("账户明细");
        mTitle.add("充值记录");
        mTitle.add("提现记录");
        mFragment.add(new FragmentAccountDetail());
        mFragment.add(new FragmentTopupDetail());
        mFragment.add(new FragmentWithdrawDetail());

    }

    @Override
    protected void initView() {
        initViewPager();
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), mTitle, mFragment);
        mPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(mPager);
        //使用ViewPager的适配器
        tabs.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(UserInfo userInfo) {
        total.setText(userInfo.getAvailable_predeposit());
        in.setText(userInfo.getIncome()+"");
        out.setText(userInfo.getSpending()+"");
    }

}

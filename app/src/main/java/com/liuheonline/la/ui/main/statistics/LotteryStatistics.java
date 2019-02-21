package com.liuheonline.la.ui.main.statistics;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.event.PeriodsEvent;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.PeriodsPopu;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LotteryStatistics extends BaseMVPActivity  {

    private TabLayout tabs;
    private ViewPager mPager;

    int lotteryId = 0;

    private DefaultNavigationBar defaultNavigationBar;

    private PeriodsPopu periodsPopu;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    List<Integer> int_list = new ArrayList<>();
    ArrayAdapter<Integer> integerArrayAdapter;

    @Override
    protected void initPresenter() {
    }

    private void initViewPager() {
        mPager = findViewById(R.id.viewPager);
        tabs =  findViewById(R.id.tabs);
        mTitle.add("号码");
        mTitle.add("生肖");
        mTitle.add("波色");
        mTitle.add("头尾");

        mFragment.add(new FragmentNum());
        mFragment.add(new FragmentZodiac());
        mFragment.add(new FragmentColor());
        mFragment.add(new FragmentHeadFooter());
    }
    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {
        int_list.add(30);
        int_list.add(50);
        int_list.add(100);
        int_list.add(200);
        //适配器
        integerArrayAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, int_list);
        //设置样式
        integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lotterystatistics);
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("数据统计")
                .setLeftIconVisibility(false)
                .setRightText("近30期")
                .setRightClickListener(view -> {
                    periodsPopu.showPopupWindow(R.id.right_text);
                })
                .builder();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        lotteryId = intent.getExtras().getInt("lotteryid");
        initViewPager();
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), mTitle, mFragment);
        mPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(mPager);
        //使用ViewPager的适配器
        tabs.setTabsFromPagerAdapter(adapter);
        tabs.getTabAt(0).setIcon(R.mipmap.haoma);
        tabs.getTabAt(1).setIcon(R.mipmap.shengxiao);
        tabs.getTabAt(2).setIcon(R.mipmap.bose);
        tabs.getTabAt(3).setIcon(R.mipmap.touwei);

        periodsPopu = new PeriodsPopu(this, issue -> {
            defaultNavigationBar.setRightText("近"+issue+"期");
            //发送消息到fragment
            PeriodsEvent periodsEvent = new PeriodsEvent();
            periodsEvent.setPeriods(issue);
            EventBus.getDefault().post(periodsEvent);
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }


}

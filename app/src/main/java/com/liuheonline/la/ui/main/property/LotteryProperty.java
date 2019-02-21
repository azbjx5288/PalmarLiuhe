package com.liuheonline.la.ui.main.property;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.main.statistics.ViewPageAdapter;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class LotteryProperty extends BaseMVPActivity {

    private TabLayout tabs;
    private ViewPager mPager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    int  lotteryId = 0;
    @Override
    protected void initPresenter() {

    }

    private void initViewPager() {
        mPager = findViewById(R.id.viewPager);
        tabs =  findViewById(R.id.tabs);
        mTitle.add("波色");
        mTitle.add("生肖");
        mTitle.add("五行");
        mTitle.add("家禽野兽");

        mFragment.add(new FragmentPropertyColor());
        mFragment.add(new FragmentPropertyZodiac());
        mFragment.add(new FragmentPropertyFive());
        mFragment.add(new FragmentPropertyAnimal());
    }
    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lotteryproperty);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("彩票属性")
                .setLeftIconVisibility(false)
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
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}

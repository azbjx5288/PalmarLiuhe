package com.liuheonline.la.ui.user.agency;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.main.statistics.ViewPageAdapter;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class AgencyCenter extends BaseMVPActivity {
    private TabLayout tabs;
    private ViewPager mPager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }
    private void initViewPager() {
        mPager = findViewById(R.id.viewPager);
        tabs =  findViewById(R.id.tabs);
        mTitle.add("我的代理");
        mTitle.add("发展下线");

        mFragment.add(new FragmentMyAgency());
        mFragment.add(new FragmentOffLine());
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agencycenter);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("代理中心")
                .setLeftIconVisibility(false)
                .builder();

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
    public void successed(Object o) {

    }
}

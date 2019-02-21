package com.liuheonline.la.ui.user.lotterylog;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.liuheonline.la.ui.main.statistics.ViewPageAdapter;
import com.liuheonline.la.ui.user.lotterylog.fragment.LotteryLogFragment;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: aini
 * @date 2018/8/2 14:18
 * @description 投注记录主页面
 */
public class LotteryLogActivity extends BaseFrameActivity {

    @BindId(R.id.log_tab)
    private TabLayout tabLayout;

    @BindId(R.id.log_viewpager)
    private ViewPager viewPager;

    private List<String> mTitle = new ArrayList<>();

    private List<Fragment> mFragment = new ArrayList<>();

    private String lotteryid = "";

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int anInt = extras.getInt("lotteryid", 0);
            if (anInt == 0) {
                lotteryid = "";
            } else {
                lotteryid = anInt + "";
            }
        } else {
            lotteryid = "";
        }
        mTitle.add("全部");
        mTitle.add("已中奖");
        mTitle.add("待开奖");
        mTitle.add("未中奖");
        mTitle.add("已取消");

        LotteryLogFragment allLotteryLogFragment = new LotteryLogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        bundle.putString("lotteryid", lotteryid);
        allLotteryLogFragment.setArguments(bundle);

        LotteryLogFragment winningPrizeFragment = new LotteryLogFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 30);
        bundle2.putString("lotteryid", lotteryid);
        winningPrizeFragment.setArguments(bundle2);

        LotteryLogFragment openPrizeFragment = new LotteryLogFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("type", 20);
        bundle4.putString("lotteryid", lotteryid);
        openPrizeFragment.setArguments(bundle4);

        LotteryLogFragment notWinningPrizeFragment = new LotteryLogFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", 40);
        bundle3.putString("lotteryid", lotteryid);
        notWinningPrizeFragment.setArguments(bundle3);

        LotteryLogFragment cancleLotteryLogFragment = new LotteryLogFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putInt("type", 50);
        bundle5.putString("lotteryid", lotteryid);
        cancleLotteryLogFragment.setArguments(bundle5);

        mFragment.add(allLotteryLogFragment);
        mFragment.add(winningPrizeFragment);
        mFragment.add(openPrizeFragment);
        mFragment.add(notWinningPrizeFragment);
        mFragment.add(cancleLotteryLogFragment);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lottery_log);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("游戏记录")
                .builder();
    }

    @Override
    protected void initView() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), mTitle, mFragment);
        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabLayout.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
    }


}

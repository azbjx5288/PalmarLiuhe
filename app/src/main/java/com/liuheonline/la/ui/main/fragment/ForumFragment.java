package com.liuheonline.la.ui.main.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.liuheonline.la.event.ForumTopEvent;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.forum.AddForumActivity;
import com.liuheonline.la.ui.forum.FragmentChat;
import com.liuheonline.la.ui.forum.FragmentCommunity;
import com.liuheonline.la.ui.main.statistics.ViewPageAdapter;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends BaseMvpFragment {

    @BindId(R.id.tabs)
    private TabLayout tabs;
    @BindId(R.id.viewPager)
    private ViewPager mPager;

    @BindId(R.id.add_photo)
    private ImageView addPhoto;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    private int index = 0;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    private void initViewPager() {
        mTitle.add("论坛");
        mTitle.add("聊天室");
        mFragment.add(new FragmentCommunity());
        mFragment.add(new FragmentChat());
    }

    @Override
    protected void initViews() {
        initViewPager();
        ViewPageAdapter adapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(), mTitle, mFragment);
        mPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(mPager);
        //使用ViewPager的适配器
        tabs.setTabsFromPagerAdapter(adapter);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*if(SharedperfencesUtil.getInt(getContext(),"userId") == 0){
                    startActivity(LoginActivity.class);
                    return;
                }*/
                addPhoto.setVisibility(tab.getPosition() == 0 ? View.VISIBLE : View.GONE);
                index = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            if (tab == null) {
                continue;
            }
            Class tabClass = tab.getClass();
            try {
                Field field = tabClass.getDeclaredField("mView");
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                View view = (View) field.get(tab);
                if (view == null) {
                    continue;
                }
                view.setTag(i);
                view.setOnClickListener(view1 -> {
                    if (index == 0) {
                        EventBus.getDefault().post(new ForumTopEvent(true));
                        tab.select();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.add_photo})
    private void addPhoto() {
        if (SharedperfencesUtil.getInt(mContext, "userId") == 0) {
            startActivity(LoginActivity.class);
        } else {
            startActivity(AddForumActivity.class);
        }
    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}

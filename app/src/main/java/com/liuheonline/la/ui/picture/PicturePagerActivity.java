package com.liuheonline.la.ui.picture;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.yxt.itv.library.ioc.BindId;

import java.util.List;


/**
 * @author: aini
 * @date 2018/7/24 18:58
 * @description 图片浏览activity
 */
public class PicturePagerActivity extends BaseFrameActivity{

    @BindId(R.id.viewpager)
    private ViewPager imgViewPager;

    private PicturePagerAdapter imagePagerAdapter;

    private List<String> urlList;

    private int index;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.urlList = bundle.getStringArrayList("urlList");
            this.index = bundle.getInt("index");
        }

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_imgagelook);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
        imagePagerAdapter = new PicturePagerAdapter(urlList, (view, postion) -> finish());
        imgViewPager.setAdapter(imagePagerAdapter);
        imgViewPager.setCurrentItem(index);
        imagePagerAdapter.notifyDataSetChanged();
    }
}

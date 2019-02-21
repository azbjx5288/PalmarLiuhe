package com.liuheonline.la.ui.infomation;


import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;


/**
 * 查看图片
 */
public class InfomationDetails extends BaseFrameActivity {

    @BindId(R.id.info_detail)
    private PhotoView imageView;

    private String picUrl = "";

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            picUrl = bundle.getString("url");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_infodetail);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("资料详情")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        Glide.with(this)
                .load(picUrl)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.jianzaizhong) //加载中图片
                        .error(R.mipmap.jiazaishibai) //加载失败图片
                        .fallback(R.mipmap.jianzaizhong) //url为空图片
                        .fitCenter() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

}

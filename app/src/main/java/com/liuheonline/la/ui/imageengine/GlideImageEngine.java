package com.liuheonline.la.ui.imageengine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.ysyy.aini.palmarliuhe.R;

/**
 * @author BenYanYi
 * @date 2019/01/16 16:02
 * @email ben@yanyi.red
 * @overview
 */
public class GlideImageEngine implements ImageEngine {
    @Override
    public void loadImage(Context context, String url, ImageView imageView, View progressView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.jianzaizhong) //加载中图片
                        .error(R.mipmap.jianzaizhong) //加载失败图片
                        .fallback(R.mipmap.jianzaizhong) //url为空图片
                        .centerInside()// 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }
}

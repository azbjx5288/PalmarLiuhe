package com.liuheonline.la.ui.picture;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.ysyy.aini.palmarliuhe.R;

import java.util.List;


/**
 * @author: aini
 * @date 2018/7/24 19:53
 * @description 图片浏览适配器
 */
public class PicturePagerAdapter extends PagerAdapter{

    private List<String> mItems;

    private OnImgItemClick onImgItemClick;

    public PicturePagerAdapter(List<String> items, OnImgItemClick onImgItemClick){
        this.mItems = items;
        this.onImgItemClick = onImgItemClick;
    }

    @Override
    public int getCount() {
        return(mItems == null? 0 : mItems.size());
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setOnClickListener(v -> onImgItemClick.onItemClick(v,position));
        Glide.with(container)
                .load(mItems.get(position))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.jianzaizhong) //加载中图片
                        .error(R.mipmap.jianzaizhong) //加载失败图片
                        .fallback(R.mipmap.jianzaizhong) //url为空图片
                        .centerInside()// 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    interface OnImgItemClick{
        void onItemClick(View view,int postion);
    }
}

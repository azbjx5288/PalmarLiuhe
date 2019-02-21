package com.liuheonline.la.ui.forum.holder;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

/**
 * @author: aini
 * @date 2018/7/23 10:21
 * @description
 */
public class GlideImageLoader implements ImageLoader{
    @Override
    public void displayImage(Activity activity, Context context, String path,
                             GalleryImageView galleryImageView, int width, int height) {
        Glide.with(context)
                .load(path)
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}

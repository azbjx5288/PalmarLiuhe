package com.liuheonline.la.ui.main.holder;

import android.content.Context;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

public class NetImageLoadHolder implements Holder<String> {
    private ImageView image_lv;

    //可以是一个布局也可以是一个Imageview
    @Override
    public ImageView createView(Context context) {
        image_lv = new ImageView(context);
        image_lv.setScaleType(ImageView.ScaleType.FIT_XY);

        return image_lv;

    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        //Glide框架
        Glide.with(context).load(data).into(image_lv);

    }
}

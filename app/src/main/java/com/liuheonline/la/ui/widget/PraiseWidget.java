package com.liuheonline.la.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.widget.span.CustomImageSpan;
import com.liuheonline.la.ui.widget.span.SpannableStringBuilderCompat;

import java.util.List;
import java.util.Map;


/**
 * @author: aini
 * @date 2018/7/20 10:06
 * @description 点赞列表
 */
public class PraiseWidget extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "PraiseWidget";

    //点赞名字展示的默认颜色
    private int textColor = 0xff517fae;
    //点赞列表心心默认图标
    private int iconRes = R.mipmap.dianzanhou;
    //默认字体大小
    private int textSize = 14;
    //默认点击背景
    private int clickBg = 0x00000000;

    private static final LruCache<String, SpannableStringBuilderCompat> praiseCache
            = new LruCache<String, SpannableStringBuilderCompat>(50) {
        @Override
        protected int sizeOf(String key, SpannableStringBuilderCompat value) {
            return 1;
        }
    };

    public PraiseWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PraiseWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PraiseWidget);
        textColor = a.getColor(R.styleable.PraiseWidget_font_color, 0xff517fae);
        textSize = a.getDimensionPixelSize(R.styleable.PraiseWidget_font_size, 14);
        clickBg = a.getColor(R.styleable.PraiseWidget_click_bg_color, 0x00000000);
        iconRes = a.getResourceId(R.styleable.PraiseWidget_like_icon, R.mipmap.dianzanhou);
        a.recycle();
        //如果不设置，clickableSpan不能响应点击事件
        setTextSize(textSize);
        setTextColor(textColor);
        //setBackgroundColor(clickBg);
    }

    public void setDatas(List<Map<String,Object>> datas) {
        createSpanStringBuilder(datas);
    }


    private void createSpanStringBuilder(List<Map<String,Object>> datas) {
        if (datas == null || datas.size() == 0) return;
        String key = Integer.toString(datas.hashCode() + datas.size());
        SpannableStringBuilderCompat spanStrBuilder = praiseCache.get(key);
        if (spanStrBuilder == null) {
            CustomImageSpan icon = new CustomImageSpan(getContext(), iconRes);
            //因为spanstringbuilder不支持直接append span，所以通过spanstring转换
            SpannableString iconSpanStr = new SpannableString(" ");
            iconSpanStr.setSpan(icon, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            spanStrBuilder = new SpannableStringBuilderCompat(iconSpanStr);
            //给出两个空格，点赞图标后
            spanStrBuilder.append(" ");
            for (int i = 0; i < datas.size(); i++) {
                try {
                    //spanStrBuilder.append(datas.get(i).getNick(), praiseClick, 0);
                    spanStrBuilder.append(datas.get(i).get("nickname").toString());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.e(TAG, "praiseUserInfo是空的哦");
                }
                if (i != datas.size() - 1) spanStrBuilder.append(", ");
                else spanStrBuilder.append("\0");
            }
            praiseCache.put(key, spanStrBuilder);
        }
        setText(spanStrBuilder);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        praiseCache.evictAll();
        if (praiseCache.size() == 0) {
            Log.d(TAG, "clear cache success!");
        }
    }
}

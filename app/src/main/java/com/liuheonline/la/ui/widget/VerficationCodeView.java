package com.liuheonline.la.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.liuheonline.la.utils.CodeUtils;

public class VerficationCodeView extends View {
    public Paint mPaint;
    public Bitmap mBitmap;

    public VerficationCodeView(Context context) {
        super(context);
        init();
    }


    public VerficationCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(32);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            mBitmap = CodeUtils.getInstance().createBitmap();
        }
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

    }


    public void refresh() {
        mBitmap = CodeUtils.getInstance().createBitmap();
        invalidate();
    }
}

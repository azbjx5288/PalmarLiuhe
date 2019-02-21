package com.liuheonline.la.ui.user.share;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.popu.SaveImgMenuPopup;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.ImgSaveUtils;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.HashMap;
import java.util.Map;

public class ShareActivity extends BaseMVPActivity<BaseView<WebEntity>, WebPresenter, WebEntity> {
    @BindId(R.id.erweima)
    ImageView erweima;
    @BindId(R.id.save)
    ImageView saveimg;
    @BindId(R.id.money)
    TextView money;
    private SaveImgMenuPopup saveImgMenuPopup;

    //    GetInviteNumPresenter
    @Override
    protected void initPresenter() {
        presenter = new WebPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getWeb();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_share);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("分享软件")
                .builder();
    }

    @Override
    protected void initView() {
        saveImgMenuPopup = new SaveImgMenuPopup(this);
        saveImgMenuPopup.setOnDeleteCommentMenuClickListener(() -> {
            boolean flag = ImgSaveUtils.saveImageToGallery(ShareActivity.this, erweima);
            Toast.makeText(LiuHeApplication.context, flag ? "保存成功" : "保存失败", Toast.LENGTH_SHORT).show();
        });
        saveimg.setOnClickListener(v -> {
            saveImgMenuPopup.showPopupWindow();
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(WebEntity webEntity) {
        if (webEntity != null) {
            String shareurl = webEntity.getShare_url();
            if (shareurl.trim().length() > 0) {
                shareurl += SharedperfencesUtil.getInt(this, "userId");
                Bitmap bitmap = generateBitmap(shareurl, Dip2pxUtil.dip2px(ShareActivity.this, Float.valueOf("160")), Dip2pxUtil.dip2px(ShareActivity.this, Float.valueOf("160")));
                erweima.setImageBitmap(bitmap);
            }
            money.setText(" " + webEntity.getExp_obtain() + " ");
        }
    }


    @OnClick(R.id.save)
    private void onClick(View view) {

    }

    //第一个参数为扫码后显示的内容   第二个为二维码的宽   第三个为二维码的高
    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}

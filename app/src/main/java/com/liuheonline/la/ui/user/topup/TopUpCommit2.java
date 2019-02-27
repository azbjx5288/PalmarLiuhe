package com.liuheonline.la.ui.user.topup;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.PaysEntity;
import com.liuheonline.la.entity.QueryEntity;
import com.liuheonline.la.entity.RechargeSNEntity;
import com.liuheonline.la.mvp.presenter.GetRechargeSNPresenter;
import com.liuheonline.la.mvp.presenter.NewPaysPresenter;
import com.liuheonline.la.mvp.presenter.PaysCreatePresenter;
import com.liuheonline.la.mvp.presenter.PaysPresenter;
import com.liuheonline.la.mvp.presenter.QueryPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.popu.SaveImgMenuPopup;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.ImgSaveUtils;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.HashMap;
import java.util.Map;

public class TopUpCommit2 extends BaseMVPActivity<BaseView<RechargeSNEntity>, GetRechargeSNPresenter, RechargeSNEntity> {

    @BindId(R.id.order_number)
    TextView order_number;
    PayMentEntity payMentEntity;
    PaysPresenter paysPresenter;
    QueryPresenter queryPresenter;
    @BindId(R.id.saomakuang)
    ImageView saomakuang;
    CountDownTimer countDownTimer;//倒计时

    @BindId(R.id.open_alipay)
    TextView openAlipay;
    @BindId(R.id.top_saveimg)
    TextView saveimg;
    NewPaysPresenter newPaysPresenter;
    private SaveImgMenuPopup saveImgMenuPopup;
    /*@BindId(R.id.linear_tishi1)
    LinearLayout tishi1;*/
    @BindId(R.id.linear_tishi2)
    LinearLayout tishi2;
    private long orderid;
    private String sign = "";

    @BindId(R.id.topup_tishi0)
    TextView tishi0;
    @BindId(R.id.m1)
    TextView m1;
    @BindId(R.id.m2)
    TextView m2;
    @BindId(R.id.s1)
    TextView s1;
    @BindId(R.id.s2)
    TextView s2;
    @BindId(R.id.topup_price)
    TextView topup_price;
    /*@BindId(R.id.topup_tishiprice)
    TextView tishiprice;*/
    int times = 0;

    boolean isfirst = true;
    AlertDialog paydialog;
    String topprice = "";

    private String alipayUrl;
    /**
     * 刷新二维码
     */
    private PaysCreatePresenter paysCreatePresenter;

    private AlertDialog waitDialog;

    @Override
    protected void initPresenter() {
        presenter = new GetRechargeSNPresenter();
        paysPresenter = new PaysPresenter();
        newPaysPresenter = new NewPaysPresenter();
        queryPresenter = new QueryPresenter();
        queryPresenter.attachView(new BaseView<QueryEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                JLog.w("topup2info", error);
            }

            @Override
            public void successed(QueryEntity queryEntity) {
                JLog.w("topup2info", queryEntity);
                if (queryEntity.getPdr_payment_state().equals("1")) {
                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    Log.w("queryentity", "支付成功");
                    Bundle bundle = new Bundle();
                    bundle.putString("price", topprice);
                    startActivity(TopUpFinish.class, bundle);
                    finish();
                }
            }
        });
        newPaysPresenter.attachView(new BaseView<PaysEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(PaysEntity paysEntity) {
                JLog.d("newPaysPresenter.success");
                dell(paysEntity);
            }
        });
        paysPresenter.attachView(new BaseView<PaysEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                if (error.indexOf("提交订单时间") != -1 && payMentEntity.getName().equals("支付宝")) {//重复提交就用上一次的缓存数据&&选择的类型与缓存的支付类型一致
                    String s = error.substring(error.indexOf("请在") + 2, error.indexOf("秒"));
                    //Log.w("the sss",s);
                    try {
                        int ss = Integer.parseInt(s);
                        LiuHeApplication.getZhifubaopay().getOrder().setRemainseconds(ss);
                        PaysEntity paysEntity = LiuHeApplication.getZhifubaopay();
                        topprice = paysEntity.getOrder().getPrice();
                        topup_price.setText("充值金额:￥" + paysEntity.getOrder().getPrice());
                        //tishiprice.setText("扫码后请手动输入金额￥"+paysEntity.getOrder().getPrice());
                        paydialog = new AlertDialog.Builder(TopUpCommit2.this)
                                .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                                .setContentView(R.layout.dialog_pay)
                                .setCancelable(false)
                                .setText(R.id.money, paysEntity.getOrder().getPrice())
                                .setText(R.id.money2, "￥ " + paysEntity.getOrder().getPrice())
                                .setOnClickListener(R.id.dialog_open_alipay, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        JLog.v();
                                        paydialog.dismiss();
                                        openAlipay();
                                    }
                                })
                                .setOnClickListener(R.id.dialog_save, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        JLog.v();
                                        paydialog.dismiss();
                                        saveImgMenuPopup.showPopupWindow();
                                    }
                                })
                                .setOnClickListener(R.id.dialog_cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JLog.v();
                                        paydialog.dismiss();
                                    }
                                })
                                .create();
                        if (isfirst) {
                            paydialog.show();
                            isfirst = false;
                        }
                        sign = paysEntity.getSign();
                        orderid = paysEntity.getOrder().getOut_order_id();
                        JLog.d("paysPresenter.onLoadFailed1");
                        dell(paysEntity);
                    } catch (Exception e) {
                    }

                } else if (error.indexOf("提交订单时间") != -1 && payMentEntity.getName().equals("微信支付")) {
                    String s = error.substring(error.indexOf("请在") + 2, error.indexOf("秒"));
                    //Log.w("the sss",s);
                    try {
                        int ss = Integer.parseInt(s);
                        LiuHeApplication.getWeixinpay().getOrder().setRemainseconds(ss);
                        PaysEntity paysEntity = LiuHeApplication.getWeixinpay();
                        topprice = paysEntity.getOrder().getPrice();
                        topup_price.setText("充值金额:￥" + paysEntity.getOrder().getPrice());
                        //tishiprice.setText("扫码后请手动输入金额￥"+paysEntity.getOrder().getPrice());
                        paydialog = new AlertDialog.Builder(TopUpCommit2.this)
                                .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                                .setContentView(R.layout.dialog_pay2)
                                .setCancelable(false)
                                .setText(R.id.money, paysEntity.getOrder().getPrice())
                                .setText(R.id.money2, "￥ " + paysEntity.getOrder().getPrice())
                                .setOnClickListener(R.id.dialog_save, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        JLog.v();
                                        paydialog.dismiss();
                                        saveImgMenuPopup.showPopupWindow();
                                    }
                                })
                                .setOnClickListener(R.id.dialog_cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JLog.v();
                                        paydialog.dismiss();
                                    }
                                })
                                .create();
                        if (isfirst) {
                            paydialog.show();
                            isfirst = false;
                        }
                        sign = paysEntity.getSign();
                        orderid = paysEntity.getOrder().getOut_order_id();
                        JLog.d("paysPresenter.onLoadFailed2");
                        dell(paysEntity);
                    } catch (Exception e) {
                    }

                } else {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
                waitDialog.cancel();
            }

            @Override
            public void successed(PaysEntity paysEntity) {
                JLog.d(paysEntity);
                if (paysEntity.getPayAppName().equals("支付宝")) {
                    LiuHeApplication.setZhifubaopay(paysEntity);
                } else {
                    LiuHeApplication.setWeixinpay(paysEntity);
                }
                topprice = paysEntity.getOrder().getPrice();
                topup_price.setText("充值金额:￥" + paysEntity.getOrder().getPrice());
                if (paysEntity.getPayAppName().equals("支付宝")) {
                    alipayUrl = paysEntity.getOrder().getQrcodeurl();
                    paydialog = new AlertDialog.Builder(TopUpCommit2.this)
                            .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                            .setContentView(R.layout.dialog_pay)
                            .setCancelable(false)
                            .setText(R.id.money, paysEntity.getOrder().getPrice())
                            .setText(R.id.money2, "￥ " + paysEntity.getOrder().getPrice())
                            .setOnClickListener(R.id.dialog_open_alipay, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JLog.v();
                                    paydialog.dismiss();
                                    openAlipay();
                                }
                            })
                            .setOnClickListener(R.id.dialog_save, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JLog.v();
                                    paydialog.dismiss();
                                    saveImgMenuPopup.showPopupWindow();
                                }
                            })
                            .setOnClickListener(R.id.dialog_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JLog.v();
                                    paydialog.dismiss();
                                }
                            })
                            .create();
                } else {
                    paydialog = new AlertDialog.Builder(TopUpCommit2.this)
                            .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                            .setContentView(R.layout.dialog_pay2)
                            .setCancelable(false)
                            .setText(R.id.money, paysEntity.getOrder().getPrice())
                            .setText(R.id.money2, "￥ " + paysEntity.getOrder().getPrice())
                            .setOnClickListener(R.id.dialog_save, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    paydialog.dismiss();
                                    saveImgMenuPopup.showPopupWindow();
                                }
                            })
                            .setOnClickListener(R.id.dialog_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    paydialog.dismiss();
                                }
                            })
                            .create();
                }
                //tishiprice.setText("扫码后请手动输入金额￥"+paysEntity.getOrder().getPrice());
//                paydialog = new AlertDialog.Builder(TopUpCommit2.this)
//                        .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//                        .setContentView(R.layout.dialog_pay)
//                        .setCancelable(false)
//                        .setText(R.id.money, paysEntity.getOrder().getPrice())
//                        .setText(R.id.money2, "￥ " + paysEntity.getOrder().getPrice())
//                        .setOnClickListener(R.id.getit, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                paydialog.dismiss();
//                            }
//                        })
//                        .create();
                if (isfirst) {
                    paydialog.show();
                    isfirst = false;
                }
                sign = paysEntity.getSign();
                orderid = paysEntity.getOrder().getOut_order_id();
                JLog.d("paysPresenter.successed");
                dell(paysEntity);
                waitDialog.cancel();
            }
        });
    }

    private void dell(PaysEntity paysEntity) {
        JLog.d(paysEntity);
        times = paysEntity.getOrder().getRemainseconds();
        //tishi1.setVisibility(View.VISIBLE);
        topup_price.setVisibility(View.VISIBLE);
        tishi0.setVisibility(View.GONE);
        tishi2.setVisibility(View.GONE);//提示点击刷新
        JLog.w("topup2info", paysEntity.toString());
        Bitmap bitmap = generateBitmap(paysEntity.getOrder().getQrcodeurl(), Dip2pxUtil.dip2px(TopUpCommit2.this, Float.valueOf("160")), Dip2pxUtil.dip2px(TopUpCommit2.this, Float.valueOf("160")));
        saomakuang.setImageBitmap(bitmap);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        JLog.d(orderid);
        JLog.d(sign);
        if (paysEntity.getOrder().getRemainseconds() != 0) {
            countDownTimer = new CountDownTimer(paysEntity.getOrder().getRemainseconds() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    JLog.w("topup2info", sign);
                    long m = millisUntilFinished / 1000 / 60 % 60;
                    long s = millisUntilFinished / 1000 % 60;
                    queryPresenter.pays(orderid + "", sign);
                    m1.setText(m / 10 + "");
                    m2.setText(m % 10 + "");
                    s1.setText(s / 10 + "");
                    s2.setText(s % 10 + "");

                }

                @Override
                public void onFinish() {
                    //tishi1.setVisibility(View.GONE);
                    topup_price.setVisibility(View.GONE);
                    tishi0.setVisibility(View.VISIBLE);
                    tishi2.setVisibility(View.VISIBLE);//提示点击刷新
                    saomakuang.setImageResource(R.mipmap.saomakuang);
                }
            };
            countDownTimer.start();
        }
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


    @OnClick({R.id.top_copy, R.id.linear_tishi2, R.id.open_alipay})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(order_number.getText());
                Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_tishi2:
                newPaysPresenter.pays(orderid + "", payMentEntity.getPrice() + "", payMentEntity.getId() + "");
                break;
            case R.id.open_alipay:
                JLog.v(topprice);
                JLog.v(payMentEntity.getPrice());
                openAlipay();
                break;

        }
    }

    private void openAlipay() {
        if (!TextUtils.isEmpty(alipayUrl)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(alipayUrl);
            intent.setData(content_url);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "打开支付宝失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void fetchData() {
        if (times == 0) {
            paysPresenter.pays(payMentEntity.getPrice() + "", payMentEntity.getId() + "");
            presenter.getsn();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topupcommit2);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("扫码支付")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        payMentEntity = (PayMentEntity) getIntent().getSerializableExtra("payMentEntity");
        if (payMentEntity != null && payMentEntity.getCode().equals("alipay")) {
            openAlipay.setVisibility(View.VISIBLE);
        } else {
            openAlipay.setVisibility(View.GONE);
        }
        saveImgMenuPopup = new SaveImgMenuPopup(this);
        saveImgMenuPopup.setOnDeleteCommentMenuClickListener(() -> {
            boolean flag = ImgSaveUtils.saveImageToGallery(TopUpCommit2.this, saomakuang);
            Toast.makeText(LiuHeApplication.context, flag ? "保存成功" : "保存失败", Toast.LENGTH_SHORT).show();
        });
        saveimg.setOnClickListener(v -> {
            saveImgMenuPopup.showPopupWindow();
        });

        waitDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait2)
                .setText(R.id.text_hint,"加载中……")
                .create();
        waitDialog.show();
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
    }

    @Override
    public void successed(RechargeSNEntity rechargeSNEntity) {
        Log.w("topup2info", rechargeSNEntity.getPay_sn().toString());
        order_number.setText("订单号:" + rechargeSNEntity.getPay_sn());
    }

}

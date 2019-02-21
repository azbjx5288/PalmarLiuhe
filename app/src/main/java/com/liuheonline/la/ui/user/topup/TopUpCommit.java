package com.liuheonline.la.ui.user.topup;


import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.RechargeEntity;
import com.liuheonline.la.entity.RechargeSNEntity;
import com.liuheonline.la.mvp.presenter.GetRechargeSNPresenter;
import com.liuheonline.la.mvp.presenter.RechargePresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.widget.CustomDatePicker2;
import com.liuheonline.la.ui.widget.popu.SaveImgMenuPopup;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.ImgSaveUtils;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TopUpCommit extends BaseMVPActivity<BaseView<RechargeEntity>,RechargePresenter,RechargeEntity> {

     GetRechargeSNPresenter getRechargeSNPresenter;
    PayMentEntity payMentEntity;
    @BindId(R.id.time)
    TextView texttime;
    @BindId(R.id.img)
    ImageView img;
    @BindId(R.id.price)
    EditText price;
    @BindId(R.id.name)
    EditText name;
    @BindId(R.id.desc)
    EditText desc;
    @BindId(R.id.order_id)
    TextView order_id;

    private SaveImgMenuPopup saveImgMenuPopup;

    CustomDatePicker2 customDatePicker;
    @OnClick({R.id.time,R.id.submit,R.id.order_copy})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.time:
                customDatePicker.show();
                break;
            case R.id.submit:
                if (texttime.getText().length()<10){
                    Toast.makeText(getApplicationContext(),"请选择时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (price.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(),"请输入金额",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(),"请输入姓名",Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("pdr_amount",price.getText().toString().trim());
                map.put("payment_code",payMentEntity.getCode());
                map.put("pdr_qrcode",2);
                map.put("pdr_qrcode_id",payMentEntity.getQrcode().getId());
                map.put("pdr_account",name.getText().toString().trim());
                map.put("pdr_note",desc.getText().toString());
                map.put("pdr_payment_time",texttime.getText().toString());
                map.put("pay_sn",order_id.getText().toString());
                presenter.recharge(map);
                break;
            case R.id.order_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(order_id.getText());
                Toast.makeText(getApplicationContext(),"复制成功",Toast.LENGTH_SHORT).show();
                break;
        }

    }
    @Override
    protected void initPresenter() {
        presenter = new RechargePresenter();
        getRechargeSNPresenter = new GetRechargeSNPresenter();
        getRechargeSNPresenter.attachView(new BaseView<RechargeSNEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(RechargeSNEntity o) {
                order_id.setText(o.getPay_sn());
            }
        });

    }

    @Override
    protected void fetchData() {
        getRechargeSNPresenter.getsn();
    }

    @Override
    protected void initData() {
        payMentEntity = (PayMentEntity) getIntent().getSerializableExtra("payMentEntity");
        //显示图片
        Glide.with(TopUpCommit.this)
                .load(ImageUrlUtil.getImgUrl(payMentEntity.getQrcode().getPic_link(),100,100))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.jianzaizhong) //加载中图片
                        .error(R.mipmap.jiazaishibai) //加载失败图片
                        .fallback(R.mipmap.jiazaishibai) //url为空图片
                        .override(100,100)
                        .centerCrop() // 填充方式
                        .priority(Priority.HIGH) //优先级
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(img);

    }
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker2(this, new CustomDatePicker2.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                texttime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topupcommit);
    }

    @Override
    protected void initTitle() {
        initDatePicker();
        new DefaultNavigationBar.Builder(this)
                .setTitle("充值详情")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        saveImgMenuPopup = new SaveImgMenuPopup(this);
        saveImgMenuPopup.setOnDeleteCommentMenuClickListener(() -> {
            boolean flag = ImgSaveUtils.saveImageToGallery(TopUpCommit.this,img);
            Toast.makeText(LiuHeApplication.context,flag ? "保存成功" : "保存失败",Toast.LENGTH_SHORT).show();
        });
        img.setOnLongClickListener(v -> {
            saveImgMenuPopup.showPopupWindow();
            return false;
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        Toast.makeText(getApplicationContext(),"提交失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successed(RechargeEntity rechargeEntity) {
        Toast.makeText(getApplicationContext(),"已提交",Toast.LENGTH_SHORT).show();
        finish();
    }

}

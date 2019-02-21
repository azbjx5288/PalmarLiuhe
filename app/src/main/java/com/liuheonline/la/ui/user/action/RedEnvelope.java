package com.liuheonline.la.ui.user.action;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.liuheonline.la.entity.RedEnvelopeEntity;
import com.liuheonline.la.mvp.presenter.RedEnvelopeAmountPresenter;
import com.liuheonline.la.mvp.presenter.RedEnvelopeNumPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author BenYanYi
 * @date 2018/12/13 13:07
 * @email ben@yanyi.red
 * @overview
 */
public class RedEnvelope extends BaseMVPActivity<BaseView<RedEnvelopeEntity>, RedEnvelopeNumPresenter, RedEnvelopeEntity> {

    @BindId(R.id.num)
    TextView tvNum;
    @BindId(R.id.grab)
    TextView tvGrab;


    private RedEnvelopeAmountPresenter amountPresenter;
    private int num = 0;

    private AlertDialog dialog;

    @Override
    protected void initPresenter() {
        amountPresenter = new RedEnvelopeAmountPresenter();
        amountPresenter.attachView(new BaseView<RedEnvelopeEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(RedEnvelope.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(RedEnvelopeEntity redEnvelopeEntity) {
                dialog.show();
                dialog.setText(R.id.money, redEnvelopeEntity.getRed_packed_amount() + "");
                dialog.setOnclickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        num = num - 1;
                        tvNum.setText(getString(R.string.remaining_times, num + ""));
                        dialog.dismiss();
                    }
                });
            }
        });
        presenter = new RedEnvelopeNumPresenter();
        presenter.attachView(this);
        presenter.getNum();
    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_redenvelope);
    }

    @OnClick({R.id.grab})
    private void onClick(View view) {
        if (num > 0) {
            amountPresenter.getAmount();
        }
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("抢红包")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        tvNum.setText(getString(R.string.remaining_times, "0"));
        dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_redenvelope)
                .setCancelable(false)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Dip2pxUtil.dip2px(this, 600f))
                .create();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(RedEnvelopeEntity redEnvelopeEntity) {
        num = redEnvelopeEntity.getRed_packed_num();
        tvNum.setText(getString(R.string.remaining_times, num + ""));
    }

    @Override
    public void onLoadFailed(int code, String error) {
        num = 0;
        tvNum.setText(getString(R.string.remaining_times, num + ""));
    }
}

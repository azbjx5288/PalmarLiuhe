package com.liuheonline.la.ui.infomation;


import android.content.Context;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.InviteNumEntity;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.GetInviteNumPresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

//内幕界面
public class LowdownActivity extends BaseMVPActivity<BaseView<WebEntity>, WebPresenter, WebEntity> {
    @BindId(R.id.tv_tuiguanglianjie)
    TextView tvShareUrl;
    @BindId(R.id.tv_num)
    TextView tvNum;
    private GetInviteNumPresenter numPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lowdown);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("资料")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initPresenter() {
        presenter = new WebPresenter();
        numPresenter = new GetInviteNumPresenter();
    }

    @Override
    protected void fetchData() {
        numPresenter.attachView(new BaseView<InviteNumEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                tvNum.setText("0");
            }

            @Override
            public void successed(InviteNumEntity inviteNumEntity) {
                tvNum.setText(String.format("%s", inviteNumEntity.getInvite_num()));
            }
        });
        presenter.getWeb();
        numPresenter.getNum();

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
                tvShareUrl.setText(shareurl);
            }
        }
    }

    @OnClick(R.id.iv_fuzhi)
    private void onClick(View view) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tvShareUrl.getText());
        Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
    }
}

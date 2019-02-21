package com.liuheonline.la.ui.main.fragment;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.JokerEntity;
import com.liuheonline.la.mvp.presenter.JokerPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.infomation.LowdownActivity;
import com.liuheonline.la.ui.infomation.TheoryActivity;
import com.liuheonline.la.ui.infomation.joker.JokerListActivity;
import com.liuheonline.la.ui.infomation.tuku.TukuActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

//图片列表页
public class InfoNewFragment extends BaseMvpFragment<BaseView<JokerEntity>, JokerPresenter, JokerEntity> {
    @BindId(R.id.iv_click_to_look)
    private ImageView ivToLook;
    @BindId(R.id.iv_look_xuanji_more)
    private ImageView ivLookXuanjiMore;
    @BindId(R.id.iv_look_xiaohua_more)
    private ImageView ivLookXiaohuaMore;
    @BindId(R.id.iv_more_tuku)
    private ImageView IvMoreTuku;
    @BindId(R.id.tv_xiaohua)
    private TextView tvXiaohua;

    private AlertDialog waitDialog;

    @Override
    protected void initPresenter() {
        presenter = new JokerPresenter();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        Glide.with(this).load(R.drawable.lookmore2)
                .into(ivToLook);
        Glide.with(this).load(R.drawable.lookmore)
                .into(ivLookXuanjiMore);
        Glide.with(this).load(R.drawable.lookmore)
                .into(ivLookXiaohuaMore);
        Glide.with(this).load(R.drawable.lookmore3)
                .into(IvMoreTuku);
        waitDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "获取数据中……")
                .create();
    }

    @OnClick({R.id.iv_click_to_look, R.id.iv1, R.id.iv_look_xuanji_more, R.id.iv_xuanji, R.id.iv_xiaohua, R.id.iv_look_xiaohua_more, R.id.iv_tuku, R.id.iv_more_tuku})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_click_to_look:
            case R.id.iv1:
                if (SharedperfencesUtil.getInt(getContext(), "userId") != 0) {
                    startActivity(LowdownActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.iv_look_xuanji_more:
            case R.id.iv_xuanji:
                startActivity(TheoryActivity.class);
                break;
            case R.id.iv_look_xiaohua_more:
            case R.id.iv_xiaohua:
                startActivity(JokerListActivity.class);
                break;
            case R.id.iv_more_tuku:
            case R.id.iv_tuku:
                startActivity(TukuActivity.class);
                break;
        }
    }

    @Override
    protected void initTitle(View view) {
        new DefaultNavigationBar
                .Builder(getContext(), (ViewGroup) view)
                .setTitle("资料")
                .setLeftIconVisibility(true)
                .builder();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_info_new;
    }

    @Override
    protected void fetchData() {
        presenter.addForum();
    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void successed(JokerEntity jokerEntity) {
        tvXiaohua.setText(Html.fromHtml(jokerEntity.getContent()));
        waitDialog.cancel();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
    }
}

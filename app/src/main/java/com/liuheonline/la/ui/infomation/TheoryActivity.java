package com.liuheonline.la.ui.infomation;

import android.view.View;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.TheoryEntity;
import com.liuheonline.la.mvp.presenter.TheoryPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.infomation.xuanji.XuanjiListActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class TheoryActivity extends BaseMVPActivity<BaseView<TheoryEntity>, TheoryPresenter, TheoryEntity> {
    @BindId(R.id.theorytitle)
    TextView title;
    @BindId(R.id.theorycontent)
    TextView content;

    @Override
    protected void initPresenter() {
        presenter = new TheoryPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getTheory();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.theorymore)
    private void onClick(View view) {
        startActivity(XuanjiListActivity.class);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_theory);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("玄机锦囊")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(TheoryEntity theoryEntity) {
        title.setText(theoryEntity.getPeriod() + "期");
        content.setText(theoryEntity.getContent());
    }

}

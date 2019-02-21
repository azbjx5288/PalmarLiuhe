package com.liuheonline.la.ui.bet;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.RulesEntity;
import com.liuheonline.la.mvp.presenter.RulesPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/8/17 19:09
 * @description 玩法规则
 */
public class RulesActivity extends BaseMVPActivity<BaseView<RulesEntity>,RulesPresenter,RulesEntity>{

    @BindId(R.id.content)
    private TextView content;

    private String rulesContent = "";
    int lotteryid = 0;
    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_rules);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("玩法规则")
                .builder();
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            rulesContent = bundle.getString("rulesContent");
            lotteryid = bundle.getInt("lotteryid");
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new RulesPresenter();
    }

    @Override
    protected void fetchData() {
        presenter.getRules(lotteryid);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(RulesEntity rulesEntity) {
        if (rulesEntity!=null){
            rulesContent = rulesEntity.getRules_content();
            if (rulesContent!=null)
                content.setText(Html.fromHtml(Html.fromHtml(rulesContent).toString()));
        }

    }
}

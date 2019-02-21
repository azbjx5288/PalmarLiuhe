package com.liuheonline.la.ui.user.agency2;

import android.text.Html;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class Agency2Desc extends BaseMVPActivity {

    @BindId(R.id.content)
    TextView content;
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {
        content.setText(Html.fromHtml(StringUtil.translation(SharedperfencesUtil.getString(this,"agent_announcement"))));
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_agencydesc);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("代理说明")
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
    public void successed(Object o) {

    }
}

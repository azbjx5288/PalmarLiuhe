package com.liuheonline.la.ui.infomation.joker;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.JokerEntity;
import com.yxt.itv.library.base.BaseActivity;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * 笑话详情
 */
public class JokerDetailActivity extends BaseActivity {
    @BindId(R.id.tv_title)
    private TextView tvTitle;
    @BindId(R.id.tv_content)
    private TextView tvContent;
    JokerEntity jokerEntity;

    @Override
    protected void initData() {
        jokerEntity = (JokerEntity) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_joker_detail);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("笑话详情")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        tvTitle.setText(jokerEntity.getTitle());
        tvContent.setText(Html.fromHtml(jokerEntity.getContent()));
    }

    //静态方法
    public static void lookDetail(Context context, JokerEntity jokerEntity) {
        Intent intent = new Intent(context, JokerDetailActivity.class);
        intent.putExtra("data", jokerEntity);
        context.startActivity(intent);
    }
}

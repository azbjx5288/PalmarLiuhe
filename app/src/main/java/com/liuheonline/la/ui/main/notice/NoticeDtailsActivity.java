package com.liuheonline.la.ui.main.notice;


import android.os.Bundle;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.NoticeEntity;
import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class NoticeDtailsActivity extends BaseFrameActivity{

    @BindId(R.id.content)
    private TextView contentText;

    @BindId(R.id.time)
    private TextView timeText;

    private NoticeEntity noticeEntity;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            noticeEntity = (NoticeEntity) bundle.getSerializable("notice");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_noticedetais);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("公告详情")
                .builder();
    }

    @Override
    protected void initView() {
        if(noticeEntity != null){
            contentText.setText(noticeEntity.getContent());
            timeText.setText(noticeEntity.getCreate_time());
        }
    }
}

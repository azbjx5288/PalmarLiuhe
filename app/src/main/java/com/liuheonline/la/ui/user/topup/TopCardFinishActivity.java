package com.liuheonline.la.ui.user.topup;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.ui.base.BaseFrameActivity;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

/**
 * @author: aini
 * @date 2018/8/24 17:50
 * @description 银行卡充值成功
 */
public class TopCardFinishActivity extends BaseFrameActivity{
    @Override
    protected void initData() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_topcardfinish);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("充值结果")
                .builder();
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.finish_btn})
    private void click(){
        finish();
    }
}

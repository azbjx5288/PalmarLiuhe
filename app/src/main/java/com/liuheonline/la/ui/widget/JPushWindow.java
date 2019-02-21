package com.liuheonline.la.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.event.JPushEvent;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.ioc.ViewUtils;
import com.yxt.itv.library.util.ActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.android.api.JPushInterface;

public class JPushWindow extends Activity {

    @BindId(R.id.show_text)
    private TextView text;
    @BindId(R.id.show_title)
    private TextView title;

    @OnClick(R.id.show_img)
    private void onClick(){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_jpushwindow);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        text.setText(Html.fromHtml(StringUtil.translation(bundle.getString(JPushInterface.EXTRA_ALERT))));
        //title.setText(bundle.getString("title"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            Log.w("the isregistered","true");
            EventBus.getDefault().register(this);
        }
        ActivityManager.getActivityManager().popActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.getActivityManager().pushActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pushEvent(JPushEvent pushEvent) {
        Log.w("JpushEvent","true");
        text.setText(pushEvent.getJpushMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

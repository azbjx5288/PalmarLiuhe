package com.liuheonline.la.receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.liuheonline.la.event.JPushEvent;
import com.liuheonline.la.ui.main.MainActivity;
import com.liuheonline.la.ui.widget.JPushWindow;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了自定义消息："+bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了通知");
            /*PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            boolean ifOpen = powerManager.isScreenOn();
            Log.w("the powerManager",ifOpen+"");
            Log.w("the receiver1 msg",bundle.getString(JPushInterface.EXTRA_ALERT));
            if (!ifOpen) {*/
                //拿到锁屏管理者
                KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                if(km.isKeyguardLocked()){
                    Intent intent1 = new Intent();
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    bundle.putString("title","最新消息");
                    intent1.putExtra("bundle",bundle);
                    intent1.setClass(context, JPushWindow.class);
                    context.startActivity(intent1);

                    JPushEvent jpush = new JPushEvent();
                    jpush.setJpushMsg(bundle.getString(JPushInterface.EXTRA_ALERT));
                    EventBus.getDefault().post(jpush);
                }
           // }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, MainActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}

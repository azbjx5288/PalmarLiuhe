package com.liuheonline.la.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.liuheonline.la.ui.widget.ProtectActivity;

public class ScreenReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.w("Screen","锁屏");
            Intent intentNew = new Intent(context, ProtectActivity.class);
            intentNew.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intentNew);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.w("Screen","解锁");
            ProtectActivity protectActivity = ProtectActivity.weakReference != null ? ProtectActivity.weakReference.get() : null;
            if(protectActivity != null){
                protectActivity.finish();
            }
//            Intent intentNew = new Intent(context, WelcomeActivity.class);
//            intentNew.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            context.startActivity(intentNew);
        }
    }
}

package com.liuheonline.la.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.liuheonline.la.entity.GameTypeClass2Entity;
import com.liuheonline.la.entity.GameTypeNewClass3Entity;
import com.liuheonline.la.entity.PaysEntity;
import com.liuheonline.la.receiver.ScreenReceiver;
import com.liuheonline.la.utils.TTSUtils;
import com.mylove.loglib.JLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.ysyy.aini.palmarliuhe.BuildConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @author: aini
 * @date 2018/7/17 15:19
 * @description application
 */
public class LiuHeApplication extends MultiDexApplication {

    public static Context context;
    public static ImageView imageView;

    public static List<GameTypeClass2Entity> getGameTypeClass2Entities() {
        return gameTypeClass2Entities;
    }

    public static void setGameTypeClass2Entities(List<GameTypeClass2Entity> gameTypeClass2Entities) {
        LiuHeApplication.gameTypeClass2Entities = gameTypeClass2Entities;
    }


    public static List<GameTypeNewClass3Entity> getGameTypeClass3Entities() {
        return gameTypeClass3Entities;
    }

    public static void setGameTypeClass3Entities(List<GameTypeNewClass3Entity> gameTypeClass3Entities) {
        LiuHeApplication.gameTypeClass3Entities = gameTypeClass3Entities;
    }

    public static List<GameTypeNewClass3Entity> gameTypeClass3Entities;
    public static List<GameTypeClass2Entity> gameTypeClass2Entities;

    public static String getQuickline() {
//        String str = quickline.length() != 0 ? quickline : "https://api20181113.6he.la";//正式服
////        String str = quickline.length() != 0 ? quickline : "https://api20180827.zs2525.com";//正式服
//        JLog.v(str);
//        return str;
        //return  "http://120.79.189.244/";//測試服
//        return "http://api20181113.6he.la";//1.4.5
        return "http://api.6he.la:1688";//1.4.6
//        return "http://47.106.177.183";//测试链接
    }

    public void setQuickline(String quickline) {
        this.quickline = quickline;
    }


    public static PaysEntity weixinpay;

    public static PaysEntity getWeixinpay() {
        return weixinpay;
    }

    public static void setWeixinpay(PaysEntity weixinpay) {
        LiuHeApplication.weixinpay = weixinpay;
    }

    public static PaysEntity getZhifubaopay() {
        return zhifubaopay;
    }

    public static void setZhifubaopay(PaysEntity zhifubaopay) {
        LiuHeApplication.zhifubaopay = zhifubaopay;
    }

    public static PaysEntity zhifubaopay;
    public static String quickline = "";
    float linetime = 1000;

    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            // do some work here
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        JLog.init(BuildConfig.LOG_DEBUG);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
//        CrashReport.initCrashReport(getApplicationContext(), "de9e0899c2", false);
        CrashReport.initCrashReport(getApplicationContext(), "64bf396913", false);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(LiuHeApplication.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
                Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());

        /*//保活方案一 前台notifycation
        startService(new Intent(this, GrayService.class));
        //        //保活方案二 1dp保活
        //        registerReceiver();*/
                //        // 对Snake进行初始化

                SpeechUtility.createUtility(LiuHeApplication.this, "appid=" + "5b5ae727");//=号后面写自己应用的APPID
                Setting.setShowLog(true); //设置日志开关（默认为true），设置成false时关闭语音云SDK日志打印
                TTSUtils.getInstance().init(); //初始化工具类
//                setline();
            }
        }).start();
        JLog.w("Application弹窗onCreate");
//        imageView = new ImageView(this);
//        imageView.setImageResource(R.mipmap.hongbao);
//        FloatWindow.with(LiuHeApplication.context)
//                .setView(imageView)
//                .setWidth(Screen.width, 0.2f)
//                .setHeight(Screen.width, 0.15f)
//                .setX(Screen.width, 0.85f)
//                .setY(Screen.height, 0.5f)
//                .setMoveType(MoveType.active)
////                .setMoveType(MoveType.active, -1, -1)
//                .setFilter(true, MainActivity.class)
//                .setMoveStyle(500, new BounceInterpolator())
//                .setDesktopShow(false)
//                .build();
    }

    private void setline() {
        String[] urls = new String[]{
                "api20180827.zs2525.com",
                "api20180827.zs2626.com",
                "api20180827.zs2727.com",
                "api20180827.zs3636.com",
                "api20180827.zs5757.com",
                "api20180827.zs6767.com"
        };
        for (int i = 0; i < urls.length; i++) {
            BufferedReader br = null;
            try {
                Process p = Runtime.getRuntime().exec("ping -c 1 -w 2 " + urls[i]);
                br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                if (sb.length() > 0) {
                    System.out.println(sb.toString());
                    String tims = sb.substring(sb.lastIndexOf("time=") + 5, sb.lastIndexOf("ms---") - 1);
                    float times = Float.parseFloat(tims);
                    if (times < linetime) {
                        linetime = times;
                        setQuickline("https://" + urls[i]);
                    }
                    System.out.println(tims);
                    System.out.println(tims.length());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new ScreenReceiver(), intentFilter);
    }
}

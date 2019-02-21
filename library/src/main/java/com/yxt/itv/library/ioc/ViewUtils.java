package com.yxt.itv.library.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by JhoneLee on 2017/10/9.
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    private static void injectFiled(ViewFinder finder, Object object) {

        // 获取类的所有属性  反射
        Class<?> clazz = object.getClass();
        Field[] f = clazz.getDeclaredFields();
        //获取viewbyid 里面的value值
        for (Field field : f) {
            BindId byid = field.getAnnotation(BindId.class);
            if (byid != null) {
                int viewid = byid.value();
                View view = finder.findViewByid(viewid);
                //能够注入所有修饰符，
                field.setAccessible(true);
                try {
                    //动态注入找到view
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectEvent(ViewFinder finder, Object object) {

        Class<?> clazz = object.getClass();
        //获取类所有的方法    // 只能获取公共方法clazz.getMethods();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            //获取OnClick 里面的value值
            OnClick onclik = m.getAnnotation(OnClick.class);
            if (onclik!=null){
                int[] viewids = onclik.value();
                if (viewids != null) {
                    for (int viewId : viewids) {
                        //找到view
                        View view = finder.findViewByid(viewId);
                        String netErrorMsg = "";
                        boolean isCheckNet = false;
                        CheckNet checkNet = m.getAnnotation(CheckNet.class);
                        if (checkNet!=null){
                            isCheckNet = true;
                            netErrorMsg = checkNet.errorMsg();
                        }
                        if (view != null) {
                            view.setOnClickListener(new DeclearedOnClickListener(m, object, isCheckNet,netErrorMsg));
                        }
                    }
                }
            }
        }
    }
    //点击事件监听类
    private static class DeclearedOnClickListener implements View.OnClickListener {

        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;
        private String mNetErrorMsg;
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private static final int MIN_CLICK_DELAY_TIME = 500;
        private static long lastClickTime;
     /*   public DeclearedOnClickListener(Method mMethod, Object mObject) {
            this.mMethod = mMethod;
            this.mObject = mObject;
        }*/

        public DeclearedOnClickListener(Method mMethod, Object mObject, boolean isCheckNet,String netErrorMsg) {
            this.mMethod = mMethod;
            this.mObject = mObject;
            this.mIsCheckNet = isCheckNet;
            this.mNetErrorMsg = netErrorMsg;
        }

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if (mIsCheckNet){
                if (!networkAvailable(view.getContext())){
                    //配置 网络问题！！！！ 这句话.....
                    Toast.makeText(view.getContext(),mNetErrorMsg,Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                mMethod.setAccessible(true);
                try {
                    mMethod.invoke(mObject, view);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        mMethod.invoke(mObject, new  Object[]{});
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            //}
        }
    }

    private static boolean networkAvailable(Context context) {
        try {
            //BaseDexClassLoader
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

package com.yxt.itv.library.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JhoneLee on 2017/10/19.
 *
 * 处理权限请求的工具类
 */

public class PermissionUtils {

    private PermissionUtils(){
        //不能被实例化对象
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断是否是6.0以上的版本
     * Marshmallow 棉花糖
     * @return
     */
    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT>= Build.VERSION_CODES.M;
    }

    /**注解是放射时的一个标记
     * 执行成功方法
     * @param object
     * @param requestCode
     */
    public static void executeSucceedMethod(Object object, int requestCode) {
        //获取clazz中所有方法
        Method[] methods=object.getClass().getDeclaredMethods();
        //遍历找到我们打标记的方法
        for (Method method : methods){
            PermissionSucceed ps = method.getAnnotation(PermissionSucceed.class);
            if (ps!=null){
                //代表该方法有标记
                int code = ps.requeseCode();
                //找到code 请求码 必须和 requestCode一致
                if (code == requestCode){
                    //该方法就是要执行的方法
                    executeMethod(object,method);
                }
            }
        }
    }

    /**
     * 反射执行该方法
     * @param object
     * @param method
     */
    private static void executeMethod(Object object, Method method) {
        //反射执行方法。 两个参数  第一个是传该方法属于哪个类，  第二个参数是传参数
        try {
            method.setAccessible(true);//允许执行私有方法
            method.invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取没有授予的权限
     * @param object  activity or fragment
     * @param requestPermissions
     * @return 没有授予过的权限
     */
    public static List<String> getDeniedPermissions(Object object, String[] requestPermissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String requestPermission:requestPermissions){
            //把没有授予的权限加入到集合中
            if (ContextCompat.checkSelfPermission(getActivity(object),requestPermission)== PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(requestPermission);
            }
        }
       return deniedPermissions;
    }

    /**
     * 获取activity
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Activity){
            return (Activity) object;
        }
        if (object instanceof Fragment){
            return ((Fragment) object).getActivity();
        }
        return null;
    }

    /**
     * 执行失败的方法
     * @param object
     * @param requestCode
     */
    public static void executeFailMothod(Object object, int requestCode) {

        //获取clazz中所有方法
        Method[] methods=object.getClass().getDeclaredMethods();
        //遍历找到我们打标记的方法
        for (Method method : methods){
            PermissionFail pf = method.getAnnotation(PermissionFail.class);
            if (pf!=null){
                //代表该方法有标记
                int code = pf.requeseCode();
                //找到code 请求码 必须和 requestCode一致
                if (code == requestCode){
                    //该方法就是要执行的方法
                    executeMethod(object,method);
                }
            }
        }
    }
}

package com.yxt.itv.library.permission;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by JhoneLee on 2017/10/19.
 * 权限申请帮助类
 */

public class PermissionHelper {

    //传什么参数，已什么方式传参，
    //1 object fragment or activity  2. int 请求码 3.需要的权限 string[]
    private Object mObject;
    private int mRequestCode;
    private String[] mRequestPermissions;

    private PermissionHelper(Object object) {
        this.mObject = object;
    }

    //2  直接传递参数
    public static void requestPermission(Activity activity,int requestCode,String... requestPermissions){
        with(activity)
                .requestCode(requestCode)
                .requestPermission(requestPermissions)
                .request();
    }
    //2  直接传递参数
    public static void requestPermission(Fragment fragment,int requestCode,String... requestPermissions){
        with(fragment)
                .requestCode(requestCode)
                .requestPermission(requestPermissions)
                .request();
    }
    //链式的方式传
    public static PermissionHelper with(Activity activity){
        return new PermissionHelper(activity);
    }
    public static PermissionHelper with(Fragment fragment){
        return new PermissionHelper(fragment);
    }
    //添加请求的code值
    public PermissionHelper requestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }
    //添加请求的权限数组
    public PermissionHelper requestPermission(String... requestPermissions){
        this.mRequestPermissions = requestPermissions;
        return this;
    }

    /**
     * 真正判断和发起请求的方法
     */
    public void request(){
        //1.判断版本是否是6.0以上.....
        if (!PermissionUtils.isOverMarshmallow()){
            //执行什么方法 并不确定，那么只有才用注解的方式给方法打标记，通过反射去执行
            PermissionUtils.executeSucceedMethod(mObject,mRequestCode);
            return;
        }

        //2.如果不是6.0以上，那么直接执行方法（反射执行）
          //需要申请权限中，获取没有授予过的权限

        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mObject,mRequestPermissions);
        if (deniedPermissions.size()==0){
            //3.  6.0以上，判断是否权限授予
            // 授予
            PermissionUtils.executeSucceedMethod(mObject,mRequestCode);
            return;
        }else {
            //不授予 就申请权限
            ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject)
                    ,deniedPermissions.toArray(new String[deniedPermissions.size()])
                    ,mRequestCode);
        }
    }

    /**
     * 权限的回调
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissionsResult(Object object,int requestCode, String[] permissions) {
        //再次没有授予过的权限
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(object,permissions);
        if(deniedPermissions.size()==0){
            //代表权限用户都同意 授予
            PermissionUtils.executeSucceedMethod(object,requestCode);
        }else {
            //代表用户拒绝了  申请权限中不允许的。。。
            PermissionUtils.executeFailMothod(object,requestCode);
        }
    }
}

package com.liuheonline.la.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedperfencesUtil {

    private SharedperfencesUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key) {

        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        if (prefences!=null){
            return prefences.getString(key, "");
        }else {
            return "";
        }

    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return prefences.getInt(key, 0);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return prefences.getBoolean(key, false);
    }
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences prefences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return prefences.getBoolean(key, defValue);
    }
}


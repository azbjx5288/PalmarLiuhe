package com.yxt.itv.library.db;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/24
 * @Version 1.0
 * @Description
 */
public class DaoUtil {

    public static String getTableName(Class clazz){
        return clazz.getSimpleName();
    }
    public static String getTypeValue(String type){
        String value = null;
        if (type.contains("int")){
            value = " integer";
        } else if (type.contains("String")){
            value = " text";
        }else if (type.contains("byte[]")){
            value = " blob ";
        }else if (type.contains("boolean")){
            value = " integer ";
        } else{
            value =new StringBuffer().append(" ").append(type).toString();
        }
        return value;
    }

    public static String capitalize(String name) {
        String type = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  type;
    }

}

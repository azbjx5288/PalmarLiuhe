package com.yxt.itv.library.db.curd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.yxt.itv.library.db.DaoUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/24
 * @Version 1.0
 * @Description  查询支持类
 */
public class QuerySupport<T> {

    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;
    //查询的列
    private String[] mQueyColumns;
    //查询的条件
    private String mQuerySelection;
    //查询的参数
    private String[] mQuerySelectionArgs;

    //查询分组
    private String mQueryGroupBy;
    //查询排序
    private String mQueryOrderBy;
    //查询对结果集过滤
    private String mQueryHaving;
    //查询用于分页
    private String mQueryLimit;

    public QuerySupport(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
    }

    public QuerySupport setColumn(String... column) {
        this.mQueyColumns = column;
        return this;
    }

    public QuerySupport setSelection(String selection) {
        this.mQuerySelection = selection;
        return this;
    }
    public QuerySupport setArgs(String... args) {
        this.mQuerySelectionArgs = args;
        return this;
    }

    public QuerySupport setGroupBy(String groupBy) {
        this.mQueryGroupBy = groupBy;
        return this;
    }

    public QuerySupport setOrderBy(String orderBy) {
        this.mQueryOrderBy = orderBy;
        return this;
    }

    public QuerySupport setHaving(String having) {
        this.mQueryHaving = having;
        return this;
    }
    public QuerySupport setLimit(String limit) {
        this.mQueryLimit = limit;
        return this;
    }



    public List<T> query() {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), mQueyColumns, mQuerySelection, mQuerySelectionArgs, mQueryGroupBy, mQueryHaving, mQueryOrderBy, mQueryLimit);
        clearQueryParams();
        return cursorToList(cursor);
    }


    public List<T> queryAll() {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    private List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    //反射 new 对象
                    T instance = mClazz.newInstance();
                    Field[] fields = mClazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        int index = cursor.getColumnIndex(name);
                        if (index == -1) {
                            continue;
                        }
                        if(field.getType().getSimpleName().contains("List")){
                            continue;
                        }
                        Method cursorMethod = cursorMethod(field.getType());

                        if (cursorMethod != null) {
                            Object value = cursorMethod.invoke(cursor, index);
                            if (value == null) continue;
                            field.set(instance, value);//反射注入生成对象
                        }
                    }
                    list.add(instance); //对象添加到list
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return list;
    }

    private Method cursorMethod(Class<?> type) throws NoSuchMethodException {
        String methodName = getColumnMethodName(type);
        if (methodName.equals("getCreator")){
            return null;
        }
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getData".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }else if("getbyte[]".equals(methodName)){
            methodName = "getBlob";
        }
        return methodName;
    }
    private void clearQueryParams() {
        mQueyColumns = null;
        mQuerySelection = null;
        mQuerySelectionArgs = null;
        mQueryGroupBy = null;
        mQueryOrderBy = null;
        mQueryHaving = null;
        mQueryLimit = null;
    }
}

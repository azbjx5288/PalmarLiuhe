package com.yxt.itv.library.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;

import com.yxt.itv.library.db.curd.QuerySupport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/24
 * @Version 1.0
 * @Description
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DaoSupport<T> implements IDaoSoupport<T> {


    //持有外部数据库引用
    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;

    private QuerySupport<T> mQuerySupport;
    private static final  Object[]  mPutMethodArgs = new Object[2];
    //缓存方法
    private static final Map<String,Method> mPutMethods = new ArrayMap<>();
    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mClazz = clazz;
        this.mSqLiteDatabase = sqLiteDatabase;

        //创建表
        StringBuffer sb = new StringBuffer();

        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append("(id integer primary key autoincrement,");

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isSynthetic()) { //判断运行时生成的字段
                continue;
            }
                String name = field.getName();
                String type = field.getType().getSimpleName();//int String boolean
            //type 需要转换  int 要转成 integer  String  text
            if(type.contains("List")){
                Log.d("SQL创建","跳过一次");
               continue;
            }
            sb.append(name).append(DaoUtil.getTypeValue(type)).append(", ");
            }
        sb.replace(sb.length() - 2, sb.length(), ")");
        String sql = sb.toString();
        mSqLiteDatabase.execSQL(sql);
    }

    //插入数据库  任意对象
    @Override
    public long insert(T t) {

        //原生的使用方式，只是封装了
        ContentValues values = contentValuesByObj(t);
        //null 速度比第三方快。
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    @Override
    public void insert(List<T> data) {
        //批量插入采用事务
        mSqLiteDatabase.beginTransaction();
        for (T t : data) {
            insert(t);
        }
        mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();
    }


    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport==null){
            mQuerySupport = new QuerySupport<>(mSqLiteDatabase,mClazz);
        }
        return mQuerySupport;
    }
    //反射得到contentValues
    private ContentValues contentValuesByObj(T t) {

        ContentValues values = new ContentValues();
        //封装values;
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                //put 的第二个参数必须指定类型
                //设置权限  ，私有和公有都可以访问
                field.setAccessible(true);
                String key = field.getName();
                Object obj = field.get(t);

                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = obj;
                //反射确定类型  反射在一定程度上影响性能  、、 源码中 activity 实例，view创建 反射
                //感谢谷歌的提供的源码  缓存方法
                if (obj==null || field.getType().getSimpleName().equals("List")){
                   continue;
                }
                Method putMethod = mPutMethods.get(obj.getClass().getName());
                if (putMethod==null){
                     putMethod = ContentValues.class.getDeclaredMethod("put", String.class, obj.getClass());
                     mPutMethods.put(obj.getClass().getName(),putMethod);
                }
                putMethod.invoke(values, key, obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
            }
        }
        return values;
    }
    /**
    * 删除
    */
    @Override
    public void delete(String whereClause,String... whereArgs){
        mSqLiteDatabase.delete(DaoUtil.getTableName(mClazz),whereClause,whereArgs);
    }

    /**
     * 清空表数据
     */
    @Override
    public void clean(){
        mSqLiteDatabase.execSQL("DELETE FROM "+DaoUtil.getTableName(mClazz));
    }
    /**
     *  更新
     */
    @Override
    public int update(T t,String whereCause,String... whereArgs){
        ContentValues contentValues = contentValuesByObj(t);
        return mSqLiteDatabase.update(DaoUtil.getTableName(mClazz),contentValues,whereCause,whereArgs);
    }
}

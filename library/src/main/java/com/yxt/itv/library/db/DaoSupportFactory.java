package com.yxt.itv.library.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/24
 * @Version 1.0
 * @Description 数据库工厂类
 * 数据存在内存卡中，有时候放在data/data/xxx/database
 * 获取factory不一样 写入位置不懂
 * 面向接口编程，不需要关系实现， 为了扩展性强
 */
public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    //持有外部数据库引用
    private SQLiteDatabase mSqLiteDatabase;

    private DaoSupportFactory(String dbpackage) {

        //把数据库放在内存卡里面  判断是否有存储卡，  6.0 申请权限
        File dbroot = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + dbpackage + File.separator + "database");
        //打开或者创建数据库
        if (!dbroot.exists()) {
            dbroot.mkdirs();
        }
        File dbFile = new File(dbroot, new StringBuffer().append(dbpackage).append(".db").toString());
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DaoSupportFactory getFactory(String dbpackage) {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory(dbpackage);
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSoupport<T> getDao(Class<T> clazz) {
        IDaoSoupport<T> daoSoupport = new DaoSupport();
        daoSoupport.init(mSqLiteDatabase, clazz);
        return daoSoupport;
    }
}

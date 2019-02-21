package com.yxt.itv.library.db;

import android.database.sqlite.SQLiteDatabase;


import com.yxt.itv.library.db.curd.QuerySupport;

import java.util.List;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/24
 * @Version 1.0
 * @Description
 */
public interface IDaoSoupport<T> {

    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);
    long insert(T t);
    //批量插入  检查性能
    void insert(List<T> data);

  //  List<T> query();
    //按照语句查询
   QuerySupport<T> querySupport();

   void delete(String whereClause, String... whereArgs);

   int update(T t, String whereCause, String... whereArgs);

   void clean();
}

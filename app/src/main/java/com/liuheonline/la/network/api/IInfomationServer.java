package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.InfoDetailEntity;
import com.liuheonline.la.entity.InfoImagesEntity;
import com.liuheonline.la.entity.InfomationClassEntity;
import com.liuheonline.la.entity.InfomationEntity;
import com.liuheonline.la.entity.TheoryEntity;
import com.liuheonline.la.entity.XuanjiLaji;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IInfomationServer {

    /**
     * @param
     * @return
     * @description 获取资料列表
     */
    @GET("/api/information/informationclass")
    Observable<BaseEntity<List<InfomationClassEntity>>> getInfomationClass();

    /**
     * @param
     * @return
     * @description 获取资料列表
     */
    @GET("/api/information")
    Observable<BaseEntity<List<InfomationEntity>>> getInfomation(@Query(value = "pid") int pid);

    /**
     * @param
     * @return
     * @description 获取资料详情
     */
    @GET("/api/information/{id}")
    Observable<BaseEntity<InfoDetailEntity>> getInfomationDetail(@Path("id") int id);

    /**
     * @param
     * @return
     * @description 获取信息fragment中图片列表
     */
    @GET()
    Observable<ArrayList<InfoImagesEntity>> getInfomationList(@Url String url);

    /**
     * @param
     * @return
     * @description 获取玄机信息
     */
    @GET("/api/com/tips")
    Observable<BaseEntity<TheoryEntity>> getTheory();

    /**
     * @param
     * @return
     * @description 获取玄机集合
     */
    @GET("/api/com/jinglang")
    Observable<BaseEntity<XuanjiLaji>> getXuanjiList(@Query(value = "year") int year, @Query(value = "page") int page, @Query(value = "pageSize") int pageSize);
}

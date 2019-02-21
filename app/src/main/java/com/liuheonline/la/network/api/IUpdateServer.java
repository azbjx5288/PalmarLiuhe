package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.DownLoadAPkEntity;
import com.liuheonline.la.entity.UpdateEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IUpdateServer {

    @GET("/api/com/version")
    Observable<BaseEntity<List<UpdateEntity>>> getUpdate(@QueryMap Map<String, Object> map);


    @GET("/api/com/app")
    Observable<BaseEntity<List<DownLoadAPkEntity>>> getdownLoad();
}

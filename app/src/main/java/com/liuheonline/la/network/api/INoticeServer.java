package com.liuheonline.la.network.api;



import com.liuheonline.la.entity.NoticeEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface INoticeServer {

    //获取公告列表
    @GET("/api/announcement")
    Observable<BaseEntity<List<NoticeEntity>>> getNotice(@QueryMap Map<String, Object> map);


}


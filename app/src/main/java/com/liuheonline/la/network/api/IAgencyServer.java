package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.AgencyPriceEntity;
import com.liuheonline.la.entity.AgencyTableEntity;
import com.liuheonline.la.entity.AgentListEntity;
import com.liuheonline.la.entity.MemberInfoEntity;
import com.liuheonline.la.entity.NoddEntity;
import com.liuheonline.la.entity.PriceMemberEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IAgencyServer {

    //新增成员接口
    @FormUrlEncoded
    @POST("/api/agent")
    Observable<BaseEntity<Object>> addMember(@FieldMap Map<String,Object> params);

    //成员详情接口
    @GET("/api/agent/{id}")
    Observable<BaseEntity<AgencyTableEntity>> getAgentInfo(@Path("id") int id,@QueryMap Map<String, Object> map);

    //成员列表接口
    @GET("/api/agent")
    Observable<BaseEntity<List<AgentListEntity>>> getAgentList(@QueryMap Map<String, Object> map);

    //成员报表接口
    @GET("/api/agent/member")
    Observable<BaseEntity<MemberInfoEntity>> getMember(@QueryMap Map<String,Object> params);

    //彩票返点列表接口
    @GET("/api/agent/nodd")
    Observable<BaseEntity<NoddEntity>> getNodd();

    //编辑成员返点
    @FormUrlEncoded
    @PUT("/api/agent/{id}")
    Observable<BaseEntity<Object>> updateMember(@Path("id") int id,@FieldMap Map<String,Object> params);


    //代理佣金列表接口
    @GET("/api/agent/commission")
    Observable<BaseEntity<AgencyPriceEntity>> getPriceList(@QueryMap Map<String, Object> map);

    //代理佣金成员列表接口
    @GET("/api/agent/member")
    Observable<BaseEntity<List<PriceMemberEntity>>> getPriceMemberList(@QueryMap Map<String, Object> map);
}

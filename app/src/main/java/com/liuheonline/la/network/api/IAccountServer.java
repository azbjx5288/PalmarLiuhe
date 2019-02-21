package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.BankCardEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IAccountServer {

    //新增银行卡
    @FormUrlEncoded
    @POST("/api/memberbankcard")
    Observable<BaseEntity<Object>> addCard(@FieldMap Map<String,Object> params);

    //银行卡验证手机接口
    @FormUrlEncoded
    @POST("/api/memberbankcard/bankcardvalidation")
    Observable<BaseEntity<Object>> verifyNumber(@FieldMap Map<String,Object> params);

    //银行卡列表接口
    @GET("/api/memberbankcard")
    Observable<BaseEntity<List<BankCardEntity>>> getCardList(@QueryMap Map<String, Object> map);

    //删除银行卡接口
    @DELETE("/api/memberbankcard/{id}")
    Observable<BaseEntity<Object>> deleteCard(@Path(value = "id")int id,@Query(value = "paypwd")String pwd);

}

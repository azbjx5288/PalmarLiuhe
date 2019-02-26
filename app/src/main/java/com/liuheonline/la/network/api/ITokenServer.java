package com.liuheonline.la.network.api;


import com.liuheonline.la.entity.InviteNumEntity;
import com.liuheonline.la.entity.TokenEntity;
import com.liuheonline.la.entity.WebEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ITokenServer {
    @FormUrlEncoded
    @POST("/api/com/auth")
    Observable<BaseEntity<TokenEntity>> getToken(@FieldMap Map<String, Object> map);

    /**
     * @param type 发送类型 name 手机、邮箱
     * @return 验证码
     * @description 发送短信
     */
    @FormUrlEncoded
    @POST("/api/code")
    Observable<BaseEntity<Integer>> sendMsg(@Query(value = "type") String type, @Query(value = "name") String name);


    //获取图形验证码
    @POST("/api/com/checkcode")
    Observable<BaseEntity<Object>> getImg();


    /**
     * @param
     * @return 网站地址列表
     * @description 获取网站配置
     */
    @POST("/api/com/web")
    Observable<BaseEntity<WebEntity>> getWeb();


    @POST("/api/Egurl")
    Observable<BaseEntity<String>> getEgurl();

    //获取邀请人数

    /**
     * @return
     */
    @POST("/api/member/invite_num")
    Observable<BaseEntity<InviteNumEntity>> getInviteNum();
}


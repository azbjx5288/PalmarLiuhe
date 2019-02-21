package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.BetNumberEntity;
import com.liuheonline.la.entity.GameTypeClass2Entity;
import com.liuheonline.la.entity.GameTypeClass3Entity;
import com.liuheonline.la.entity.GameTypeNewClass3Entity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: aini
 * @date 2018/7/26 11:49
 * @description 购买六合彩
 */
public interface IBetLotteryServer {

    /**
     * @description 获取玩法分类列表
     * @param sid 彩票种类
     * @return gametypeclass 玩法列表
     */
    @GET("/api/lottery/lotteryclass")
    Observable<BaseEntity<List<GameTypeClass2Entity>>> getGameTypeClass(@Query(value = "sid")int sid);

    /**
     * @description 获取玩法分类列表
     * @param sid 彩票种类
     * @return gametypeclass 玩法列表
     */
    @GET("/api/lottery/lotteryclass")
    Observable<BaseEntity<List<GameTypeClass3Entity>>> getGameTypeClass3(@Query(value = "sid")int sid);
    /**
     * @description 获取玩法分类列表
     * @param sid 彩票种类
     * @return gametypeclass 玩法列表
     */
    @GET("/api/lottery/lotteryclass")
    Observable<BaseEntity<List<GameTypeNewClass3Entity>>> getGameTypeNewClass3(@Query(value = "sid")int sid);


    /**
     * @description 获取玩法分类列表
     * @param pid 玩法ID
     * @return BetNumberEntity 投注列表
     */
    @GET("/api/lottery/lottery")
    Observable<BaseEntity<List<BetNumberEntity>>> betNumber(@Query(value = "pid")int pid,@Query(value = "row")int row);

    /**
     * @description 提交购彩订单
     * @param
     * @return void
     */
    @FormUrlEncoded
    @POST("api/lottery/buy")
    Observable<BaseEntity<Object>> buyNumber(@FieldMap Map<String,Object> params);
}

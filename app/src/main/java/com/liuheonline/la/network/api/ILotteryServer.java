package com.liuheonline.la.network.api;



import com.liuheonline.la.entity.AddEntity;
import com.liuheonline.la.entity.ColorEntity;
import com.liuheonline.la.entity.ColorStatisticsEntity;
import com.liuheonline.la.entity.FiveElementsEntity;
import com.liuheonline.la.entity.FooterEntity;
import com.liuheonline.la.entity.HeadageEntity;
import com.liuheonline.la.entity.LotteryCategoryEntity;
import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.entity.NumStatisticsEntity;
import com.liuheonline.la.entity.OdevityEntity;
import com.liuheonline.la.entity.PropertiyEntity;
import com.liuheonline.la.entity.RulesEntity;
import com.liuheonline.la.entity.SizeEntity;
import com.liuheonline.la.entity.SpeciesclasstypeEntity;
import com.liuheonline.la.entity.TailStatisticsEntity;
import com.liuheonline.la.entity.TrendMapEntity;
import com.liuheonline.la.entity.ZodiacStatisticsEntity;
import com.liuheonline.la.entity.ZodiacTrendEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ILotteryServer {


    /**
     * @description 获取最新开奖号码
     * @param
     * @return LotteryEntity
     */
    @GET("/api/index")
    Observable<Response<BaseEntity<LotteryEntity>>> getLottery();

    /**
     * @description 历史开奖
     * @param params year:年份  p:页码 row:条数
     * @return userinfo 用户信息
     */
    @GET("/api/lottery")
    Observable<BaseEntity<List<LotteryEntity.LotteryBean>>> historyLottert(@QueryMap Map<String,Object> params);

    /**
     * @description 生肖走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getZodiac")
    Observable<BaseEntity<List<ZodiacTrendEntity>>> getZodiacTrend(@QueryMap Map<String,Object> params);


    /**
     * @description 头数走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getHead")
    Observable<BaseEntity<List<HeadageEntity>>> getHeadage(@QueryMap Map<String,Object> params);

    /**
     * @description 尾数走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getFooter")
    Observable<BaseEntity<List<FooterEntity>>> getFooter(@QueryMap Map<String,Object> params);

    /**
     * @description 单双走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getOdevity")
    Observable<BaseEntity<List<OdevityEntity>>> getOdevity(@QueryMap Map<String,Object> params);

    /**
     * @description 大小走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getSize")
    Observable<BaseEntity<List<SizeEntity>>> getSize(@QueryMap Map<String,Object> params);

    /**
     * @description 五行走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getFiveElements")
    Observable<BaseEntity<List<FiveElementsEntity>>> getFiveElements(@QueryMap Map<String,Object> params);

    /**
     * @description 波色走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getColor")
    Observable<BaseEntity<List<ColorEntity>>> getColor(@QueryMap Map<String,Object> params);

    /**
     * @description 合数走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getAdd")
    Observable<BaseEntity<List<AddEntity>>> getAdd(@QueryMap Map<String,Object> params);

    /**
     * @description 热图走势
     * @param params year:年份  p:页码 row:条数
     * @return
     */
    @GET("/api/trend/getTrendMap")
    Observable<BaseEntity<List<TrendMapEntity>>> getTrendMap(@QueryMap Map<String,Object> params);

    /**
     * @description 号码统计
     * @param
     * @return
     */
    @GET("/api/statistical")
    Observable<BaseEntity<NumStatisticsEntity>> getNumStatistics(@QueryMap Map<String,Object> params);

    /**
     * @description 生肖统计
     * @param
     * @return
     */
    @GET("/api/statistical/zodiac")
    Observable<BaseEntity<ZodiacStatisticsEntity>> getZodiacStatistics(@QueryMap Map<String,Object> params);

    /**
     * @description 波色统计
     * @param
     * @return
     */
    @GET("/api/statistical/wavecolor")
    Observable<BaseEntity<ColorStatisticsEntity>> getColorStatistics(@QueryMap Map<String,Object> params);

    /**
     * @description 头数统计
     * @param
     * @return
     */
    @GET("/api/statistical/tail")
    Observable<BaseEntity<TailStatisticsEntity>> getTailStatistics(@QueryMap Map<String,Object> params);

    /**
     * @description 彩票属性
     * @param
     * @return
     */

    @GET("/api/attribute")
    Observable<BaseEntity<List<PropertiyEntity>>> getProperty();

    /**
     * @description 彩票类型
     * @param
     * @return
     */
    @GET("/api/attribute/species")
    Observable<Response<BaseEntity<List<LotteryCategoryEntity>>>> getLotteryCategory(@Query(value = "tid")int tid);


    /**
     * @description 获取最新开奖号码
     * @param
     * @return LotteryEntity
     */
    @GET("/api/com/lottery")
    Observable<Response<BaseEntity<LotteryEntity>>> getSidLottery(@Query(value = "sid")int sid);

    /**
     * @description 彩种分类接口
     * @param
     * @return LotteryEntity
     */
    @GET("/api/lottery/speciesclasstype")
    Observable<BaseEntity<List<SpeciesclasstypeEntity>>> getSpeciesclasstype();

    //彩票种类详情接口
    @GET("/api/attribute/{id}")
    Observable<BaseEntity<RulesEntity>> getRules(@Path("id") int id);

}


package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.BankCard1Entity;
import com.liuheonline.la.entity.BankNameEntity;
import com.liuheonline.la.entity.BetLotteryLogDetailsEntity;
import com.liuheonline.la.entity.LotteryLogDetaisEntity;
import com.liuheonline.la.entity.LotteryLogEntity;
import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.entity.PaysEntity;
import com.liuheonline.la.entity.PdCashEntity;
import com.liuheonline.la.entity.PdlogEntity;
import com.liuheonline.la.entity.PdrechargeEntity;
import com.liuheonline.la.entity.QueryEntity;
import com.liuheonline.la.entity.RechargeEntity;
import com.liuheonline.la.entity.RechargeSNEntity;
import com.liuheonline.la.entity.RedEnvelopeEntity;
import com.liuheonline.la.entity.SignDataEntity;
import com.liuheonline.la.entity.SignEntity;
import com.liuheonline.la.entity.SignRewardEntity;
import com.liuheonline.la.entity.UserInfo;
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
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description 用户相关接口
 */
public interface IUserServer {


    /**
     * @param params name:用户名  password:密码 code:验证码
     * @return userinfo 用户信息
     * @description 会员注册
     */
    @FormUrlEncoded
    @POST("/api/user/reg")
    Observable<BaseEntity<UserInfo>> register(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/api/pay/bankcard")
    Observable<BaseEntity<BankCard1Entity>> bankcard(@FieldMap Map<String, Object> params);

    /**
     * @param name 要验证的字段的值
     * @return null
     * @description //获取会员名/手机号码/邮箱等信息是否被注册
     */
    @GET("/api/ajax/mobile")
    Observable<BaseEntity<Object>> checkString(@Query(value = "name") String name);

    @GET("/api/com/getmemberagreement")
    Observable<BaseEntity<Object>> getDeal();

    /**
     * @param params loginname:用户名、邮箱、手机号 password:密码
     * @return userinfo 用户信息
     * @description 会员登陆
     */
    @FormUrlEncoded
    @POST("/api/user/login")
    Observable<BaseEntity<UserInfo>> login(@FieldMap Map<String, Object> params);


    /**
     * @param params fastname:邮箱、手机号  fastcode:验证码
     * @return userinfo 用户信息
     * @description 会员手机号快速登陆
     */
    @FormUrlEncoded
    @POST("/api/user/fast")
    Observable<BaseEntity<UserInfo>> phoneLogin(@FieldMap Map<String, Object> params);


    /**
     * @param id 会员id
     * @return userInfo
     * @description 会员详情
     */
    @GET("/api/member/{id}")
    Observable<BaseEntity<UserInfo>> getUserInfo(@Path("id") int id);

    /**
     * @param map
     * @return userInfo
     * @description 编辑会员
     */
    @FormUrlEncoded
    @PUT("/api/member/{uid}")
    Observable<BaseEntity<Object>> upUserInfo(@Path(value = "uid") int uid, @FieldMap Map<String, Object> map);

    /**
     * @param map
     * @return null
     * @description 修改密码
     */
    @FormUrlEncoded
    @POST("/api/member/pwd")
    Observable<BaseEntity<Object>> upUserPwd(@FieldMap Map<String, Object> map);

    /**
     * @param map
     * @return null
     * @description 绑定新手机
     */
    @FormUrlEncoded
    @POST("/api/member/bind")
    Observable<BaseEntity<Object>> bindPhone(@FieldMap Map<String, Object> map);


    /**
     * @param name 用户名 code 验证码
     * @return auth值
     * @description 找回密码获取auth值
     */
    @GET("/api/user/find")
    Observable<BaseEntity<String>> findAuth(@Query(value = "loginname") String name,
                                            @Query(value = "findcode") String code);


    /**
     * @param params auth 鉴权值  password 密码  repassword 重复密码
     * @return null
     * @description 找回密码
     */
    @FormUrlEncoded
    @POST("/api/user/pwd")
    Observable<BaseEntity<Object>> findPassword(@FieldMap Map<String, Object> params);


    //意见反馈
    @FormUrlEncoded
    @POST("/api/member/feedback")
    Observable<BaseEntity<Object>> feedBack(@FieldMap Map<String, Object> params);

    //会员充值接口
    @FormUrlEncoded
    @POST("/api/member/recharge")
    Observable<BaseEntity<RechargeEntity>> recharge(@FieldMap Map<String, Object> params);

    //申请提现接口
    @FormUrlEncoded
    @POST("/api/member/cash")
    Observable<BaseEntity<Object>> getcash(@FieldMap Map<String, Object> params);

    //消费记录接口
    @GET("/api/member/pdlog")
    Observable<BaseEntity<PdlogEntity>> pdlog(@QueryMap Map<String, Object> params);

    //充值记录接口
    @GET("/api/member/pdrecharge")
    Observable<BaseEntity<PdrechargeEntity>> pdrecharge(@QueryMap Map<String, Object> params);

    //提现记录接口
    @GET("/api/member/pdcash")
    Observable<BaseEntity<PdCashEntity>> pdcash(@QueryMap Map<String, Object> params);

    //投注记录
    @GET("/api/member/myBettingRecord")
    Observable<BaseEntity<List<LotteryLogEntity>>> lotteryLog(@QueryMap Map<String, Object> params);
//    Observable<BaseEntity<List<LotteryLogEntity>>> lotteryLog(@Query(value = "order_type") int otherType, @Query(value = "dui") String dui,
//                                                              @Query(value = "p") int p, @Query(value = "row") int row);

    //订单详情
    @GET("/api/member/orderDetail")
    Observable<BaseEntity<List<LotteryLogDetaisEntity>>> lotteryLogDetais(@Query(value = "order_id") int otherType,
                                                                          @Query(value = "p") int p, @Query(value = "row") int row);

    //单注订单详情
    @GET("/api/member/betLotteryDetail")
    Observable<BaseEntity<BetLotteryLogDetailsEntity>> betLotteryLogDetais(@Query(value = "order_id") int otherType);

    //取消订单
    @GET("/api/member/cancel_orders")
    Observable<BaseEntity<Object>> cancelOther(@Query(value = "order_id") int otherType);


    //获取支付接口
    @GET("/api/memberpayment")
    Observable<BaseEntity<List<PayMentEntity>>> getPayment();

   /* //请求支付平台支付接口
    @GET("/api/memberpayment/pd_pay")
    Observable<BaseEntity<List<Object>>> getNotice(@QueryMap Map<String, Object> map);*/

    //获取会员充值订单号接口
    @GET("/api/member/recharge_sn")
    Observable<BaseEntity<RechargeSNEntity>> getRechargeSN();


    //是否设置支付密码接口
    @GET("/api/member/get_paypwd_info")
    Observable<BaseEntity<Integer>> isSetPW();

    /**
     * @param map
     * @return null
     * @description 修改交易密码
     */
    @FormUrlEncoded
    @POST("/api/member/paypwd")
    Observable<BaseEntity<Object>> upPayPwd(@FieldMap Map<String, Object> map);


    //APP所支持银行卡列表接口
    @GET("/api/memberbankcard/bankcards")
    Observable<BaseEntity<List<BankNameEntity>>> getBankName();


    /**
     * @param map
     * @return null
     * @description 获取支付二维码接口
     */
    @FormUrlEncoded
    @POST("/api/pay")
    Observable<BaseEntity<PaysEntity>> getpay(@FieldMap Map<String, Object> map);

    /**
     * @param map
     * @return null
     * @description 刷星支付二维码接口
     */
    @FormUrlEncoded
    @POST("/api/pay/create")
    Observable<BaseEntity<PaysEntity>> getnewpay(@FieldMap Map<String, Object> map);


    /**
     * @param map
     * @return null
     * @description 二维码支付状态检测接口
     */
    @FormUrlEncoded
    @POST("/api/pay/query")
    Observable<BaseEntity<QueryEntity>> getquery(@FieldMap Map<String, Object> map);

    /**
     * 获取红包次数
     */
    @GET("/api/member/red_packed_num")
    Observable<BaseEntity<RedEnvelopeEntity>> getRedEnvelopeNum();

    /**
     * 抽奖金额
     */
    @GET("/api/member/red_packed_draw")
    Observable<BaseEntity<RedEnvelopeEntity>> getRedEnvelopeAmount();

    /**
     * 签到记录
     */
    @GET("/api/membersign")
    Observable<BaseEntity<List<SignDataEntity>>> getSignIn();

    /**
     * 获取签到奖励
     */
    @GET("/api/membersign/expsetting")
    Observable<BaseEntity<List<SignRewardEntity>>> getSignReward();

    /**
     * 获取签到状态
     */
    @GET("/api/membersign/sign_info")
    Observable<BaseEntity<Integer>> signStatus();

    /**
     * 签到
     */
    @GET("/api/membersign/sign")
    Observable<BaseEntity<SignEntity>> sign();
}

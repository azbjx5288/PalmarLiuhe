package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.CommentEntity;
import com.liuheonline.la.entity.ForumEntity;
import com.liuheonline.la.entity.JokerEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: aini
 * @date 2018/7/20 15:09
 * @description 论坛相关api
 */
public interface IForumServer {

    /**
     * @param uid:会员ID p:页码 row:条数
     * @return userinfo 用户信息
     * @description 论坛列表
     */
    @GET("/api/circle")
    Observable<BaseEntity<List<ForumEntity>>> getForumList(@Query(value = "p") int p,
                                                           @Query(value = "row") int row,
                                                           @Query(value = "uid") int uid);


    /**
     * @param depict:内容 imglist:图片
     * @return userinfo 用户信息
     * @description 新增论坛
     */
    @FormUrlEncoded
    @POST("/api/circle")
    Observable<BaseEntity<Object>> addForum(@Field(value = "depict") String depict,
                                            @Field(value = "imglist") String imgList);

    /**
     * @param type 1 - 论坛  2 - 评论  item_id - 论坛id or 评论id
     * @return data 1 - 点赞成功  -1 取消点赞成功
     * @description 点赞
     */
    @FormUrlEncoded
    @POST("api/agree/save")
    Observable<BaseEntity<Integer>> agree(@Field(value = "type") int type, @Field(value = "item_id") int itemId);

    /**
     * @param content 评论内容   item_id - 论坛id
     * @return data 评论数据
     * @description 新增评论
     */
    @FormUrlEncoded
    @POST("api/comment")
    Observable<BaseEntity<CommentEntity>> addComment(@Field(value = "content") String content,
                                                     @Field(value = "item_id") int itemId);

    /**
     * @param id 帖子id
     * @return null
     * @description 删除帖子
     */
    @DELETE("api/circle/{id}")
    Observable<BaseEntity<Object>> deleteForum(@Path(value = "id") int id);

    /**
     * @param pid 上级评论id  content 评论内容
     * @return data 评论数据
     * @description 回复评论
     */
    @FormUrlEncoded
    @PUT("api/comment/{pid}")
    Observable<BaseEntity<CommentEntity>> answerComment(@Path(value = "pid") int pid, @Field(value = "content") String content);


    /**
     * @param id 评论id
     * @return null
     * @description 删除评论
     */
    @DELETE("api/comment/{id}")
    Observable<BaseEntity<Object>> deleteComment(@Path(value = "id") int id);

    /**
     * @description 随机笑话
     */
    @GET("/api/article/rends")
    Observable<BaseEntity<JokerEntity>> getRandomJoker();

    /**
     * @description 笑话列表
     */
    @GET("/api/article")
    Observable<BaseEntity<List<JokerEntity>>> getJokerList(@Query(value = "p") int p,
                                                       @Query(value = "row") int row);
}

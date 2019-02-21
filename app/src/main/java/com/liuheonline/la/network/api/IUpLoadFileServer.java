package com.liuheonline.la.network.api;

import com.liuheonline.la.entity.ImageEntity;
import com.yxt.itv.library.http.retrofit.BaseEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author: aini
 * @date 2018/7/18 10:03
 * @description 公共上传图片接口
 */
public interface IUpLoadFileServer {

    /**
     * @description 上传单张图片
     * @param image 图片名 type 上传类型 默认单张 part 文件封装
     * @return url
     */
    @Multipart
    @POST("/files/Image/upload")
    Observable<BaseEntity<List<ImageEntity>>> postFile(@Part MultipartBody.Part image,
                                                       @Part("type") RequestBody type);


    /**
     * @description 上传多张图片
     * @param image 图片名 type 上传类型 默认单张 part 文件封装
     * @return url
     */
    @Multipart
    @POST("/files/Image/upload")
    Observable<BaseEntity<List<ImageEntity>>> postFiles(@Part MultipartBody.Part[] image,
                                                       @Part("type") RequestBody type);
}

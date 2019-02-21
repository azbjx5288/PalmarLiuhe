package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.ImageEntity;
import com.liuheonline.la.network.api.IUpLoadFileServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: aini
 * @date 2018/6/28 16:21
 * @description 上传多张图片
 */
public class PostFilesPresenter extends BasePresenter<BaseView<List<ImageEntity>>>{

    public void postFiles(String type,List<File> files){
        getView().onLoading();
        MultipartBody.Part[] mPart = new MultipartBody.Part[files.size()];
        for(int i = 0; i< files.size(); i++){
            File file = files.get(i);
            //将文件封装到part
            RequestBody headBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image[]", file.getName(), headBody);
            mPart[i] = part;
        }

        RequestBody requestBody = RequestBody.create(null,type);

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUpLoadFileServer.class)
                .postFiles(mPart,requestBody)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<ImageEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<ImageEntity>> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                }, () -> {
                });
    }
}

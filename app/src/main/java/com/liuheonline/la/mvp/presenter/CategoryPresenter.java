package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.LotteryCategoryEntity;
import com.liuheonline.la.network.api.ILotteryServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class CategoryPresenter extends BasePresenter<BaseView<List<LotteryCategoryEntity>>>{

    /**
     * @description 获取彩票分类
     * @param
     * @return void
     */
    public void getLotteryCategory(int tid){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getLotteryCategory(tid)
                .compose(SetThread.io_main())
                .subscribe(baseEntityResponse -> {
                    if(baseEntityResponse != null){
                        if(baseEntityResponse.body().getmCode() == 200 || baseEntityResponse.body().getmCode() == 201){
                            for(LotteryCategoryEntity lotteryCategoryEntity : baseEntityResponse.body().getmData()){
                                lotteryCategoryEntity.setHeaderTime(baseEntityResponse.headers().get("X_End_Time"));
                            }
                            getView().successed(baseEntityResponse.body().getmData());
                        }else{
                            getView().onLoadFailed(baseEntityResponse.body().getmCode(),baseEntityResponse.body().getmMessage());
                        }
                    }

                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                } , () -> {
                });
    }
}

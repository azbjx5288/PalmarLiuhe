package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.SpeciesclasstypeEntity;
import com.liuheonline.la.network.api.ILotteryServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class SpeciesclasstypePresenter extends BasePresenter<BaseView<List<SpeciesclasstypeEntity>>> {

    /**
     * @description 彩种分类接口
     * @param
     * @return void
     */
    public void getType(){

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getSpeciesclasstype()
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<SpeciesclasstypeEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<SpeciesclasstypeEntity>> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, () -> {

                });
    }
}

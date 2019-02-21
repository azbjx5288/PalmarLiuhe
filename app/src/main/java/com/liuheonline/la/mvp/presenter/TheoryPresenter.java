package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.TheoryEntity;
import com.liuheonline.la.network.api.IInfomationServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class TheoryPresenter extends BasePresenter<BaseView<TheoryEntity>> {


    public void getTheory(){

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IInfomationServer.class)
                .getTheory()
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<TheoryEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<TheoryEntity> t) throws Exception {
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

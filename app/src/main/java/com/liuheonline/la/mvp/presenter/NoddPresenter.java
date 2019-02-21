package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.NoddEntity;
import com.liuheonline.la.network.api.IAgencyServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class NoddPresenter extends BasePresenter<BaseView<NoddEntity>> {

    /**
     * @description 彩票返点列表接口
     * @param
     * @return void
     */
    public void getNodd(){

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAgencyServer.class)
                .getNodd()
                .compose(SetThread.<BaseEntity<NoddEntity>>io_main())
                .subscribe(new BaseConsumer<NoddEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<NoddEntity> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {                    @Override                    protected void onCodeError(int code, String error) throws Exception {                        getView().onLoadFailed(code, error);                    }                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}

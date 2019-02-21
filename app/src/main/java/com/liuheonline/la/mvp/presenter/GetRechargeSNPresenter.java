package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.RechargeSNEntity;
import com.liuheonline.la.network.api.IUserServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import io.reactivex.functions.Action;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class GetRechargeSNPresenter extends BasePresenter<BaseView<RechargeSNEntity>> {

    /**
     * @description 获取会员充值订单号接口
     * @param
     * @return void
     */
    public void getsn(){

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getRechargeSN()
                .compose(SetThread.<BaseEntity<RechargeSNEntity>>io_main())
                .subscribe(new BaseConsumer<RechargeSNEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<RechargeSNEntity> t) throws Exception {
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

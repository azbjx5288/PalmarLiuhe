package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.PayMentEntity;
import com.liuheonline.la.network.api.IUserServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class PayMentPresenter extends BasePresenter<BaseView<List<PayMentEntity>>> {

    /**
     * @description 添加银行卡
     * @param
     * @return void
     */
    public void getPayment(){

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getPayment()
                .compose(SetThread.<BaseEntity<List<PayMentEntity>>>io_main())
                .subscribe(new BaseConsumer<List<PayMentEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<PayMentEntity>> t) throws Exception {
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
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}

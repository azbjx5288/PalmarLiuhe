package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.InfoDetailEntity;
import com.liuheonline.la.network.api.IInfomationServer;
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
public class InfomationDetailPresenter extends BasePresenter<BaseView<InfoDetailEntity>> {

    /**
     * @description 获取资料详情
     * @param
     * @return void
     */
    public void getInfomationDetail(int id){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IInfomationServer.class)
                .getInfomationDetail(id)
                .compose(SetThread.<BaseEntity<InfoDetailEntity>>io_main())
                .subscribe(new BaseConsumer<InfoDetailEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<InfoDetailEntity> t) throws Exception {
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

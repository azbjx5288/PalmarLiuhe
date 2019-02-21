package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.network.api.IUserServer;
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
 * @date 2018/7/17 11:26
 * @description 找回密码获取auth接口
 */
public class FindAuthPresenter extends BasePresenter<BaseView<String>>{

    public void findAuth(String loginname,String findcode){
        getView().onLoading();

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .findAuth(loginname, findcode)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<String>() {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
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

package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.SignDataEntity;
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

/**
 * @author BenYanYi
 * @date 2018/12/13 16:05
 * @email ben@yanyi.red
 * @overview
 */
public class SignInPresenter extends BasePresenter<BaseView<List<SignDataEntity>>> {
    public void getData() {
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getSignIn()
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<SignDataEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<SignDataEntity>> t) throws Exception {
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

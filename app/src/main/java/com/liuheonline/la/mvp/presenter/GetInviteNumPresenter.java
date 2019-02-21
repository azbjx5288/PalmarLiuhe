package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.InviteNumEntity;
import com.liuheonline.la.network.api.ITokenServer;
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
 * @description 获取邀请人数
 */
public class GetInviteNumPresenter extends BasePresenter<BaseView<InviteNumEntity>> {

    public void getNum() {
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ITokenServer.class)
                .getInviteNum()
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<InviteNumEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<InviteNumEntity> t) throws Exception {
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

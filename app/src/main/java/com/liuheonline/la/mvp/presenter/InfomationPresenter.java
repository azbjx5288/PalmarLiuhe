package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.InfomationEntity;
import com.liuheonline.la.network.api.IInfomationServer;
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
public class InfomationPresenter extends BasePresenter<BaseView<List<InfomationEntity>>> {

    /**
     * @description 获取资料列表
     * @param
     * @return void
     */
    public void getInfomation(int pid){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IInfomationServer.class)
                .getInfomation(pid)
                .compose(SetThread.<BaseEntity<List<InfomationEntity>>>io_main())
                .subscribe(new BaseConsumer<List<InfomationEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<InfomationEntity>> t) throws Exception {
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

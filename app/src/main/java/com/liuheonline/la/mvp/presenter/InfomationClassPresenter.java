package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.InfomationClassEntity;
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
public class InfomationClassPresenter extends BasePresenter<BaseView<List<InfomationClassEntity>>> {

    /**
     * @description 获取资料类型
     * @param
     * @return void
     */
    public void getInfomationCLass(){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IInfomationServer.class)
                .getInfomationClass()
                .compose(SetThread.<BaseEntity<List<InfomationClassEntity>>>io_main())
                .subscribe(new BaseConsumer<List<InfomationClassEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<InfomationClassEntity>> t) throws Exception {
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

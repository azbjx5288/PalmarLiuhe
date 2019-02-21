package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.PropertiyEntity;
import com.liuheonline.la.network.api.ILotteryServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;

import io.reactivex.functions.Action;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class PropertyPresenter extends BasePresenter<BaseView<List<PropertiyEntity>>> {

    /**
     * @description 获取波色走势
     * @param
     * @return void
     */
    public void getProperty(){

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getProperty()
                .compose(SetThread.<BaseEntity<List<PropertiyEntity>>>io_main())
                .subscribe(new BaseConsumer<List<PropertiyEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<PropertiyEntity>> t) throws Exception {
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

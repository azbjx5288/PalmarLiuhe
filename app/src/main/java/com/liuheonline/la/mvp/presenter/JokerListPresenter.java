package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.JokerEntity;
import com.liuheonline.la.network.api.IForumServer;
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
 * @author: aini
 * @date 2018/7/17 11:26
 * @description 笑话列表列表
 */
public class JokerListPresenter extends BasePresenter<BaseView<List<JokerEntity>>> {

    public void jokerList(int p, int row) {
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IForumServer.class)
                .getJokerList(p, row)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<JokerEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<JokerEntity>> t) throws Exception {
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

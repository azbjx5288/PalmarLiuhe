package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.BankCardEntity;
import com.liuheonline.la.network.api.IAccountServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class GetCardPresenter extends BasePresenter<BaseView<List<BankCardEntity>>> {

    /**
     * @param
     * @return void
     * @description 获取银行卡列表
     */
    public void getCard(int p, int row) {
        Map<String, Object> map = new HashMap<>();
        map.put("p", p);
        map.put("row", row);
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAccountServer.class)
                .getCardList(map)
                .compose(SetThread.<BaseEntity<List<BankCardEntity>>>io_main())
                .subscribe(new BaseConsumer<List<BankCardEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<BankCardEntity>> t) throws Exception {
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

    public void getCardUserLoad(int p, int row) {
        Map<String, Object> map = new HashMap<>();
        map.put("p", p);
        map.put("row", row);
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAccountServer.class)
                .getCardList(map)
                .compose(SetThread.<BaseEntity<List<BankCardEntity>>>io_main())
                .subscribe(new BaseConsumer<List<BankCardEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<BankCardEntity>> t) throws Exception {
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

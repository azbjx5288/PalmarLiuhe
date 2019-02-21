package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.TrendMapEntity;
import com.liuheonline.la.network.api.ILotteryServer;
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
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class TrendMapPresenter extends BasePresenter<BaseView<List<TrendMapEntity>>> {

    /**
     * @description 获取热图走势
     * @param
     * @return void
     */
    public void getTrendMap(int limit,String type,int sid){
        Map<String,Object> map = new HashMap<>();
        map.put("limit",limit);
        map.put("type",type);
        map.put("sid",sid);
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getTrendMap(map)
                .compose(SetThread.<BaseEntity<List<TrendMapEntity>>>io_main())
                .subscribe(new BaseConsumer<List<TrendMapEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<TrendMapEntity>> t) throws Exception {
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

package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.FiveElementsEntity;
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
public class FiveElementsPresenter extends BasePresenter<BaseView<List<FiveElementsEntity>>> {

    /**
     * @description 获取五行走势
     * @param  year 年份 p 页码 row页数
     * @return void
     */
    public void getFive(int year,int p,int row,int sid){
        Map<String,Object> map = new HashMap<>();
        map.put("year",year);
        map.put("p",p);
        map.put("row",row);
        map.put("sid",sid);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getFiveElements(map)
                .compose(SetThread.<BaseEntity<List<FiveElementsEntity>>>io_main())
                .subscribe(new BaseConsumer<List<FiveElementsEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<FiveElementsEntity>> t) throws Exception {
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

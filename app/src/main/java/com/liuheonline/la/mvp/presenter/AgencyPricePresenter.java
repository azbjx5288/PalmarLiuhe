package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.AgencyPriceEntity;
import com.liuheonline.la.network.api.IAgencyServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class AgencyPricePresenter extends BasePresenter<BaseView<AgencyPriceEntity>> {

    /**
     * @description 代理佣金列表
     * @param
     * @return void
     */
    public void priceList(String query_start_date,String query_end_date,String type){
        Map<String,Object> map = new HashMap<>();
        map.put("query_start_date",query_start_date);
        map.put("query_end_date",query_end_date);
        map.put("type",type);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAgencyServer.class)
                .getPriceList(map)
                .compose(SetThread.<BaseEntity<AgencyPriceEntity>>io_main())
                .subscribe(new BaseConsumer<AgencyPriceEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<AgencyPriceEntity> t) throws Exception {
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

package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.PaysEntity;
import com.liuheonline.la.network.api.IUserServer;
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

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class NewPaysPresenter extends BasePresenter<BaseView<PaysEntity>> {

    /**
     * @description 获取支付二维码接口
     * @param
     * @return void
     */
    public void pays(String out_order_id,String price,String type){
        Map<String,Object> map = new HashMap<>();
        map.put("out_order_id",out_order_id);
        map.put("price",price);
        map.put("type",type);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getpay(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<PaysEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<PaysEntity> t) throws Exception {
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

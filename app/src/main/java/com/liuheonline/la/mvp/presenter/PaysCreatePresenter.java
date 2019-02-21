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
 * @author BenYanYi
 * @date 2019/01/18 14:37
 * @email ben@yanyi.red
 * @overview
 */
public class PaysCreatePresenter extends BasePresenter<BaseView<PaysEntity>> {
    /**
     * @param
     * @return void
     * @description 刷新支付二维码接口
     */
    public void pays(String price, String type, String out_order_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("price", price);
        map.put("type", type);
        map.put("out_order_id", out_order_id);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getnewpay(map)
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

package com.liuheonline.la.mvp.presenter;

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
import java.util.Map;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class AddCardPresenter extends BasePresenter<BaseView<Object>> {

    /**
     * @description 添加银行卡
     * @param
     * @return void
     */
    public void addCard(String bank_account_name,String bank_account_number,String bank_address,String id,String bank_name,String paypwd){
        Map<String,Object> map = new HashMap<>();
        map.put("bank_account_name",bank_account_name);
        map.put("bank_account_number",bank_account_number);
        map.put("bank_address",bank_address);
        map.put("bank_id",id);
        map.put("bank_name",bank_name);
        map.put("paypwd",paypwd);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAccountServer.class)
                .addCard(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<Object>() {
                    @Override
                    protected void onSuccees(BaseEntity<Object> t) throws Exception {
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

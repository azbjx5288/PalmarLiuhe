package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.UserInfo;
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
 * @date 2018/7/17 17:01
 * @description 注册
 */
public class RegisterPresenter extends BasePresenter<BaseView<UserInfo>>{

    public void register(String name,String password,String code,String systemmodel,String mobile){

        getView().onLoading();

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("password",password);
        map.put("code",code);
        map.put("mobile",mobile);
        map.put("systemmodel",systemmodel);

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .register(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<UserInfo>() {
                    @Override
                    protected void onSuccees(BaseEntity<UserInfo> t) throws Exception {
                        getView().successed(t.getmData());
                    }

                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                },new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                },() -> {
                });
    }
}

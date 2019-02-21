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

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/17 11:26
 * @description 用户登录
 */
public class LoginPresenter extends BasePresenter<BaseView<UserInfo>>{

    public void login(String loginname,String password){

        getView().onLoading();

        Map<String,Object> map = new HashMap<>();
        map.put("loginname",loginname);
        map.put("password",password);

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .login(map)
                .compose(SetThread.<BaseEntity<UserInfo>>io_main())
                .subscribe(new BaseConsumer<UserInfo>() {
                    @Override
                    protected void onSuccees(BaseEntity<UserInfo> t) throws Exception {
                        getView().successed(t.getmData());
                    }

                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                } , new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                });
    }
}

package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.PdrechargeEntity;
import com.liuheonline.la.network.api.IUserServer;
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
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class PdrechargePresenter extends BasePresenter<BaseView<PdrechargeEntity>> {

    //充值记录
    public void pdrecharge(int p,int row,String start_time){
        Map<String,Object> map = new HashMap<>();
        map.put("p",p);
        map.put("row",row);
        map.put("start_time",start_time);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .pdrecharge(map)
                .compose(SetThread.<BaseEntity<PdrechargeEntity>>io_main())
                .subscribe(new BaseConsumer<PdrechargeEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<PdrechargeEntity> t) throws Exception {
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

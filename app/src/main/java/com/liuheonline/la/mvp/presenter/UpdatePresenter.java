package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.UpdateEntity;
import com.liuheonline.la.network.api.IUpdateServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.mylove.loglib.JLog;
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
public class UpdatePresenter extends BasePresenter<BaseView<List<UpdateEntity>>> {

    /**
     * @description
     * @param
     * @return void
     */
    public void getUpdate(String versions,int type){
        Map<String,Object> map = new HashMap<>();
        map.put("versions",versions);
        map.put("type",type);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUpdateServer.class)
                .getUpdate(map)
                .compose(SetThread.<BaseEntity<List<UpdateEntity>>>io_main())
                .subscribe(new BaseConsumer<List<UpdateEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<UpdateEntity>> t) throws Exception {
                        JLog.d(t.getmData());
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

package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.OdevityEntity;
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
public class OdevityPresenter extends BasePresenter<BaseView<List<OdevityEntity>>> {

    /**
     * @description 获取单双走势
     * @param  year 年份 p 页码 row页数
     * @return void
     */
    public void getOdevity(int year,int p,int row,int sid){
        Map<String,Object> map = new HashMap<>();
        map.put("year",year);
        map.put("p",p);
        map.put("row",row);
        map.put("sid",sid);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getOdevity(map)
                .compose(SetThread.<BaseEntity<List<OdevityEntity>>>io_main())
                .subscribe(new BaseConsumer<List<OdevityEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<OdevityEntity>> t) throws Exception {
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

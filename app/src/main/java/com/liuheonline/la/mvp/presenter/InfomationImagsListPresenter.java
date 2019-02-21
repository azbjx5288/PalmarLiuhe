package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.InfoImagesEntity;
import com.liuheonline.la.network.api.IInfomationServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class InfomationImagsListPresenter extends BasePresenter<BaseView<List<InfoImagesEntity>>> {

    /**
     * @param
     * @return void
     * @description 获取资料详情
     */
    public void getInfomationDetail(String type) {
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IInfomationServer.class)
                .getInfomationList(String.format("http://wap.jizhou56.com:8090/json/list/%s", type))
                .compose(SetThread.<ArrayList<InfoImagesEntity>>io_main())
                .subscribe(new Consumer<ArrayList<InfoImagesEntity>>() {
                    @Override
                    public void accept(ArrayList<InfoImagesEntity> infoImagesEntities) throws Exception {
                        getView().successed(infoImagesEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onLoadFailed(throwable.hashCode(), throwable.getMessage());
                    }
                });
    }
}

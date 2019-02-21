package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.XuanJiEntity;
import com.liuheonline.la.entity.XuanjiLaji;
import com.liuheonline.la.network.api.IInfomationServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class XuanjiListPresenter extends BasePresenter<BaseView<List<XuanJiEntity>>> {

    /**
     * @param
     * @return void
     * @description 获取资料详情
     */
    public void getInfomationDetail(int year, int page, int pageSize) {
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IInfomationServer.class)
                .getXuanjiList(year, page, pageSize)
                .compose(SetThread.<BaseEntity<XuanjiLaji>>io_main())
                .subscribe(new Consumer<BaseEntity<XuanjiLaji>>() {
                    @Override
                    public void accept(BaseEntity<XuanjiLaji> baseEntity) throws Exception {
                        if (baseEntity.getmData().isResult()) {
                            getView().successed(baseEntity.getmData().getItems());
                        } else {
                            getView().onLoadFailed(001, "获取失败,请稍后再试");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onLoadFailed(throwable.hashCode(), throwable.getMessage());
                    }
                });
    }
}

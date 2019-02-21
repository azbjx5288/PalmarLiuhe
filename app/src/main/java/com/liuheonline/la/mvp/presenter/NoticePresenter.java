package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.NoticeEntity;
import com.liuheonline.la.network.api.INoticeServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
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

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class NoticePresenter extends BasePresenter<BaseView<List<NoticeEntity>>>{

    /**
     * @description 获取公告
     * @param
     * @return void
     */
    public void getNotice(int p,int row){
        Map<String,Object> map = new HashMap<>();
        map.put("p",p);
        map.put("row",row);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(INoticeServer.class)
                .getNotice(map)
                .compose(SetThread.<BaseEntity<List<NoticeEntity>>>io_main())
                .subscribe(new BaseConsumer<List<NoticeEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<NoticeEntity>> t) throws Exception {
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
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}

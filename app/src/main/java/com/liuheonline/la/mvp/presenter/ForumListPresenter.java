package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.ForumEntity;
import com.liuheonline.la.network.api.IForumServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;


/**
 * @author: aini
 * @date 2018/7/17 11:26
 * @description 帖子列表
 */
public class ForumListPresenter extends BasePresenter<BaseView<List<ForumEntity>>>{

    public void forumList(int p,int row,int uid){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IForumServer.class)
                .getForumList(p, row, uid)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<ForumEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<ForumEntity>> t) throws Exception {
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

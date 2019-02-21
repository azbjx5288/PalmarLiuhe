package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.CommentEntity;
import com.liuheonline.la.network.api.IForumServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;


/**
 * @author: aini
 * @date 2018/7/17 11:26
 * @description 回复评论
 */
public class AnswerCommentPresenter extends BasePresenter<BaseView<CommentEntity>>{

    public void answerComment(int pid,String content){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IForumServer.class)
                .answerComment(pid,content)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<CommentEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<CommentEntity> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {                    @Override                    protected void onCodeError(int code, String error) throws Exception {                        getView().onLoadFailed(code, error);                    }                }, () -> {
                });
    }
}

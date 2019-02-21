package com.liuheonline.la.ui.user.forummanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.CommentEntity;
import com.liuheonline.la.entity.ForumEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.mvp.presenter.AddCommentPresenter;
import com.liuheonline.la.mvp.presenter.AnswerCommentPresenter;
import com.liuheonline.la.mvp.presenter.DeleteCommentPresenter;
import com.liuheonline.la.mvp.presenter.DeleteForumPresenter;
import com.liuheonline.la.mvp.presenter.ForumAgreePresenter;
import com.liuheonline.la.mvp.presenter.ForumListPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.ui.picture.PicturePagerActivity;
import com.liuheonline.la.ui.user.login.LoginActivity;
import com.liuheonline.la.ui.widget.ClickShowMoreLayout;
import com.liuheonline.la.ui.widget.PraiseWidget;
import com.liuheonline.la.ui.widget.commentwidget.CommentBox;
import com.liuheonline.la.ui.widget.commentwidget.CommentContentsLayout;
import com.liuheonline.la.ui.widget.commentwidget.CommentWidget;
import com.liuheonline.la.ui.widget.commentwidget.IComment;
import com.liuheonline.la.ui.widget.popu.CircleViewHelper;
import com.liuheonline.la.ui.widget.popu.CommentPopup;
import com.liuheonline.la.ui.widget.popu.DeleteCommentMenuPopup;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.KeyboardControlMnanager;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: aini
 * @date 2018/7/24 09:31
 * @description
 */
public class ForumManagerActivity extends BaseMVPActivity<BaseView<List<ForumEntity>>,ForumListPresenter,List<ForumEntity>>implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    //点赞presenter
    private ForumAgreePresenter forumAgreePresenter;

    //新增评论
    private AddCommentPresenter addCommentPresenter;

    //回复评论
    private AnswerCommentPresenter answerCommentPresenter;

    //删除帖子
    private DeleteForumPresenter deleteForumPresenter;

    //删除评论
    private DeleteCommentPresenter deleteCommentPresenter;

    @BindId(R.id.forum_refresh)
    private SwipeRefreshLayout forumRefresh;

    @BindId(R.id.forum_recycler)
    private RecyclerView forumRecycler;

    //评论框
    @BindId(R.id.widget_comment)
    private CommentBox commentBox;
    //弹出评论框辅助类
    private CircleViewHelper mViewHelper;

    //用户资料
    private UserInfo userInfo;

    //评论点赞窗口
    private CommentPopup commentPopup;

    //删除弹窗
    private AlertDialog deleteDialog;

    private BaseQuickAdapter<ForumEntity,BaseViewHolder> baseQuickAdapter;

    private int page = 1;

    @BindId(R.id.notdata_linear)
    LinearLayout nodata;

    @Override
    protected void initData() {

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.userInfo = (UserInfo) bundle.get("userInfo");
        }

        baseQuickAdapter = new BaseQuickAdapter<ForumEntity, BaseViewHolder>(R.layout.item_forum) {
            @Override
            protected void convert(BaseViewHolder helper, ForumEntity item) {
                //用户头像
                ImageView userIconImg = helper.getView(R.id.user_icon);
                Glide.with(helper.itemView)
                        .load(item.getHead_link())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.touxiang) //加载中图片
                                .error(R.mipmap.touxiang) //加载失败图片
                                .fallback(R.mipmap.touxiang) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(userIconImg);

                //显示删除
                helper.setVisible(R.id.delete_forum, userInfo != null && item.getUid() == userInfo.getId());
                TextView deleteText = helper.getView(R.id.delete_forum);
                deleteText.setOnClickListener(v -> {
                    deleteDialog.setOnclickListener(R.id.cancel,v1 -> deleteDialog.dismiss());
                    deleteDialog.setOnclickListener(R.id.sure,v1 -> {
                        deleteForumPresenter.attachView(new BaseView<Integer>() {
                            @Override
                            public void onLoading() {
                                Log.d("删除帖子","提交中……");
                            }

                            @Override
                            public void onLoadFailed(int code, String error) {
                                Log.d("删除帖子","提交失败……");
                                deleteDialog.dismiss();
                                Toast.makeText(LiuHeApplication.context,"网络错误",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void successed(Integer integer) {
                                baseQuickAdapter.remove(helper.getAdapterPosition());
                                baseQuickAdapter.notifyDataSetChanged();
                                deleteDialog.dismiss();
                                Log.d("删除帖子","提交成功");
                                Toast.makeText(LiuHeApplication.context,"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        deleteForumPresenter.deleteForum(item.getId());
                    });
                    deleteDialog.show();
                });


                //加载用户名
                helper.setText(R.id.user_name,item.getNickname().toString());

                //帖子内容
                ClickShowMoreLayout clickShowMoreLayout = helper.getView(R.id.content);
                clickShowMoreLayout.setText(item.getDepict());

                //发帖时间
                helper.setText(R.id.forum_time,item.getCreate_time());

                //九宫格
                NineGridImageView nineGridImageView = helper.getView(R.id.grid_img);
                nineGridImageView.setAdapter(new NineGridImageViewAdapter<String>() {
                    @Override
                    protected void onDisplayImage(Context context, ImageView imageView, String o) {
                        Glide.with(context)
                                .load(ImageUrlUtil.getImgUrl(o,50))
                                .apply(new RequestOptions()
                                        .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                        .error(R.mipmap.jianzaizhong) //加载失败图片
                                        .fallback(R.mipmap.jianzaizhong) //url为空图片
                                        .centerCrop() // 填充方式
                                        .priority(Priority.HIGH) //优先级
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                                .into(imageView);
                    }
                    @Override
                    protected void onItemImageClick(Context context, int index, List<String> list) {
                        super.onItemImageClick(context, index, list);
                        Bundle bundle = new Bundle();
                        bundle.putInt("index",index);
                        bundle.putStringArrayList("urlList", (ArrayList<String>) list);
                        startActivity(PicturePagerActivity.class,bundle);
                    }
                });
                nineGridImageView.setImagesData(item.getImglist_link());

                //点赞列表
                PraiseWidget praiseWidget = helper.getView(R.id.praise);
                boolean needPraiseData = addLikes(item.getAgree(),praiseWidget);
                praiseWidget.setVisibility(needPraiseData ? View.VISIBLE : View.GONE);

                //点赞 、 评论
                LinearLayout commentImg = helper.getView(R.id.comment_img);
                commentImg.setOnClickListener(v -> {
                    commentPopup.updateMomentInfo(item);
                    commentPopup.showPopupWindow(v);
                    commentPopup.setOnCommentPopupClickListener(new CommentPopup.OnCommentPopupClickListener() {
                        //点赞
                        @Override
                        public void onLikeClick(View v, @NonNull ForumEntity info, boolean hasLiked) {
                            if(userInfo == null){
                                startActivity(LoginActivity.class);
                                return;
                            }
                            //true 取消赞 false 点赞
                            forumAgreePresenter.attachView(new BaseView<Integer>() {
                                @Override
                                public void onLoading() {
                                    Log.d("点赞","提交……");
                                }
                                @Override
                                public void onLoadFailed(int code, String error) {
                                    Log.d("点赞","提交失败……");
                                    Toast.makeText(getApplicationContext(),
                                            "网络错误",Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void successed(Integer integer) {
                                    if(integer == 1){//点赞
                                        Map<String,Object> map = new HashMap<>();
                                        map.put("nickname",userInfo.getNickname());
                                        item.getAgree().add(0,map);
                                        item.setIs_agree(1);
                                    }else if(integer == -1){//取消点赞
                                        for(int i = 0; i<item.getAgree().size(); i++){
                                            if(item.getAgree().get(i).get("nickname").equals(userInfo.getNickname())){
                                                item.getAgree().remove(i);
                                                item.setIs_agree(0);
                                            }
                                        }
                                    }else{
                                        Log.d("点赞","点赞异常……");
                                    }
                                    baseQuickAdapter.notifyDataSetChanged();
                                }
                            });
                            forumAgreePresenter.agree(1,item.getId());
                        }
                        //新增评论
                        @Override
                        public void onCommentClick(View v, @NonNull ForumEntity info) {
                            if(userInfo == null){
                                startActivity(LoginActivity.class);
                                return;
                            }
                            showCommentBox(helper.itemView, helper.getAdapterPosition(),
                                    info.getId()+"", null);
                        }
                    });
                });

                //评论
                CommentContentsLayout commentContentsLayout = helper.getView(R.id.comment_layout);
                commentContentsLayout.setMode(CommentContentsLayout.Mode.NORMAL);
                boolean needCommentData = commentContentsLayout.addComments(item.getSon());
                commentContentsLayout.setVisibility(needCommentData ? View.VISIBLE : View.GONE);
                commentContentsLayout.setOnCommentItemClickListener(widget -> {
                    if(userInfo == null){
                        startActivity(LoginActivity.class);
                        return;
                    }

                    IComment comment = widget.getData();
                    CommentEntity commentEntity = null;
                    if (comment instanceof CommentEntity) {
                        commentEntity = (CommentEntity) comment;
                    }
                    if (commentEntity == null) return;
                    if(userInfo.getId() == commentEntity.getUid()){
                        CommentEntity finalCommentEntity1 = commentEntity;
                        new DeleteCommentMenuPopup(ForumManagerActivity.this).setOnDeleteCommentMenuClickListener(() -> {
                            deleteCommentPresenter.attachView(new BaseView<Integer>() {
                                @Override
                                public void onLoading() {
                                }

                                @Override
                                public void onLoadFailed(int code, String error) {
                                    Toast.makeText(LiuHeApplication.context,"删除失败,请检查网络",Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void successed(Integer integer) {
                                    for(int i = 0;i < item.getSon().size();i++){
                                        CommentEntity cm = item.getSon().get(i);
                                        if(cm.getId() == finalCommentEntity1.getId()){
                                            item.getSon().remove(i);
                                        }
                                    }
                                    baseQuickAdapter.notifyDataSetChanged();
                                    Toast.makeText(LiuHeApplication.context,"删除成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                            deleteCommentPresenter.deleteComment(finalCommentEntity1.getId());
                        }).showPopupWindow();
                    }else{
                        showCommentBox(null, helper.getAdapterPosition(),
                                commentEntity.getId()+"", widget);
                    }
                });

                //分割线显示
                View line = helper.getView(R.id.divider);
                line.setVisibility(needPraiseData && needCommentData ? View.VISIBLE : View.GONE);

                //评论域显示
                LinearLayout commentLinear = helper.getView(R.id.comment_praise_layout);
                commentLinear.setVisibility(needPraiseData || needCommentData ? View.VISIBLE : View.GONE);

                //新增评论
                commentBox.setOnCommentSendClickListener((v, comment, commentContent) -> {
                    if(userInfo == null){
                        startActivity(LoginActivity.class);
                        return;
                    }
                    if (TextUtils.isEmpty(commentContent)) {
                        commentBox.dismissCommentBox(true);
                        return;
                    }
                    int itemPos = mViewHelper.getCommentItemDataPosition();
                    if (itemPos < 0 || itemPos > baseQuickAdapter.getItemCount()) return;

                    ForumEntity forumEntity = baseQuickAdapter.getData().get(itemPos);

                    if(comment == null){  //回复帖子
                        addCommentPresenter.attachView(new BaseView<CommentEntity>() {
                            @Override
                            public void onLoading() {
                                Log.d("回复帖子","提交回复内容");
                            }
                            @Override
                            public void onLoadFailed(int code, String error) {
                                Toast.makeText(LiuHeApplication.context,"网络错误",Toast.LENGTH_SHORT).show();
                                Log.d("回复帖子","提交回复内容失败");
                            }

                            @Override
                            public void successed(CommentEntity commentEntity) {
                                //刷新本地数据
                                baseQuickAdapter.getData().get(itemPos).getSon().add(commentEntity);
                                baseQuickAdapter.notifyDataSetChanged();
                                //baseQuickAdapter.setData(itemPos,forumEntity);
                                //关闭评论窗
                                commentBox.clearDraft();
                                commentBox.dismissCommentBox(true);
                            }
                        });
                        addCommentPresenter.addComment(commentContent,forumEntity.getId());

                    }else{  //回复评论
                        answerCommentPresenter.attachView(new BaseView<CommentEntity>() {
                            @Override
                            public void onLoading() {
                                Log.d("回复评论","提交回复内容");
                            }
                            @Override
                            public void onLoadFailed(int code, String error) {
                                Toast.makeText(LiuHeApplication.context,"网络错误",Toast.LENGTH_SHORT).show();
                                Log.d("回复评论","提交回复内容失败");
                            }
                            @Override
                            public void successed(CommentEntity commentEntity) {
                                //刷新本地数据
                                baseQuickAdapter.getData().get(itemPos).getSon().add(commentEntity);
                                baseQuickAdapter.notifyDataSetChanged();
                                //关闭评论窗
                                commentBox.clearDraft();
                                commentBox.dismissCommentBox(true);
                            }
                        });
                        if(comment instanceof CommentEntity){
                            CommentEntity commentTop = (CommentEntity) comment;
                            answerCommentPresenter.answerComment(commentTop.getId(),commentContent);
                        }else{
                            commentBox.dismissCommentBox(true);
                            Toast.makeText(LiuHeApplication.context,"未知异常",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            /**
             * 添加点赞
             * @param likesList
             * @return ture=显示点赞，false=不显示点赞
             */
            private boolean addLikes(List<Map<String,Object>> likesList, PraiseWidget praiseWidget) {
                if (likesList == null || likesList.size() <= 0) {
                    return false;
                }
                praiseWidget.setDatas(likesList);
                return true;
            }
        };
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_community);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("我的帖子")
                .builder();
    }

    @Override
    protected void initView() {
//初始化评论框
        initKeyboardHeightObserver();
        if (mViewHelper == null) {
            mViewHelper = new CircleViewHelper(ForumManagerActivity.this);
        }

        //初始化adapter
        baseQuickAdapter.setOnLoadMoreListener(this,forumRecycler);
        baseQuickAdapter.disableLoadMoreIfNotFullPage();

        //初始化recycler
        forumRecycler.setLayoutManager(new LinearLayoutManager(ForumManagerActivity.this));
        forumRecycler.setAdapter(baseQuickAdapter);
        forumRecycler.setHasFixedSize(true);
        forumRefresh.setOnRefreshListener(this);

        //初始化popupWindow
        if (commentPopup == null) {
            commentPopup = new CommentPopup(ForumManagerActivity.this);
        }

        //初始化dialog
        deleteDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_delete)
                .setWidthAndHeight(Dip2pxUtil.dip2px(this,300f),Dip2pxUtil.dip2px(ForumManagerActivity.this,150f))
                .create();
    }


    /**/

    @Override
    protected void initPresenter() {
        presenter = new ForumListPresenter();
        forumAgreePresenter = new ForumAgreePresenter();
        addCommentPresenter = new AddCommentPresenter();
        answerCommentPresenter = new AnswerCommentPresenter();
        deleteForumPresenter = new DeleteForumPresenter();
        deleteCommentPresenter = new DeleteCommentPresenter();
    }

    @Override
    public void onRefresh() {
        //刷新帖子列表
        page = 1;
        presenter.forumList(page,10, userInfo.getId());
    }

    @Override
    protected void fetchData() {
        forumRefresh.setRefreshing(true);
        //获取帖子列表
        presenter.forumList(page,10, userInfo.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        forumRefresh.setRefreshing(false);
        baseQuickAdapter.loadMoreFail();
    }

    @Override
    public void successed(List<ForumEntity> forumEntities) {
        forumRefresh.setRefreshing(false);
        nodata.setVisibility(View.GONE);
        if(page == 1){
            if(forumEntities != null && forumEntities.size() > 0){
                baseQuickAdapter.setNewData(forumEntities);
                if(forumEntities.size()  < 10){
                    baseQuickAdapter.loadMoreEnd();
                }
            }else{
                baseQuickAdapter.setNewData(forumEntities);//空数据
                nodata.setVisibility(View.VISIBLE);
            }
        }else{
            if(forumEntities != null && forumEntities.size() == 10){

                baseQuickAdapter.loadMoreComplete();
            }else{
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(forumEntities);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.forumList(page,10, userInfo.getId());
    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(this,
                new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
                    View anchorView;
                    @Override
                    public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                        int commentType = commentBox.getCommentType();
                        if (isVisible) {
                            //定位评论框到view
                            anchorView = mViewHelper.alignCommentBoxToView(forumRecycler, commentBox, commentType);
                        } else {
                            //定位到底部
                            commentBox.dismissCommentBox(false);
                            mViewHelper.alignCommentBoxToViewWhenDismiss(forumRecycler, commentBox, commentType, anchorView);
                        }
                    }
                });
    }

    /**
     * @description 弹出评论框
     * @param viewHolderRootView
     * @return void
     */
    public void showCommentBox(@Nullable View viewHolderRootView, int itemPos,
                               String momentid, CommentWidget commentWidget) {
        if (viewHolderRootView != null) {
            mViewHelper.setCommentAnchorView(viewHolderRootView);
        } else if (commentWidget != null) {
            mViewHelper.setCommentAnchorView(commentWidget);
        }
        mViewHelper.setCommentItemDataPosition(itemPos);
        commentBox.toggleCommentBox(momentid, commentWidget == null ? null : commentWidget.getData(), false);
    }

}

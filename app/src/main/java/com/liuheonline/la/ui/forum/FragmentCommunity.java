package com.liuheonline.la.ui.forum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.liuheonline.la.entity.CommentEntity;
import com.liuheonline.la.entity.ForumEntity;
import com.liuheonline.la.entity.ImageEntity;
import com.liuheonline.la.entity.UserInfo;
import com.liuheonline.la.event.ForumTopEvent;
import com.liuheonline.la.mvp.presenter.AddCommentPresenter;
import com.liuheonline.la.mvp.presenter.AnswerCommentPresenter;
import com.liuheonline.la.mvp.presenter.DeleteCommentPresenter;
import com.liuheonline.la.mvp.presenter.DeleteForumPresenter;
import com.liuheonline.la.mvp.presenter.ForumAgreePresenter;
import com.liuheonline.la.mvp.presenter.ForumListPresenter;
import com.liuheonline.la.mvp.presenter.PostFilePresenter;
import com.liuheonline.la.mvp.presenter.UpUserInfoPresenter;
import com.liuheonline.la.mvp.presenter.UserInfoPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.ui.forum.holder.GlideImageLoader;
import com.liuheonline.la.ui.imageengine.GlideImageEngine;
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
import com.liuheonline.la.utils.FileUtil;
import com.liuheonline.la.utils.ImageUrlUtil;
import com.liuheonline.la.utils.KeyboardControlMnanager;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.mylove.loglib.JLog;
import com.pedaily.yc.ycdialoglib.dialog.CustomSelectDialog;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class FragmentCommunity extends BaseMvpFragment<BaseView<List<ForumEntity>>, ForumListPresenter,
        List<ForumEntity>> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    //用户信息
    private UserInfoPresenter userInfoPresenter;

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

    //编辑用户信息
    private UpUserInfoPresenter upUserInfoPresenter;

    //上传文件
    private PostFilePresenter postFilePresenter;

    private String headBgUrl = "";

    //图片选择器
    private GalleryConfig galleryConfig;

    //图片选择器回调
    private IHandlerCallBack iHandlerCallBack;

    private AlertDialog waitDialog;

    @BindId(R.id.forum_refresh)
    private SwipeRefreshLayout forumRefresh;

    @BindId(R.id.forum_recycler)
    private RecyclerView forumRecycler;

    @BindId(R.id.top)
    FloatingActionButton topBut;

    //评论框
    @BindId(R.id.widget_comment)
    private CommentBox commentBox;
    //弹出评论框辅助类
    private CircleViewHelper mViewHelper;

    //用户名
    private TextView userNameText;

    //用户头像
    private ImageView userImg;

    //头部背景
    private ImageView headerBgImg;

    //用户资料
    private UserInfo userInfo;

    //评论点赞窗口
    private CommentPopup commentPopup;

    //删除弹窗
    private AlertDialog deleteDialog;

    private BaseQuickAdapter<ForumEntity, BaseViewHolder> baseQuickAdapter;

    private int page = 1;

    private int firstPosition = 0;

    @Override
    protected void initData() {

        initGallery();

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider(getContext().getPackageName())   // provider(必填)
                .isShowCamera(true)
                .multiSelect(false)
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();

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
                    deleteDialog.setOnclickListener(R.id.cancel, v1 -> deleteDialog.dismiss());
                    deleteDialog.setOnclickListener(R.id.sure, v1 -> {
                        deleteForumPresenter.attachView(new BaseView<Integer>() {
                            @Override
                            public void onLoading() {
                                Log.d("删除帖子", "提交中……");
                            }

                            @Override
                            public void onLoadFailed(int code, String error) {
                                Log.d("删除帖子", "提交失败……");
                                deleteDialog.dismiss();
                                Toast.makeText(LiuHeApplication.context, "网络错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void successed(Integer integer) {
                                baseQuickAdapter.remove(helper.getAdapterPosition() - 1);
                                baseQuickAdapter.notifyDataSetChanged();
                                deleteDialog.dismiss();
                                Log.d("删除帖子", "提交成功");
                                Toast.makeText(LiuHeApplication.context, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        deleteForumPresenter.deleteForum(item.getId());
                    });
                    deleteDialog.show();
                });

                //加载用户名
                helper.setText(R.id.user_name, item.getNickname());

                //帖子内容
                ClickShowMoreLayout clickShowMoreLayout = helper.getView(R.id.content);
                clickShowMoreLayout.setText(item.getDepict());

                //发帖时间
                helper.setText(R.id.forum_time, item.getCreate_time());

                //九宫格
                NineGridImageView nineGridImageView = helper.getView(R.id.grid_img);
                nineGridImageView.setAdapter(new NineGridImageViewAdapter<String>() {
                    @Override
                    protected void onDisplayImage(Context context, ImageView imageView, String o) {
                        Glide.with(context)
                                .load(ImageUrlUtil.getImgUrl(o, 50))
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
                        MNImageBrowser.with(mContext)
                                //当前位置(必填)
                                .setCurrentPosition(index)
                                //必须-图片加载用户自己去选择
                                .setImageEngine(new GlideImageEngine())
                                //必须（setImageList和setImageUrl二选一，会覆盖）-设置单张图片
                                .setImageList((ArrayList<String>) list)
                                //非必须-指示器样式（默认文本样式：两种模式）
//                                .setIndicatorType(ImageBrowserConfig.IndicatorType.Indicator_Number)
//                                //设置隐藏指示器
//                                .setIndicatorHide(false)
                                .setOnLongClickListener((activity, view1, position, url) -> {
                                    List<String> names = new ArrayList<>();
                                    names.add("保存图片");
                                    CustomSelectDialog dialog = new CustomSelectDialog(activity, R.style.transparentFrameWindowStyle, (parent, view11, position1, id) -> FileUtil.saveFile(url, activity), names);
                                    //判断activity是否finish
                                    if (!activity.isFinishing()) {
                                        dialog.show();
                                    } else {
                                        dialog.dismiss();
                                    }
                                })
                                //全屏模式
                                .setFullScreenMode(false)
//                                //打开动画
//                                .setActivityOpenAnime(R.anim.mn_browser_enter_anim)
//                                //关闭动画
//                                .setActivityExitAnime(R.anim.mn_browser_exit_anim)
                                //显示：传入当前View
                                .show(nineGridImageView);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("index", index);
//                        bundle.putStringArrayList("urlList", (ArrayList<String>) list);
//                        startActivity(PicturePagerActivity.class, bundle);
                    }
                });
                nineGridImageView.setImagesData(item.getImglist_link());

                //点赞列表
                PraiseWidget praiseWidget = helper.getView(R.id.praise);
                boolean needPraiseData = addLikes(item.getAgree(), praiseWidget);
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
                            if (userInfo == null) {
                                startActivity(LoginActivity.class);
                                return;
                            }
                            //true 取消赞 false 点赞
                            forumAgreePresenter.attachView(new BaseView<Integer>() {
                                @Override
                                public void onLoading() {
                                    Log.d("点赞", "提交……");
                                }

                                @Override
                                public void onLoadFailed(int code, String error) {
                                    Log.d("点赞", "提交失败……");
                                    Toast.makeText(getContext().getApplicationContext(),
                                            "网络错误", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void successed(Integer integer) {
                                    if (integer == 1) {//点赞
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("nickname", userInfo.getNickname());
                                        item.getAgree().add(0, map);
                                        item.setIs_agree(1);
                                    } else if (integer == -1) {//取消点赞
                                        for (int i = 0; i < item.getAgree().size(); i++) {
                                            if (item.getAgree().get(i).get("nickname").equals(userInfo.getNickname())) {
                                                item.getAgree().remove(i);
                                                item.setIs_agree(0);
                                            }
                                        }
                                    } else {
                                        Log.d("点赞", "点赞异常……");
                                    }
                                    //baseQuickAdapter.notifyItemChanged(helper.getAdapterPosition(),0);
                                    baseQuickAdapter.notifyDataSetChanged();
                                }
                            });
                            forumAgreePresenter.agree(1, item.getId());
                        }

                        //新增评论
                        @Override
                        public void onCommentClick(View v, @NonNull ForumEntity info) {
                            if (userInfo == null) {
                                startActivity(LoginActivity.class);
                                return;
                            }
                            showCommentBox(helper.itemView, helper.getAdapterPosition(),
                                    info.getId() + "", null);
                        }
                    });
                });

                //评论
                CommentContentsLayout commentContentsLayout = helper.getView(R.id.comment_layout);
                commentContentsLayout.setMode(CommentContentsLayout.Mode.NORMAL);
                boolean needCommentData = commentContentsLayout.addComments(item.getSon());
                commentContentsLayout.setVisibility(needCommentData ? View.VISIBLE : View.GONE);
                commentContentsLayout.setOnCommentItemClickListener(widget -> {
                    if (userInfo == null) {
                        startActivity(LoginActivity.class);
                        return;
                    }

                    IComment comment = widget.getData();
                    CommentEntity commentEntity = null;
                    if (comment instanceof CommentEntity) {
                        commentEntity = (CommentEntity) comment;
                    }
                    if (commentEntity == null) return;
                    if (userInfo.getId() == commentEntity.getUid()) {
                        CommentEntity finalCommentEntity1 = commentEntity;
                        new DeleteCommentMenuPopup(getActivity()).setOnDeleteCommentMenuClickListener(() -> {
                            deleteCommentPresenter.attachView(new BaseView<Integer>() {
                                @Override
                                public void onLoading() {
                                }

                                @Override
                                public void onLoadFailed(int code, String error) {
                                    Toast.makeText(LiuHeApplication.context, "删除失败,请检查网络", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void successed(Integer integer) {
                                    for (int i = 0; i < item.getSon().size(); i++) {
                                        CommentEntity cm = item.getSon().get(i);
                                        if (cm.getId() == finalCommentEntity1.getId()) {
                                            item.getSon().remove(i);
                                        }
                                    }
                                    baseQuickAdapter.notifyDataSetChanged();
                                    Toast.makeText(LiuHeApplication.context, "删除成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            deleteCommentPresenter.deleteComment(finalCommentEntity1.getId());
                        }).showPopupWindow();
                    } else {
                        showCommentBox(null, helper.getAdapterPosition(),
                                commentEntity.getId() + "", widget);
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
                    if (userInfo == null) {
                        startActivity(LoginActivity.class);
                        return;
                    }
                    if (TextUtils.isEmpty(commentContent)) {
                        commentBox.dismissCommentBox(true);
                        return;
                    }
                    int itemPos = mViewHelper.getCommentItemDataPosition();
                    if (itemPos < 0 || itemPos > baseQuickAdapter.getItemCount()) return;

                    ForumEntity forumEntity = baseQuickAdapter.getData().get(itemPos - 1);

                    if (comment == null) {  //回复帖子
                        addCommentPresenter.attachView(new BaseView<CommentEntity>() {
                            @Override
                            public void onLoading() {
                                Log.d("回复帖子", "提交回复内容");
                            }

                            @Override
                            public void onLoadFailed(int code, String error) {
                                Toast.makeText(LiuHeApplication.context, "网络错误", Toast.LENGTH_SHORT).show();
                                Log.d("回复帖子", "提交回复内容失败");
                            }

                            @Override
                            public void successed(CommentEntity commentEntity) {
                                //刷新本地数据
                                baseQuickAdapter.getData().get(itemPos - 1).getSon().add(commentEntity);
                                baseQuickAdapter.notifyDataSetChanged();
                                //baseQuickAdapter.setData(itemPos,forumEntity);
                                //关闭评论窗
                                commentBox.clearDraft();
                                commentBox.dismissCommentBox(true);
                            }
                        });
                        addCommentPresenter.addComment(commentContent, forumEntity.getId());

                    } else {  //回复评论
                        answerCommentPresenter.attachView(new BaseView<CommentEntity>() {
                            @Override
                            public void onLoading() {
                                Log.d("回复评论", "提交回复内容");
                            }

                            @Override
                            public void onLoadFailed(int code, String error) {
                                Toast.makeText(LiuHeApplication.context, "网络错误", Toast.LENGTH_SHORT).show();
                                Log.d("回复评论", "提交回复内容失败");
                            }

                            @Override
                            public void successed(CommentEntity commentEntity) {
                                //刷新本地数据
                                baseQuickAdapter.getData().get(itemPos - 1).getSon().add(commentEntity);
                                baseQuickAdapter.notifyDataSetChanged();
                                //关闭评论窗
                                commentBox.clearDraft();
                                commentBox.dismissCommentBox(true);
                            }
                        });
                        if (comment instanceof CommentEntity) {
                            CommentEntity commentTop = (CommentEntity) comment;
                            answerCommentPresenter.answerComment(commentTop.getId(), commentContent);
                        } else {
                            commentBox.dismissCommentBox(true);
                            Toast.makeText(LiuHeApplication.context, "未知异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            /**
             * 添加点赞
             * @param likesList
             * @return ture=显示点赞，false=不显示点赞
             */
            private boolean addLikes(List<Map<String, Object>> likesList, PraiseWidget praiseWidget) {
                if (likesList == null || likesList.size() <= 0) {
                    return false;
                }
                praiseWidget.setDatas(likesList);
                return true;
            }
        };
    }


    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<String> photoList) {
                waitDialog.show();
                Flowable.just(photoList)
                        .observeOn(Schedulers.io())
                        .map(list -> {
                            // 同步方法直接返回压缩后的文件
                            return Luban.with(getContext()).load(list).get();
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(files -> {
                            if (files != null && files.size() > 0) {
                                //本地文件
                                File imgFile = files.get(0);
                                //上传图片
                                postFilePresenter.postFile("", imgFile);
                            } else {
                                waitDialog.cancel();
                            }
                        });
            }

            @Override
            public void onCancel() {
                Log.i("AddForumActivity", "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i("AddForumActivity", "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i("AddForumActivity", "onError: 出错");
            }
        };

    }

    @Override
    protected void initViews() {
        //初始化评论框
        initKeyboardHeightObserver();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (mViewHelper == null) {
            mViewHelper = new CircleViewHelper(getActivity());
        }

        //初始化adapter
        baseQuickAdapter.setOnLoadMoreListener(this, forumRecycler);
        baseQuickAdapter.disableLoadMoreIfNotFullPage(forumRecycler);

        //添加头部
        View headerView = getLayoutInflater().inflate(R.layout.item_forum_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Dip2pxUtil.dip2px(getContext(), 265f)));
        userNameText = headerView.findViewById(R.id.header_user_name);
        userImg = headerView.findViewById(R.id.header_user_icon);
        headerBgImg = headerView.findViewById(R.id.forum_bg);
        headerBgImg.setOnClickListener(v -> {
            if (SharedperfencesUtil.getInt(getContext(), "userId") != 0) {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
            } else {
                startActivity(LoginActivity.class);
            }
        });
        baseQuickAdapter.addHeaderView(headerView);

        //初始化recycler
        forumRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        forumRecycler.setAdapter(baseQuickAdapter);
        forumRecycler.setHasFixedSize(true);
        forumRefresh.setOnRefreshListener(this);

        //初始化popupWindow
        if (commentPopup == null) {
            commentPopup = new CommentPopup(getActivity());
        }

        waitDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint, "提交中……")
                .create();

        //初始化dialog
        deleteDialog = new AlertDialog.Builder(getContext())
                .setContentView(R.layout.dialog_delete)
                .setWidthAndHeight(Dip2pxUtil.dip2px(getContext(), 300f), Dip2pxUtil.dip2px(getContext(), 150f))
                .create();
//        topBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "跳转", Toast.LENGTH_SHORT).show();
//                forumRecycler.scrollToPosition(0);
//            }
//        });
//        forumRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
//                if (lm instanceof LinearLayoutManager) {
//                    LinearLayoutManager linearManager = (LinearLayoutManager) lm;
//                    //获取第一个可见view的位置
//                    firstPosition = linearManager.findFirstVisibleItemPosition();
//                }
//            }
//        });
////        RelativeLayout itemLayout = helper.getView(R.id.item);
//        DoubleClick.registerDoubleClickListener(forumRecycler, new OnDoubleClickListener() {
//            @Override
//            public void OnSingleClick(View v) {
//
//            }
//
//            @Override
//            public void OnDoubleClick(View v) {
//                GoTopTask task = new GoTopTask(v);
//                task.execute(firstPosition);
//            }
//        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ForumTopEvent forumTopEvent) {
        JLog.d("呵呵呵");
        forumRecycler.scrollToPosition(0);
        onResume();
    }

    @Override
    protected void initTitle(View view) {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_community;
    }

    /**/

    @Override
    protected void initPresenter() {
        presenter = new ForumListPresenter();
        userInfoPresenter = new UserInfoPresenter();
        forumAgreePresenter = new ForumAgreePresenter();
        addCommentPresenter = new AddCommentPresenter();
        answerCommentPresenter = new AnswerCommentPresenter();
        deleteForumPresenter = new DeleteForumPresenter();
        deleteCommentPresenter = new DeleteCommentPresenter();
        postFilePresenter = new PostFilePresenter();
        upUserInfoPresenter = new UpUserInfoPresenter();
    }

    @Override
    public void onRefresh() {
        if (SharedperfencesUtil.getInt(getActivity(), "userId") != 0) {
            //刷新用户信息
            userInfoPresenter.getUserInfo(SharedperfencesUtil.getInt(getContext(), "userId"));
        }
        //刷新帖子列表
        page = 1;
        presenter.forumList(page, 10, 0);
    }

    @Override
    protected void fetchData() {
        forumRefresh.setRefreshing(true);

        //上传文件
        postFilePresenter.attachView(new BaseView<List<ImageEntity>>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context, error, Toast.LENGTH_SHORT).show();
                waitDialog.cancel();
            }

            @Override
            public void successed(List<ImageEntity> imageEntities) {
                if (imageEntities != null && imageEntities.size() > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("background", imageEntities.get(0).getPath());
                    headBgUrl = imageEntities.get(0).getLink();
                    upUserInfoPresenter.upUserInfo(SharedperfencesUtil.getInt(getContext(), "userId"), map);
                } else {
                    Toast.makeText(LiuHeApplication.context, "修改失败，网络错误", Toast.LENGTH_SHORT).show();
                    waitDialog.cancel();
                }
            }
        });

        //编辑会员信息
        upUserInfoPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(LiuHeApplication.context, error, Toast.LENGTH_SHORT).show();
                waitDialog.cancel();
            }

            @Override
            public void successed(Object o) {
                Glide.with(getContext())
                        .load(ImageUrlUtil.getImgUrl(headBgUrl, 10))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.htyjd) //加载中图片
                                .error(R.mipmap.htyjd) //加载失败图片
                                .fallback(R.mipmap.htyjd) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(headerBgImg);
                Toast.makeText(LiuHeApplication.context, "修改成功", Toast.LENGTH_SHORT).show();
                waitDialog.cancel();
            }
        });

        //获取用户信息
        userInfoPresenter.attachView(new BaseView<UserInfo>() {
            @Override
            public void onLoading() {
                Log.d("论坛初始化", "获取用户信息");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("论坛初始化", "获取用户信息失败");
                // Toast.makeText(getContext().getApplicationContext(),"获取用户信息失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(UserInfo userInfo) {
                Log.d("论坛初始化", "获取用户信息成功");
                FragmentCommunity.this.userInfo = userInfo;
                userNameText.setText(userInfo.getNickname());
                Glide.with(FragmentCommunity.this.getContext())
                        .load(ImageUrlUtil.getImgUrl(userInfo.getHead_link(), 100, 100))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.touxiang2) //加载中图片
                                .error(R.mipmap.touxiang2) //加载失败图片
                                .fallback(R.mipmap.touxiang2) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(userImg);
                Glide.with(FragmentCommunity.this.getContext())
                        .load(ImageUrlUtil.getImgUrl(userInfo.getBackground_link(), 10))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.htyjd) //加载中图片
                                .error(R.mipmap.htyjd) //加载失败图片
                                .fallback(R.mipmap.htyjd) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into(headerBgImg);
            }
        });
        if (SharedperfencesUtil.getInt(getActivity(), "userId") != 0) {
            userInfoPresenter.getUserInfo(SharedperfencesUtil.getInt(getContext(), "userId"));
        } else {
            userInfo = null;
            userNameText.setText("未登录");
            Glide.with(FragmentCommunity.this.getContext())
                    .load(R.mipmap.touxiang2)
                    .into(userImg);
            Glide.with(FragmentCommunity.this.getContext())
                    .load(R.mipmap.htyjd)
                    .apply(new RequestOptions()
                            .centerCrop() // 填充方式
                            .priority(Priority.HIGH) //优先级
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(headerBgImg);
        }
        //获取帖子列表
        presenter.forumList(page, 10, 0);
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
        JLog.d("下拉", page);
        JLog.d("下拉", forumEntities);
        forumRefresh.setRefreshing(false);
        if (page == 1) {
            if (forumEntities != null && forumEntities.size() > 0) {
                baseQuickAdapter.setNewData(forumEntities);
                if (forumEntities.size() < 10) {
                    baseQuickAdapter.loadMoreEnd();
                }
            } else {
                baseQuickAdapter.setNewData(forumEntities); //空数据
            }
        } else {
            if (forumEntities != null && forumEntities.size() == 10) {
                baseQuickAdapter.loadMoreComplete();
            } else {
                baseQuickAdapter.loadMoreEnd();
            }
            baseQuickAdapter.addData(forumEntities);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.forumList(page, 10, 0);
    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(getActivity(),
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
     * @param viewHolderRootView
     * @return void
     * @description 弹出评论框
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

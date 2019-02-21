package com.liuheonline.la.ui.widget.popu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.ForumEntity;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.WeakHandler;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/7/20 18:01
 * @description 点赞评论弹框
 */
public class CommentPopup extends BasePopupWindow implements View.OnClickListener {
    private static final String TAG = "CommentPopup";

    private ImageView mLikeView;
    private TextView mLikeText;

    private RelativeLayout mLikeClikcLayout;
    private RelativeLayout mCommentClickLayout;

    private ForumEntity forumEntity;

    private WeakHandler handler;
    private ScaleAnimation mScaleAnimation;

    private OnCommentPopupClickListener mOnCommentPopupClickListener;

    //是否已经点赞
    private boolean hasLiked;

    public CommentPopup(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setNeedPopupFade(false);
        handler = new WeakHandler();

        mLikeView = (ImageView) findViewById(R.id.iv_like);
        mLikeText = (TextView) findViewById(R.id.tv_like);

        mLikeClikcLayout = (RelativeLayout) findViewById(R.id.item_like);
        mCommentClickLayout = (RelativeLayout) findViewById(R.id.item_comment);

        mLikeClikcLayout.setOnClickListener(this);
        mCommentClickLayout.setOnClickListener(this);

        buildAnima();
        setBlurBackgroundEnable(true);
        setInterceptTouchEvent(false);
        setDismissWhenTouchOutside(true);
    }

    public void setCommentVisble(boolean flag){
        mCommentClickLayout.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    protected Animation initShowAnimation() {
        TranslateAnimation showAnima = new TranslateAnimation(Dip2pxUtil.dip2px(getContext(),180f), 0, 0, 0);
        showAnima.setInterpolator(new DecelerateInterpolator());
        showAnima.setDuration(250);
        showAnima.setFillAfter(true);
        return showAnima;
    }

    @Override
    protected Animation initExitAnimation() {
        TranslateAnimation exitAnima = new TranslateAnimation(0, Dip2pxUtil.dip2px(getContext(),180f), 0, 0);
        exitAnima.setInterpolator(new DecelerateInterpolator());
        exitAnima.setDuration(250);
        exitAnima.setFillAfter(true);
        return exitAnima;
    }

    private void buildAnima() {
        mScaleAnimation = new ScaleAnimation(1f, 2.5f, 1f, 2.5f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(300);
        mScaleAnimation.setInterpolator(new SpringInterPolator());
        mScaleAnimation.setFillAfter(false);

        mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 150);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_comment);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.comment_popup_contianer);
    }

    @Override
    public void showPopupWindow(View v) {
        setOffsetX(-getWidth() - 10);
        setOffsetY(-getHeight()*2/3);
        super.showPopupWindow(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_like:
                if (mOnCommentPopupClickListener != null) {
                    //mLikeView.clearAnimation();
                    //mLikeView.startAnimation(mScaleAnimation);
                    dismiss();
                    mOnCommentPopupClickListener.onLikeClick(v, forumEntity, hasLiked);
                }
                break;
            case R.id.item_comment:
                if (mOnCommentPopupClickListener != null) {
                    dismissWithOutAnima();
                    mOnCommentPopupClickListener.onCommentClick(v, forumEntity);
                }
                break;
        }
    }
    //=============================================================Getter/Setter

    public OnCommentPopupClickListener getOnCommentPopupClickListener() {
        return mOnCommentPopupClickListener;
    }

    public void setOnCommentPopupClickListener(OnCommentPopupClickListener onCommentPopupClickListener) {
        mOnCommentPopupClickListener = onCommentPopupClickListener;
    }

    public void updateMomentInfo(@NonNull ForumEntity info) {
        this.forumEntity = info;
        hasLiked = false;
        if (info.getIs_agree() == 1) {
            hasLiked = true;
        }
        mLikeText.setText(hasLiked ? "取消" : "赞");

    }

    //=============================================================InterFace
    public interface OnCommentPopupClickListener {
        void onLikeClick(View v, @NonNull ForumEntity info, boolean hasLiked);

        void onCommentClick(View v, @NonNull ForumEntity info);
    }

    static class SpringInterPolator extends LinearInterpolator {

        public SpringInterPolator() {
        }


        @Override
        public float getInterpolation(float input) {
            return (float) Math.sin(input * Math.PI);
        }
    }
}
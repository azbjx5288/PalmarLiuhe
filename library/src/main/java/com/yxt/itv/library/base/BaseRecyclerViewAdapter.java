package com.yxt.itv.library.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.yxt.itv.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Li.ZhiHao
 * @Date on 2018/3/21 15:23
 * @Version 1.0
 * @Description 对 RecyclerViewAdapter 简单封装
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private boolean isLoading=false;
    private OnLoadMoreListener mOnLoadMoreListener;
    private List<T> mDatas = new ArrayList<>();
    private int mLayoutId;
    private View mHeadView;
    private final static int TYPE_HEADVIEW=100;
    private final static int TYPE_ITEM=101;
    private final static int TYPE_PROGRESS=102;

    public BaseRecyclerViewAdapter(Context mContext, RecyclerView recyclerView, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        init(recyclerView);
    }
    private void init(RecyclerView recyclerView) {
        //mRecyclerView添加滑动事件监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int    totalItemCount = linearLayoutManager.getItemCount();
                int    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading &&dy>0&&lastVisibleItemPosition>=totalItemCount-1) {
                    //此时是刷新状态
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }
    public void updateData(List<T> data) {
        mDatas.clear();
        mDatas.addAll(data);
        mDatas.addAll(data);
        mDatas.addAll(data);
        mDatas.addAll(data);
        mDatas.addAll(data);
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }
    public void addHeadView(View headView){
        mHeadView=headView;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==TYPE_ITEM){
            View itemView= LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
            BaseViewHolder baseViewHolder=new BaseViewHolder(itemView);
            return baseViewHolder;
        }else if (viewType==TYPE_HEADVIEW){
            HeadViewHolder headViewHolder=new HeadViewHolder(mHeadView);
            return headViewHolder;
        } else {
            View progressView=LayoutInflater.from(mContext).inflate(R.layout.progress_item,parent,false);
            ProgressViewHolder progressViewHolder= new ProgressViewHolder(progressView);
            return progressViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if(holder instanceof ProgressViewHolder){

        }else if(holder instanceof HeadViewHolder){

        }else{
            bindData(holder, mDatas.get(position),position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadView!=null){
            if (position==getItemCount()-1 && getItemCount()>=10){
                return TYPE_PROGRESS;
            }else if (position==0){
                return TYPE_HEADVIEW;
            }else {
                return TYPE_ITEM;
            }
        }else {
            if (position==getItemCount()-1 && getItemCount()>=10){
                return TYPE_PROGRESS;
            }else {
                return TYPE_ITEM;
            }
        }

    }

    public abstract void bindData(BaseViewHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return mDatas == null || mDatas.size() == 0 ? 0 : mDatas.size() >= 10 ? mDatas.size()+1 : mDatas.size();
    }
    public void setLoading(boolean b){
        isLoading=b;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mOnLoadMoreListener=listener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public static class ProgressViewHolder extends BaseViewHolder{
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeadViewHolder extends BaseViewHolder{
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}

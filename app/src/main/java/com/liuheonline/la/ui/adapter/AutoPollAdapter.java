package com.liuheonline.la.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.LotteryEntity;
import com.yxt.itv.library.base.BaseViewHolder;

import java.util.List;

public class AutoPollAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final Context mContext;
    private final List<LotteryEntity.WinningDataBean> mData;
    public AutoPollAdapter(Context context, List<LotteryEntity.WinningDataBean> list) {
        this.mContext = context;
        this.mData = list;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String data1 = mData.get(position%mData.size()).getNickname();
        holder.setText(R.id.poii_username,data1);
        String data2 = mData.get(position%mData.size()).getOrder_species_name();
        holder.setText(R.id.poll_type,data2);
        String data3 = mData.get(position%mData.size()).getWinning_total();
        holder.setText(R.id.poll_money,data3+"元");
    }
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}

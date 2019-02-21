package com.liuheonline.la.ui.widget.popu;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.BankNameEntity;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/8/1 15:20
 * @description 设置窗口
 */
public class BanknamePopu extends BasePopupWindow{

    RecyclerView recyclerView;
    private OnViewClickListener onViewClickListener;
    BaseQuickAdapter<BankNameEntity,BaseViewHolder> baseQuickAdapter;
    public BanknamePopu(Context context, OnViewClickListener onViewClickListener) {
        super(context);
        this.onViewClickListener = onViewClickListener;
        initAdapter();
        initRecycleview();
    }

    private void initAdapter(){
        baseQuickAdapter = new BaseQuickAdapter<BankNameEntity, BaseViewHolder>(R.layout.item_bankname) {
            @Override
            protected void convert(BaseViewHolder helper, BankNameEntity item) {
                helper.setText(R.id.bank_text,item.getTitle());
                //加载图片
                Glide.with(getContext())
                        .load(item.getPic_link())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.jianzaizhong) //加载中图片
                                .error(R.mipmap.jiazaishibai) //加载失败图片
                                .fallback(R.mipmap.jiazaishibai) //url为空图片
                                .centerCrop() // 填充方式
                                .priority(Priority.HIGH) //优先级
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .into((ImageView) helper.getView(R.id.bank_img));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onViewClickListener.onVeiwCilck(item);
                    }
                });
            }
        };
    }
    private void initRecycleview(){
        recyclerView = (RecyclerView) findViewById(R.id.banker_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }


    public void showPopupWindow(View view,List<BankNameEntity> list) {
        baseQuickAdapter.setNewData(list);
        super.showPopupWindow();
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0f, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0f, 1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popu_banknamepicker);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.picker_root);
    }

    public interface OnViewClickListener {
        void onVeiwCilck(BankNameEntity bankNameEntity);
    }
}

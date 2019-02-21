package com.liuheonline.la.ui.main.property;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.PropertiyEntity;
import com.liuheonline.la.mvp.presenter.PropertyPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.utils.LotteryTypeUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;

import java.util.List;

public class FragmentPropertyZodiac extends BaseMvpFragment<BaseView<List<PropertiyEntity>>,PropertyPresenter,List<PropertiyEntity>> {
    @BindId(R.id.property_recycle)
    private RecyclerView recyclerView;


    BaseQuickAdapter<PropertiyEntity.ChildBeanX,BaseViewHolder> baseQuickAdapter;
    @Override
    protected void initPresenter() {
        presenter = new PropertyPresenter();
    }

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<PropertiyEntity.ChildBeanX, BaseViewHolder>(R.layout.item_property_zodiac) {

            @Override
            protected void convert(BaseViewHolder helper, PropertiyEntity.ChildBeanX item) {
                LinearLayout root = helper.getView(R.id.zodiac_root);
                helper.setText(R.id.color_name,item.getTitle());
                Log.w("the title",item.getTitle());
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout row1 = new LinearLayout(helper.itemView.getContext());
                //权重
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                row1.setLayoutParams(layoutParams);
                row1.setWeightSum(7);
                //row.setPadding(3,5,3,5);
                for (int i=0;i<item.get_child().size();i++){

                    LinearLayout layout1 = new LinearLayout(getContext());
                    layout1.setLayoutParams(lp);
                    TextView textView = new TextView(getContext());
                    textView.setBackgroundResource(LotteryTypeUtil.getStasisticsBallBG(item.get_child().get(i).getColor_id()));
                    textView.setText(item.get_child().get(i).getCode()+"");
                    textView.setGravity(Gravity.CENTER);
                    layout1.addView(textView);
                    layout1.setGravity(Gravity.CENTER);
                    row1.addView(layout1);
                    if (i!=0&&(i+1)%7==0){
                        root.addView(row1);
                        row1 = new LinearLayout(getContext());
                        row1.setWeightSum(7);
                        row1.setLayoutParams(layoutParams);
                    }
                    if (item.get_child().size()<7&&i==item.get_child().size()-1){
                        root.addView(row1);
                        row1 = new LinearLayout(getContext());
                        row1.setWeightSum(7);
                        row1.setLayoutParams(layoutParams);
                    }
                    if ((i+1)==item.get_child().size()&&(i+1)%7!=0){
                        root.addView(row1);
                        row1 = new LinearLayout(getContext());
                        row1.setWeightSum(7);
                        row1.setLayoutParams(layoutParams);
                    }

                }

            }

        };

    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_property_zodiac;
    }

    @Override
    protected void fetchData() {
        presenter.getProperty();

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(List<PropertiyEntity> propertiyEntities) {
        baseQuickAdapter.setNewData( propertiyEntities.get(1).get_child());

    }

}



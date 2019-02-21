package com.liuheonline.la.ui.bet;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.GameTypeClass;
import com.liuheonline.la.entity.GameTypeClass2Entity;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/7/26 09:31
 * @description 玩法类型popuwindow
 */
public class GameTypePopu extends BasePopupWindow {

    //玩法一级选择显示
    private TextView gameTypeOneText;

    //玩法一级选择列表
    private RecyclerView gameTypeOneRecycler;

    //玩法二级选择显示
    private TextView gameTypeTwoText;

    //玩法耳机选择列表
    private RecyclerView gameTypeTwoRecycler;

    //玩法列表一级适配器
    private BaseQuickAdapter<GameTypeClass2Entity,BaseViewHolder> baseQuickAdapterOne;

    //玩法列表二级适配器
    private BaseQuickAdapter<GameTypeClass2Entity.ChildBeanX, BaseViewHolder> baseQuickAdapterTwo;

    //玩法列表数据
    private List<GameTypeClass2Entity> mDatas;

    //一级默认选中postion
    private int GameTypePostion;

    //二级默认选中postion
    private int GameTypeChildPostion;

    //默认二级Item
    private GameTypeClass2Entity.ChildBeanX childBean;

    //选中事件
    private OnSelectClickListener onSelectClickListener;

    //一级玩法选中名称
    private String oneTitle = "";

    //是否第一次显示
    private boolean isOneShow = false;

    //二级第0个Item是否需要触发回调事件
    private boolean isTwoClick = true;

    public GameTypePopu(Context context, int w, int h) {
        super(context, w, h);
        childBean = new GameTypeClass2Entity.ChildBeanX();
        childBean.setId(0);
        childBean.setPid(0);
        childBean.setTitle("选码");
        childBean.setSort(0);
        childBean.setSelected(true);
        initAdapter();
        initView();
    }

    private void initAdapter(){
        baseQuickAdapterOne = new BaseQuickAdapter<GameTypeClass2Entity, BaseViewHolder>(R.layout.item_game_type) {
            @Override
            protected void convert(BaseViewHolder helper, GameTypeClass2Entity item) {
                CheckBox checkBox = helper.getView(R.id.type_checkbox);
                checkBox.setText(item.getTitle());
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked){
                        gameTypeOneText.setText(item.getTitle());
                        oneTitle = item.getTitle();
                    }
                });
                checkBox.setChecked(item.isSelected());
                checkBox.setOnClickListener(v -> {
                    isTwoClick = true;
                    isOneShow = true;
                    checkBox.setChecked(true);
                    oneTitle = item.getTitle();
                    GameTypeChildPostion = 0;
                    gameTypeOneText.setText(item.getTitle());
                    GameTypePostion = helper.getAdapterPosition();
                    refreshDatas();
                    //加载二级列表
                    if(item.get_child() != null){
                        for(GameTypeClass2Entity.ChildBeanX childBean : item.get_child()){
                            childBean.setSelected(false);
                        }
                        item.get_child().get(GameTypeChildPostion).setSelected(true);
                        baseQuickAdapterTwo.setNewData(item.get_child());
                    }else{
                        List<GameTypeClass2Entity.ChildBeanX> list = new ArrayList<>();
                        childBean.setPid(item.getId());
                        switch (item.getTitle()){
                            case "特码B":
                            case "特码A":
                            case "正码":
                                childBean.setTitle("选码");
                                break;
                            case "两面":
                                childBean.setTitle("两面");
                                break;
                            case "特肖":
                            case "正肖":
                                childBean.setTitle("生肖");
                                break;
                            case "头尾数":
                                childBean.setTitle("头尾数");
                                break;
                            case "五行":
                            case "7色波":
                            case "总肖":
                                childBean.setTitle("种类");
                                break;
                        }
                        list.add(childBean);
                        baseQuickAdapterTwo.setNewData(list);
                    }
                });
            }
        };

      baseQuickAdapterTwo = new BaseQuickAdapter<GameTypeClass2Entity.ChildBeanX, BaseViewHolder>(R.layout.item_game_type) {
          @Override
          protected void convert(BaseViewHolder helper, GameTypeClass2Entity.ChildBeanX item) {
              CheckBox checkBox = helper.getView(R.id.type_checkbox);
              checkBox.setText(item.getTitle());
              checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                  if(isChecked){
                      gameTypeTwoText.setText(item.getTitle());
                  }
              });
              checkBox.setChecked(item.isSelected());

              //当二级玩法为空时  直接返回
              if(item.getId() == 0){
                  if(isOneShow){
                      onSelectClickListener.onSelectClick(oneTitle,item.getTitle(),item.getId(),item.getPid(),true);
                  }else{
                      onSelectClickListener.onSelectClick(oneTitle,item.getTitle(),item.getId(),item.getPid(),false);
                  }
              }else if(helper.getAdapterPosition() == 0){
                  if(isTwoClick){
                      onSelectClickListener.onSelectClick(oneTitle,item.getTitle(),item.getId(),item.getPid(),false);
                  }
              }

              checkBox.setOnClickListener(v -> {
                  isTwoClick = false;
                  checkBox.setChecked(true);
                  gameTypeTwoText.setText(item.getTitle());
                  GameTypeChildPostion = helper.getAdapterPosition();
                  if(item.getId() != 0){
                      refreshChildDatas();
                  }
                  //选择玩法后回调
                  onSelectClickListener.onSelectClick(oneTitle,item.getTitle(),
                          item.getId(),item.getPid(),true);
              });
          }
      };
    }

    private void initView(){
        gameTypeOneText = (TextView) findViewById(R.id.game_type_noe);
        gameTypeOneRecycler = (RecyclerView) findViewById(R.id.game_type_noe_recycler);
        gameTypeTwoText = (TextView) findViewById(R.id.game_type_two);
        gameTypeTwoRecycler = (RecyclerView) findViewById(R.id.game_type_two_recycler);

        GridLayoutManager gridLayoutManagerOne = new GridLayoutManager(getContext(),3);
        gameTypeOneRecycler.setLayoutManager(gridLayoutManagerOne);
        gameTypeOneRecycler.setAdapter(baseQuickAdapterOne);

        GridLayoutManager gridLayoutManagerTwo = new GridLayoutManager(getContext(),3);
        gameTypeTwoRecycler.setLayoutManager(gridLayoutManagerTwo);
        gameTypeTwoRecycler.setAdapter(baseQuickAdapterTwo);
    }

    public void setDatas(List<GameTypeClass2Entity> mDatas){
        this.mDatas = mDatas;
        //默认选中第一个item
        mDatas.get(GameTypePostion).setSelected(true);
        baseQuickAdapterOne.setNewData(mDatas);
        if(mDatas.get(GameTypePostion).get_child() == null || mDatas.get(GameTypePostion).get_child().size() > 0){
            List<GameTypeClass2Entity.ChildBeanX> list = new ArrayList<>();
            oneTitle = mDatas.get(GameTypePostion).getTitle();
            childBean.setPid(mDatas.get(GameTypePostion).getId());
            childBean.setTitle("选码");
            list.add(childBean);
            baseQuickAdapterTwo.setNewData(list);
        }
    }

    private void refreshDatas(){
        //刷新默认选中的item
        for (GameTypeClass2Entity gameTypeClass : mDatas){
            gameTypeClass.setSelected(false);
        }
        mDatas.get(GameTypePostion).setSelected(true);
        baseQuickAdapterOne.notifyDataSetChanged();
    }

    private void refreshChildDatas(){
        //刷新二级选中的item
        for(GameTypeClass2Entity.ChildBeanX childBean : mDatas.get(GameTypePostion).get_child()){
            childBean.setSelected(false);
        }
        mDatas.get(GameTypePostion).get_child().get(GameTypeChildPostion).setSelected(true);
        baseQuickAdapterTwo.notifyDataSetChanged();
    }


    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener){
        this.onSelectClickListener = onSelectClickListener;
    }

    @Override
    public void showPopupWindow(View v) {
        super.showPopupWindow(v);
        isOneShow = false;
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(-1f, 0, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_gametype);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_gametype_container);
    }

    public interface OnSelectClickListener {
        void onSelectClick(String oneTitle,String twoTitle,int pid,int id,boolean isRefresh);
    }
}

package com.liuheonline.la.ui.bet;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.GameTypeNewClass3Entity;
import com.liuheonline.la.event.BetClearAllEvent;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.http.retrofit.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/7/26 09:31
 * @description 玩法类型popuwindow
 */
public class GameTypePopu3 extends BasePopupWindow {

    //玩法一级选择显示
    private TextView gameTypeOneText;

    //玩法一级选择列表
    private RecyclerView gameTypeOneRecycler;

    //玩法二级选择显示
    private TextView gameTypeTwoText;

    //玩法耳机选择列表
    private RecyclerView gameTypeTwoRecycler;

    //玩法列表一级适配器
    private BaseQuickAdapter<GameTypeNewClass3Entity, BaseViewHolder> baseQuickAdapterOne;

    //玩法列表二级适配器
    private BaseQuickAdapter<GameTypeNewClass3Entity, BaseViewHolder> baseQuickAdapterTwo;

    //玩法列表数据
    private List<GameTypeNewClass3Entity> mDatas;

    //一级默认选中postion
    private int GameTypePostion = 0;

    //二级默认选中postion
    private int GameTypeChildPostion = 0;

    //默认二级Item
    private GameTypeNewClass3Entity childBean;

    //选中事件
    private OnSelectClickListener onSelectClickListener;

    //一级玩法选中名称
    private String oneTitle = "";

    //是否第一次显示
    private boolean isOneShow = false;

    //二级第0个Item是否需要触发回调事件
    private boolean isTwoClick = true;

    //关于清不清空页面的统计的数据
    String oneTtitle, twoTitle;

    public GameTypePopu3(Context context, int w, int h) {
        super(context, w, h);
        childBean = new GameTypeNewClass3Entity();
        childBean.setId(0);
        childBean.setPid(0);
        childBean.setTitle("选码");
        childBean.setSort(0);
        childBean.setSelected(true);
        initAdapter();
        initView();
    }

    private void initAdapter() {
        //一级适配器
        baseQuickAdapterOne = new BaseQuickAdapter<GameTypeNewClass3Entity, BaseViewHolder>(R.layout.item_game_type) {
            @Override
            protected void convert(BaseViewHolder helper, GameTypeNewClass3Entity item) {
                CheckBox checkBox = helper.getView(R.id.type_checkbox);
                checkBox.setText(item.getTitle());
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        gameTypeOneText.setText(item.getTitle());
                        oneTitle = item.getTitle();
                    }
                });
                //初始化默认选中的一级、二级选中的title
                if (oneTtitle == null) {
                    oneTtitle = item.getTitle();
                    twoTitle = item.get_child() == null ? item.getChild().get(0).getAttr() : item.get_child().get(0).getTitle();
                }
                checkBox.setChecked(item.isSelected());
                checkBox.setOnClickListener(v -> {
                    isTwoClick = true;
                    isOneShow = true;
                    checkBox.setChecked(true);
                    oneTitle = item.getTitle();
                    //如果点击的是不同的玩法    就清空当前页面选择项
                    if (!oneTtitle.equals(checkBox.getText().toString().trim())) {
                        EventBus.getDefault().post(new BetClearAllEvent());
                        oneTtitle = checkBox.getText().toString().trim();
                    }
                    GameTypeChildPostion = 0;
                    gameTypeOneText.setText(item.getTitle());
                    SharedperfencesUtil.setString(LiuHeApplication.context, "onegametitle", item.getTitle());
                    GameTypePostion = helper.getAdapterPosition();
                    refreshDatas();
                    //加载二级列表
                    if (item.get_child() != null) {  //除斗牛特殊情况
                        for (GameTypeNewClass3Entity childBean : item.get_child()) {
                            childBean.setSelected(false);
                        }
                        item.get_child().get(GameTypeChildPostion).setSelected(true);
                        if (!oneTitle.equals("定位胆")) {
                            if (!oneTitle.equals("不定位")) {
                                baseQuickAdapterTwo.setNewData(item.get_child());
                                //点击以及菜单回调数据回去  刷新界面   比较复杂
                                onSelectClickListener.onSelectClick(item.getTitle(), item.get_child().get(0).getTitle(), item.get_child().get(0).getId(), getNiceData(item), item.getPrice().equals("0.00")?item.get_child().get(0).getPrice():"0.00");
                            } else {
                                baseQuickAdapterTwo.setNewData(item.get_child());
                                onSelectClickListener.onSelectClick(item.getTitle(), item.get_child().get(0).getTitle(), item.get_child().get(0).getId(), transStructure(item.get_child().get(0)),  item.getPrice().equals("0.00")?item.get_child().get(0).getPrice():"0.00");
                            }
                        } else {
                            //设置二级菜单
                            List<GameTypeNewClass3Entity> list = new ArrayList<>();
                            childBean.setPid(item.getId());
                            childBean.setTitle("选码");
                            list.add(childBean);
                            baseQuickAdapterTwo.setNewData(list);
                            //回调点击一级菜单的数据
                            onSelectClickListener.onSelectClick(item.getTitle(), item.get_child().get(0).getTitle(), item.get_child().get(0).getId(), item.get_child(),  item.getPrice().equals("0.00")?item.get_child().get(0).getPrice():"0.00");
                            //隐藏pop
                            dismissWithOutAnima();
                        }
                    } else {//斗牛特殊情况
                        List<GameTypeNewClass3Entity> list = new ArrayList<>();
                        childBean.setPid(item.getId());
                        childBean.setTitle("选码");
                        list.add(childBean);
                        baseQuickAdapterTwo.setNewData(list);
                        onSelectClickListener.onSelectClick(item.getTitle(), "选码", item.getId(), getNiceData(item), item.getPrice());
                        dismissWithOutAnima();
                    }
                });
            }
        };


        //二级适配器
        baseQuickAdapterTwo = new BaseQuickAdapter<GameTypeNewClass3Entity, BaseViewHolder>(R.layout.item_game_type) {
            @Override
            protected void convert(BaseViewHolder helper, GameTypeNewClass3Entity item) {
                CheckBox checkBox = helper.getView(R.id.type_checkbox);
                checkBox.setText(item.getTitle());
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        gameTypeTwoText.setText(item.getTitle());
                    }
                });
                checkBox.setChecked(item.isSelected());

                //=======全部刷新   所有玩法全部一次性在一个接口返回不考虑用以前的方法也就不存在考不考虑刷新界面
//                //当二级玩法为空时  直接返回
//                if (item.getId() == 0) {
//                    if (isOneShow) {
//                        //onSelectClickListener.onSelectClick(item.getTitle(),item.getId(), (List<GameTypeClass3Entity.ChildBeanXX>) item,item.getPrice());
//                    } else {
//                        //onSelectClickListener.onSelectClick(item.getTitle(),item.getId(), (List<GameTypeClass3Entity.ChildBeanXX>) item,item.getPrice());
//                    }
//                } else if (helper.getAdapterPosition() == 0) {
//                    if (isTwoClick) {
//                        // onSelectClickListener.onSelectClick(item.getTitle(),item.getId(), (List<GameTypeClass3Entity.ChildBeanXX>) item,item.getPrice());
//                    }
//                }
                checkBox.setOnClickListener(v -> {
                    isTwoClick = false;
                    checkBox.setChecked(true);
                    gameTypeTwoText.setText(item.getTitle());
                    if (!twoTitle.equals(checkBox.getText().toString().trim())) {
                        EventBus.getDefault().post(new BetClearAllEvent());
                        twoTitle = checkBox.getText().toString();
                    }
                    GameTypeChildPostion = helper.getAdapterPosition();
                    if (item.getId() != 0) {
                        refreshChildDatas();
                    }
                    String title = checkBox.getText().toString();
                    //如果是选码按钮就是不做任何操作   直接隐藏pop
                    if (title.equals("选码")) {

                    } else if (title.equals("三星组三") || title.equals("三星组六") || title.equals("二星组选")
                            || title.equals("前三一码") || title.equals("前三二码") || title.equals("后三一码") || title.equals("后三二码") || title.equals("前四一码") || title.equals("前四二码") || title.equals("后四一码") || title.equals("后四二码") || title.equals("五星一码") || title.equals("五星二码") || title.equals("五星三码")
                            || title.equals("直选和值") || title.equals("组三复式") || title.equals("组六复式") || title.equals("组选和值") || title.equals("组选复式")
                            || title.equals("组选24") || title.equals("组选6")) {
                        //结构体改变
                        onSelectClickListener.onSelectClick(item.getTitle(), "", item.getId(), transStructure(item), item.getPrice());

                    } else {
                        //选择玩法后回调
                        onSelectClickListener.onSelectClick(item.getTitle(), "", item.getId(), getTwoNiceData(item), item.getPrice());
                    }
                    dismissWithOutAnima();
                });
            }
        };
    }

    /**
     * 获取一级菜单点击的数据-------有点复杂  在这里
     *
     * @param mData
     * @return
     */
    public ArrayList<GameTypeNewClass3Entity> getNiceData(GameTypeNewClass3Entity mData) {
        if (mData.get_child() != null && mData.get_child().size() > 0) {//没到底
            if (mData.get_child().get(0).get_child() != null) {//五星、三星、二星这种
                return mData.get_child().get(0).get_child();
            } else {//
                ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();
                GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
                childBean.setId(mData.getId());
                ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
                list1.addAll(mData.get_child().get(0).getChild());
                childBean.setChild(list1);
                list.add(childBean);
                return list;
            }
        } else {
            ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();
            GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
            childBean.setId(mData.getId());
            ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
            list1.addAll(mData.getChild());
            childBean.setChild(list1);

            list.add(childBean);
            return list;
        }
    }

    /**
     * 获取二级菜单点击的数据-------有点复杂  在这里
     *
     * @param mData
     * @return
     */
    public ArrayList<GameTypeNewClass3Entity> getTwoNiceData(GameTypeNewClass3Entity mData) {
        if (mData.get_child() != null && mData.get_child().size() > 0) {//五星、三星、二星这种
            return mData.get_child();
        } else {
            ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();
            GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
            childBean.setId(mData.getId());
            ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
            list1.addAll(mData.getChild());
            childBean.setChild(list1);
            list.add(childBean);
            return list;
        }
    }

    //改结构
    public ArrayList<GameTypeNewClass3Entity> transStructure(GameTypeNewClass3Entity mData) {
        LogUtil.i("info", mData.toString());
        if (mData.getChild() == null) {
            return mData.get_child();
        } else {
            ArrayList<GameTypeNewClass3Entity> list = new ArrayList<>();

//            GameTypeNewClass3Entity childBean = new GameTypeNewClass3Entity();
//            childBean.setId(mData.getId());
//            childBean.setTitle("选码");
//            ArrayList<GameTypeNewClass3Entity> list1 = new ArrayList<>();
//            list1.addAll(mData.getChild());
//            childBean.set_child(list1);

            list.add(mData);
            return list;
        }
    }

    private void initView() {
        gameTypeOneText = (TextView) findViewById(R.id.game_type_noe);
        gameTypeOneRecycler = (RecyclerView) findViewById(R.id.game_type_noe_recycler);
        gameTypeTwoText = (TextView) findViewById(R.id.game_type_two);
        gameTypeTwoRecycler = (RecyclerView) findViewById(R.id.game_type_two_recycler);

        GridLayoutManager gridLayoutManagerOne = new GridLayoutManager(getContext(), 3);
        gameTypeOneRecycler.setLayoutManager(gridLayoutManagerOne);
        gameTypeOneRecycler.setAdapter(baseQuickAdapterOne);

        GridLayoutManager gridLayoutManagerTwo = new GridLayoutManager(getContext(), 3);
        gameTypeTwoRecycler.setLayoutManager(gridLayoutManagerTwo);
        gameTypeTwoRecycler.setAdapter(baseQuickAdapterTwo);
    }

    public void setDatas(List<GameTypeNewClass3Entity> mDatas) {
        this.mDatas = mDatas;
        //默认选中第一个item
        mDatas.get(GameTypePostion).setSelected(true);
        baseQuickAdapterOne.setNewData(mDatas);
        //|| mDatas.get(GameTypePostion).getChild().size() > 0
        if (mDatas.get(GameTypePostion).get_child() == null) {
            List<GameTypeNewClass3Entity> list = new ArrayList<>();
            oneTitle = mDatas.get(GameTypePostion).getTitle();
            childBean.setPid(mDatas.get(GameTypePostion).getId());
            childBean.setTitle("选码");
            list.add(childBean);
            baseQuickAdapterTwo.setNewData(list);
        } else {
            if (!mDatas.get(GameTypePostion).getTitle().equals("定位胆")) {
                mDatas.get(GameTypePostion).get_child().get(GameTypeChildPostion).setSelected(true);
                baseQuickAdapterTwo.setNewData(mDatas.get(GameTypePostion).get_child());
            } else {
                List<GameTypeNewClass3Entity> list = new ArrayList<>();
                childBean.setPid(mDatas.get(GameTypePostion).getId());
                childBean.setTitle("选码");
                list.add(childBean);
                baseQuickAdapterTwo.setNewData(list);
            }
        }
    }

    private void refreshDatas() {
        //刷新默认选中的item
        for (GameTypeNewClass3Entity gameTypeClass : mDatas) {
            gameTypeClass.setSelected(false);
        }
        mDatas.get(GameTypePostion).setSelected(true);
        baseQuickAdapterOne.notifyDataSetChanged();
    }

    private void refreshChildDatas() {
        //刷新二级选中的item
        for (GameTypeNewClass3Entity childBean : mDatas.get(GameTypePostion).get_child()) {
            childBean.setSelected(false);
        }
        mDatas.get(GameTypePostion).get_child().get(GameTypeChildPostion).setSelected(true);
        baseQuickAdapterTwo.notifyDataSetChanged();
    }


    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
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
        void onSelectClick(String ontTitle, String twoTitle, int id, List<GameTypeNewClass3Entity> list, String price);
    }
}

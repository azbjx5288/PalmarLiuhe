package com.liuheonline.la.ui.user;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuheonline.la.entity.UserActivityEntity;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.UserActivityPresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.Dip2pxUtil;
import com.liuheonline.la.utils.SpaceItemDecoration;
import com.liuheonline.la.utils.StringUtil;
import com.mylove.loglib.JLog;
import com.ysyy.aini.palmarliuhe.R;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.db.DaoSupportFactory;
import com.yxt.itv.library.db.IDaoSoupport;
import com.yxt.itv.library.db.curd.QuerySupport;
import com.yxt.itv.library.dialog.AlertDialog;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import java.util.List;

/**
 * @description 用户活动
 * 缓存集合数据到数据库中
 */
public class UserActiveActivity2 extends BaseMVPActivity<BaseView<List<UserActivityEntity>>,UserActivityPresenter, List<UserActivityEntity>>  implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG ="UserActiveActivity2" ;
    @BindId(R.id.topup_recycle)
    private RecyclerView recyclerView;

    private com.yxt.itv.library.dialog.AlertDialog waitDialog;

    private BaseQuickAdapter<UserActivityEntity, BaseViewHolder> baseQuickAdapter;

    List<UserActivityEntity> list;

    private AlertDialog redEnvelopeDialog;

    @BindId(R.id.index_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private IDaoSoupport<UserActivityEntity> mUserActivityEntityDao;//list集合数据缓存到数据库

    @Override
    protected void initData() {
        baseQuickAdapter = new BaseQuickAdapter<UserActivityEntity, BaseViewHolder>(R.layout.item_useractivity) {
            @Override
            protected void convert(BaseViewHolder helper, UserActivityEntity item) {

                if(!TextUtils.isEmpty(item.getImg())){

                    Glide.with(UserActiveActivity2.this)
                            .load(item.getImg())
                            .apply(new RequestOptions()
//                                .placeholder(R.mipmap.jianzaizhong) //加载中图片
//                                .error(R.mipmap.jiazaishibai) //加载失败图片
//                                .fallback(R.mipmap.jiazaishibai) //url为空图片
//                                .centerCrop() // 填充方式
//                                .priority(Priority.HIGH) //优先级
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .into((ImageView) helper.getView(R.id.payent_img));
                    helper.setGone(R.id.payent_img,true);
                    helper.setGone(R.id.content,false);
                }else{
                    helper.setText(R.id.content, item.getTitle());
                    helper.setGone(R.id.content,true);
                    helper.setGone(R.id.payent_img,false);
                }



                helper.itemView.setOnClickListener(v -> {

                    redEnvelopeDialog.show();
//                    redEnvelopeDialog.setText(R.id.edit_01, item.getAddtime() + "");
                    redEnvelopeDialog.setText(R.id.edit_01, "即日起");
                    redEnvelopeDialog.setText(R.id.edit_02, item.getPeople() + "");
                    redEnvelopeDialog.setText(R.id.edit_03, item.getContent() + item.getContent() + "");
                    TextView edit_03tv = redEnvelopeDialog.getView(R.id.edit_03);
                    edit_03tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                    redEnvelopeDialog.setOnclickListener(R.id.cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            redEnvelopeDialog.dismiss();
                        }
                    });
                    redEnvelopeDialog.setOnclickListener(R.id.sure, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            message();
                        }
                    });
                });
            }
        };

        mUserActivityEntityDao = DaoSupportFactory.getFactory(getPackageName()).getDao(UserActivityEntity.class);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_useractive2);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setLeftIconVisibility(false)
                .setTitle("活动中心")
                .builder();
    }

    @Override
    protected void initView() {
        waitDialog = new com.yxt.itv.library.dialog.AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_wait)
                .setText(R.id.text_hint,"正在加载……")
                .create();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, SpaceItemDecoration.LINEARLAYOUT));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.setHasFixedSize(true);

        redEnvelopeDialog = new AlertDialog.Builder(UserActiveActivity2.this)
                .setContentView(R.layout.dialog_activityuser)
                .setCancelable(false)
                .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                .create();

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new UserActivityPresenter();

    }

    @Override
    protected void fetchData() {
        QuerySupport<UserActivityEntity> querySupport=mUserActivityEntityDao.querySupport();
       list=querySupport.query();
       if(list!=null&&list.size()>0){
            baseQuickAdapter.setNewData(list);
            JLog.w(TAG, "list cache="+list.size());
        }else{
           presenter.getUserActivity();
       }

    }

    @Override
    public void onLoading() {
        waitDialog.show();
    }

    @Override
    public void onLoadFailed(int code, String error) {
        super.onLoadFailed(code, error);
        waitDialog.cancel();
        Toast.makeText(LiuHeApplication.context,error,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void successed(List<UserActivityEntity>  userActivityEntityList) {
        list = userActivityEntityList;
        baseQuickAdapter.setNewData(userActivityEntityList);
        mUserActivityEntityDao.clean();
        mUserActivityEntityDao.insert(userActivityEntityList);
        waitDialog.cancel();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getUserActivity();
    }


    private  void  message(){
        WebPresenter webPresenter= new WebPresenter();
        webPresenter.attachView(new BaseView<WebEntity>() {
            @Override
            public void onLoading() {
                Log.d("开奖", "获取最新开奖~~");
            }

            @Override
            public void onLoadFailed(int code, String error) {
                Log.d("开奖", "获取最新开奖失败~~");
            }

            @Override
            public void successed(WebEntity webEntity) {
                String strurl = StringUtil.translation(webEntity.getService_url());

                Uri CONTENT_URI_BROWSERS = Uri.parse(strurl);


                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(CONTENT_URI_BROWSERS);//Url 就是你要打开的网址x
                // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
                // 官方解释 : Name of the component implementing an activity that can display the intent
                if (intent.resolveActivity(UserActiveActivity2.this.getPackageManager()) != null) {
                    final ComponentName componentName = intent.resolveActivity(UserActiveActivity2.this.getPackageManager());
                    // 打印Log   ComponentName到底是什么
                    Log.e("ServiceChat", "componentName = " + componentName.getClassName());
                    startActivity(Intent.createChooser(intent, "请选择浏览器"));
                } else {
                    Toast.makeText(UserActiveActivity2.this.getApplicationContext(), "没有匹配的程序", Toast.LENGTH_SHORT).show();
                }
            }
        });

        webPresenter.getWeb();
    }
}

package com.liuheonline.la.ui.user;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.event.IndividualityEvent;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

//个性设置
public class Individuality extends BaseMVPActivity {
    @BindId(R.id.indi_voice)
    CheckBox voice;
    @BindId(R.id.indi_push)
    CheckBox push;
    @BindId(R.id.indi_yaoyiyao)
    CheckBox yaoyiyao;


    @BindId(R.id.rg_style)
    RadioGroup radioGroup;


    int pushid = 0;//是否开启推送，1为开启，2为关闭，默认为开启*/
    int voiceid = 0;//是否开启语音播报，1为开启，2为关闭，默认为关闭*/
    int yaoyiyaoid = 0;//是否开启摇一摇，1为开启，2为关闭，默认为关闭*/
    int zodiacid = 0;//是否使用图片显示生肖，1为图片，2为文字

    @OnClick({R.id.indi_left,R.id.indi_right})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.indi_left:
                zodiacid = 1;
                break;
            case R.id.indi_right:
                zodiacid = 2;
                break;
        }
        showZodiacCheckBox();
        SharedperfencesUtil.setInt(Individuality.this,"zodiacid",zodiacid);
        IndividualityEvent individualityEvent = new IndividualityEvent();
        individualityEvent.setStyle(zodiacid);
        EventBus.getDefault().post(individualityEvent);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_individuality);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("个性设置")
                .setLeftIconVisibility(false)
                .builder();
    }

    @Override
    protected void initView() {
        voice.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                SharedperfencesUtil.setInt(Individuality.this,"voiceid",1);
                voiceid = 1;
                showVoiceCheckBox();
            }else {
                SharedperfencesUtil.setInt(Individuality.this,"voiceid",2);
                voiceid = 2;
                showVoiceCheckBox();
            }
        });
        push.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                SharedperfencesUtil.setInt(Individuality.this,"pushid",1);
                if (JPushInterface.isPushStopped(Individuality.this)){
                    JPushInterface.resumePush(Individuality.this);
                }
            }else {
                SharedperfencesUtil.setInt(Individuality.this,"pushid",2);
                if (!JPushInterface.isPushStopped(Individuality.this)){
                    JPushInterface.stopPush(Individuality.this);
                }
            }
        });
        yaoyiyao.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                SharedperfencesUtil.setInt(Individuality.this,"yaoyiyaoid",1);
                if (JPushInterface.isPushStopped(Individuality.this)){
                    JPushInterface.resumePush(Individuality.this);
                }
            }else {
                SharedperfencesUtil.setInt(Individuality.this,"yaoyiyaoid",2);
                if (!JPushInterface.isPushStopped(Individuality.this)){
                    JPushInterface.stopPush(Individuality.this);
                }
            }
        });
        pushid = SharedperfencesUtil.getInt(this,"pushid");
        yaoyiyaoid = SharedperfencesUtil.getInt(this,"yaoyiyaoid");
        voiceid = SharedperfencesUtil.getInt(this,"voiceid");
        zodiacid = SharedperfencesUtil.getInt(this,"zodiacid");
        if (pushid==0){
            pushid = 1;
        }
        if (yaoyiyaoid==0){
            yaoyiyaoid = 2;
        }
        if (voiceid==0){
            voiceid = 2;
        }
        if (zodiacid==0){
            zodiacid = 1;
        }
        showPushCheckBox();
        showVoiceCheckBox();
        showYaoYiYaoCheckBox();
        showZodiacCheckBox();
    }

    private void showPushCheckBox(){
        if (pushid==1){
            push.setChecked(true);
        }else {
            push.setChecked(false);
        }
    }

    private void showYaoYiYaoCheckBox(){
        if (yaoyiyaoid==1){
            yaoyiyao.setChecked(true);
        }else {
            yaoyiyao.setChecked(false);
        }
    }
    private void showVoiceCheckBox(){
        if (voiceid==1){
            voice.setChecked(true);
        }else {
            voice.setChecked(false);
        }
    }
    private void showZodiacCheckBox(){
        if (zodiacid==1){
            radioGroup.check(R.id.indi_left);
        }else {
            radioGroup.check(R.id.indi_right);
        }
    }
    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {

    }
}

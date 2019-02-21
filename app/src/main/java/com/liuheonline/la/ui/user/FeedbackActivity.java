package com.liuheonline.la.ui.user;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.mvp.presenter.FeedBackPresenter;
import com.liuheonline.la.ui.base.BaseMVPActivity;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;
import com.yxt.itv.library.navigationbar.DefaultNavigationBar;

public class FeedbackActivity extends BaseMVPActivity<BaseView<Object>,FeedBackPresenter,Object> {

    private String token;
    @BindId(R.id.butn)
    private Button butn;
    @BindId(R.id.feedback_text)
    private EditText feedback_text;

    @Override
    protected void initPresenter() {
        presenter = new FeedBackPresenter();
    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.feedback_activity);
    }

    @OnClick({R.id.butn})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.butn:
                //提交反馈
                String content = feedback_text.getText().toString();
                if (content.trim().length()==0){
                    Toast.makeText(getApplicationContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    presenter.feedBack(content);
                }
                break;
        }
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("意见反馈")
                .setLeftIconVisibility(false)
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                })
                .builder();
    }



    @Override
    protected void initView() {
        feedback_text.setFocusable(true);
        feedback_text.setFocusableInTouchMode(true);
        feedback_text.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

}
    @Override
    public void onLoading() {

    }
    @Override
    public void onLoadFailed(int code, String error) {
        if (code!=200){
        }
    }

    @Override
    public void successed(Object o) {
        feedback_text.setText("");
       Toast.makeText(getApplicationContext(),"反馈已提交",Toast.LENGTH_SHORT).show();
       finish();
    }
}

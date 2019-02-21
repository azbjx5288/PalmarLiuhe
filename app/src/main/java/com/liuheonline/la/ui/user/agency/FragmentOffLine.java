package com.liuheonline.la.ui.user.agency;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.mvp.presenter.AddMemberPresenter;
import com.liuheonline.la.mvp.presenter.CheckMemberExistPresenter;
import com.liuheonline.la.mvp.presenter.WebPresenter;
import com.liuheonline.la.ui.base.BaseMvpFragment;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.liuheonline.la.utils.StringUtil;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.ioc.BindId;
import com.yxt.itv.library.ioc.OnClick;

//发展下线
public class FragmentOffLine extends BaseMvpFragment<BaseView<Object>,AddMemberPresenter,Object> {
    @BindId(R.id.check_left)
    TextView text_left;
    @BindId(R.id.check_right)
    TextView text_right;
    @BindId(R.id.left_linear)
    LinearLayout left_linear;
    @BindId(R.id.right_linear)
    LinearLayout right_linear;
    @BindId(R.id.offline_content)
    TextView text_content;

    WebPresenter webPresenter;
    @BindId(R.id.offline_agency)
    TextView agency;
    @BindId(R.id.offline_member)
    TextView member;

    @BindId(R.id.offline_account)
    EditText account;
    @BindId(R.id.offline_password)
    EditText password;
    @BindId(R.id.offline_hig)
    EditText hig;
    @BindId(R.id.offline_low)
    EditText low;

    CheckMemberExistPresenter checkMemberExistPresenter;
    int agencyid = 1;
    @OnClick({R.id.check_left,R.id.check_right,R.id.offline_copy,R.id.offline_member,R.id.offline_agency,R.id.offline_commit})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.check_left:
                text_left.setBackgroundResource(R.drawable.check_solid_left);
                text_left.setTextColor(getResources().getColor(R.color.white));
                text_right.setBackgroundResource(R.drawable.text_solid_none_shape);
                text_right.setTextColor(getResources().getColor(R.color.red));
                left_linear.setVisibility(View.VISIBLE);
                right_linear.setVisibility(View.GONE);
                break;
            case R.id.check_right:
                text_right.setBackgroundResource(R.drawable.check_solid_right);
                text_right.setTextColor(getResources().getColor(R.color.white));
                text_left.setBackgroundResource(R.drawable.text_solid_none_shape);
                text_left.setTextColor(getResources().getColor(R.color.red));
                left_linear.setVisibility(View.GONE);
                right_linear.setVisibility(View.VISIBLE);
                break;
            case R.id.offline_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(text_content.getText());
                Toast.makeText(getActivity().getApplicationContext(),"已复制到粘贴板",Toast.LENGTH_SHORT).show();
                break;
            case R.id.offline_member:
                member.setBackgroundResource(R.drawable.radius_solid_red);
                member.setTextColor(getResources().getColor(R.color.white));
                agency.setBackgroundResource(R.drawable.radius_stroke_red);
                agency.setTextColor(getResources().getColor(R.color.red));
                agencyid = 1;
                break;
            case R.id.offline_agency:
                agency.setBackgroundResource(R.drawable.radius_solid_red);
                agency.setTextColor(getResources().getColor(R.color.white));
                member.setBackgroundResource(R.drawable.radius_stroke_red);
                member.setTextColor(getResources().getColor(R.color.red));
                agencyid = 2;
                break;
            case R.id.offline_commit://提交
                if (account.getText().toString().length()!=11){
                    Toast.makeText(getActivity().getApplicationContext(),"账号输入有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().trim().length()<6){
                    Toast.makeText(getActivity().getApplicationContext(),"密码输入有误",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (Double.parseDouble(hig.getText().toString().trim())>1){
                    Toast.makeText(getActivity().getApplicationContext(),"反点输入有误",Toast.LENGTH_SHORT).show();
                    return;
                }*/
                checkMemberExistPresenter.checkMember(account.getText().toString());

                break;
        }
    }
    @Override
    protected void initPresenter() {
        presenter = new AddMemberPresenter();
        checkMemberExistPresenter = new CheckMemberExistPresenter();
        checkMemberExistPresenter.attachView(new BaseView<Object>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {
                Toast.makeText(getActivity().getApplicationContext(),"账号已被注册",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successed(Object o) {
                //用户不存在，可以注册
               // presenter.addMember(account.getText().toString(),password.getText().toString(),hig.getText().toString(),agencyid);
            }
        });
        webPresenter = new WebPresenter();
        webPresenter.attachView(new BaseView<WebEntity>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoadFailed(int code, String error) {

            }

            @Override
            public void successed(WebEntity webEntity) {
                String share_url = StringUtil.translation(webEntity.getShare_url());
                text_content.setText(share_url+SharedperfencesUtil.getInt(getContext(),"userId"));
            }

        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        if (SharedperfencesUtil.getInt(getContext(),"agent")==2){//当前角色为代理
            agency.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initTitle(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_offline;
    }

    @Override
    protected void fetchData() {
        webPresenter.getWeb();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void successed(Object o) {
        Toast.makeText(getActivity().getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
        getActivity().finish();

    }
}

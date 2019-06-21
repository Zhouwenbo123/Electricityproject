package com.bawei.electricityproject.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.electricityproject.R;
import com.bawei.electricityproject.api.Apis;
import com.bawei.electricityproject.base.BaseActivity;
import com.bawei.electricityproject.entity.LoginBean;
import com.bawei.electricityproject.presenter.IpresenterImpl;
import com.bawei.electricityproject.utils.MD5Utils;
import com.bawei.electricityproject.utils.RegularUtil;
import com.bawei.electricityproject.view.IView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements IView {
    IpresenterImpl mIpresenterImpl;
    @BindView(R.id.login_mobile)
    EditText mMobile;
    @BindView(R.id.login_pas)
    EditText mPas;
    @BindView(R.id.login_rem_pas)
    CheckBox mRemPas;
    private SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor editor;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mSharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        //互绑
        initPresenter();
    }

    private void initPresenter() {
        mIpresenterImpl = new IpresenterImpl(this);
    }

    @Override
    protected void initData() {
        //记住密码
        getEdit();
    }

   //登录
    @OnClick(R.id.login_btn)
    public  void  login(){
        //获取输入的手机号和密码
        String mobilde = mMobile.getText().toString();
        String pas = mPas.getText().toString();
        //进行判断
        if (RegularUtil.isNull(mobilde)){
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (RegularUtil.isNull(pas)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
      /*  if (RegularUtil.isPhone(mobilde)){
            Toast.makeText(this, "手机格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (RegularUtil.isPass(pas)){
            Toast.makeText(this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }*/
        //判断复选框是否选中
        if (mRemPas.isChecked()){
            //勾选中了
            editor.putString("phone",mobilde);
            editor.putString("pass",pas);
            editor.putBoolean("box_ischeck",true);
            editor.commit();
        }else{
            //清除所有的状态
            editor.clear();
            editor.commit();
        }
        //发送网络请求
        getUrl(mobilde,pas);

    }

    private void getUrl(String mobilde, String pas) {
        Map<String,String> params = new HashMap<>();
        params.put("phone",mobilde);
        params.put("pwd",MD5Utils.md5(pas));
        mIpresenterImpl.postRequestIpresenter(Apis.LOGIN_URL,params,LoginBean.class);
    }

    private void getEdit() {
        boolean box_ischeck = mSharedPreferences.getBoolean("box_ischeck", false);
        if (box_ischeck){
            String phone = mSharedPreferences.getString("phone", null);
            String pass = mSharedPreferences.getString("pass", null);
            mMobile.setText(phone);
            mPas.setText(pass);
            mRemPas.setChecked(true);
        }
    }

    private boolean pasVisibile = false;
    @OnClick(R.id.login_pas_eye)
    public void eyePas() {
        if (pasVisibile) {//密码显示，则隐藏
            mPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        } else {//密码隐藏则显示
            mPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }

    @OnClick(R.id.register_text)
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void success(Object object) {
        //看看LoginBean是否是一个实例
        if (object instanceof  LoginBean){
            LoginBean loginBean = (LoginBean) object;
            if ("0000".equals(loginBean.getStatus())){
                //跳转到主界面进行商品展示
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void failure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        mIpresenterImpl.deatch();
    }
}

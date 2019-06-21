package com.bawei.electricityproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.electricityproject.R;
import com.bawei.electricityproject.api.Apis;
import com.bawei.electricityproject.base.BaseActivity;
import com.bawei.electricityproject.entity.RegisterEntity;
import com.bawei.electricityproject.presenter.IpresenterImpl;
import com.bawei.electricityproject.utils.MD5Utils;
import com.bawei.electricityproject.utils.RegularUtil;
import com.bawei.electricityproject.view.IView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IView {
    IpresenterImpl mIpresenterImpl;
    @BindView(R.id.register_mobile)
    EditText rMobile;
    @BindView(R.id.register_pas)
    EditText rPas;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mIpresenterImpl = new IpresenterImpl(this);
    }


    @Override
    protected void initData() {

    }



    @OnClick(R.id.register_btn)
    public void register(){
        String rphone = rMobile.getText().toString();
        String rpwd = rPas.getText().toString();
        //手机号密码进行判断
        if (RegularUtil.isNull(rphone)){
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (RegularUtil.isNull(rpwd)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (RegularUtil.isPass(rpwd)){
            Toast.makeText(this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        getUrl(rphone,rpwd);

    }


    private void getUrl(String rphone, String rpwd) {
        HashMap<String,String> parmas = new HashMap<>();
        parmas.put("phone",rphone);
        parmas.put("pwd",MD5Utils.md5(rpwd));
        mIpresenterImpl.postRequestIpresenter(Apis.REG_URL,parmas,RegisterEntity.class);
    }

    @OnClick(R.id.register_text)
    public void text(){
        finish();
    }

    private boolean pasVisibile = false;
    @OnClick(R.id.register_pas_eye)
    public void eyePas(){
        if (pasVisibile){//密码显示，则隐藏
            rPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        }else{//密码隐藏则显示
            rPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }

    @Override
    public void success(Object object) {
        if (object instanceof RegisterEntity) {
            RegisterEntity registerBean = (RegisterEntity) object;
            if (registerBean.getStatus().equals("0000")) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
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

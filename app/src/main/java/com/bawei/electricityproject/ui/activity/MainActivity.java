package com.bawei.electricityproject.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bawei.electricityproject.R;
import com.bawei.electricityproject.base.BaseActivity;
import com.bawei.electricityproject.ui.fragment.BillFragment;
import com.bawei.electricityproject.ui.fragment.CircleFragment;
import com.bawei.electricityproject.ui.fragment.HomeFragment;
import com.bawei.electricityproject.ui.fragment.MyFragment;
import com.bawei.electricityproject.ui.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
  HomeFragment homeFragment;
  CircleFragment circleFragment;
  ShopFragment shopFragment;
  BillFragment billFragment;
  MyFragment myFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
       homeFragment = new HomeFragment();
       circleFragment = new CircleFragment();
       shopFragment = new ShopFragment();
       billFragment = new BillFragment();
       myFragment =  new MyFragment();
        setFragment(homeFragment);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.btn_home:
                       setFragment(homeFragment);
                       break;
                   case R.id.btn_friendster:
                       setFragment(circleFragment);
                       break;
                   case R.id.btn_shopping:
                       setFragment(shopFragment);
                       break;
                   case R.id.btn_order_form:
                       setFragment(billFragment);
                       break;
                   case R.id.btn_my:
                       setFragment(myFragment);
                       break;
               }
            }
        });

    }

    private void setFragment(Fragment  fragment) {
        //1.得到Fragment管理者
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        //2.打开事物
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        //加载Fragment  arg0,占位布局 arg1Fragment
        beginTransaction.replace(R.id.viewpager, fragment);
        beginTransaction.commit();

    }

    @Override
    protected void initData() {

    }
}

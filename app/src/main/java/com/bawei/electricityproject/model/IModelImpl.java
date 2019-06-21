package com.bawei.electricityproject.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bawei.electricityproject.application.MyApplication;
import com.bawei.electricityproject.net.NetCallback;
import com.bawei.electricityproject.net.RetrofitUtil;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/20 14:26,周文博
 * Description:
 */
public class IModelImpl implements  IModel{
    //get请求
    @Override
    public void getRequestModel(String url, final Class clazz, final NetCallback netCallback) {
        if (!isNetWork()){
            netCallback.failure("网络状态不可用");
        }
        RetrofitUtil.getInstance().get(url, new RetrofitUtil.ICallBack() {
            @Override
            public void success(String result) {
                Object object = getGson(result,clazz);
                netCallback.success(object);
            }

            @Override
            public void failure(String error) {
                netCallback.failure(error);
            }
        });
    }
    //post请求
    @Override
    public void postRequestModel(String url, Map<String, String> params, final Class clazz, final NetCallback netCallback) {
            if (!isNetWork()){
                netCallback.failure("网络状态不可用");
            }
            RetrofitUtil.getInstance().post(url, params, new RetrofitUtil.ICallBack() {
                @Override
                public void success(String result) {
                        Object object = getGson(result,clazz);
                        netCallback.success(object);
                }

                @Override
                public void failure(String error) {
                        netCallback.failure(error);
                }
            });
    }
    //gson解析
    private Object getGson(String result, Class clazz) {
        Object o = new Gson().fromJson(result, clazz);
        return o;
    }
    //判断网络状态
    public static boolean isNetWork() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.instance.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }
}

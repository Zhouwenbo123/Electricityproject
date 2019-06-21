package com.bawei.electricityproject.net;



import android.util.Log;

import com.bawei.electricityproject.api.Apis;
import com.bawei.electricityproject.api.ObservedApis;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/12 19:43,周文博
 * Description:
 */
public class RetrofitUtil {
    private  static RetrofitUtil instance;
    private ObservedApis mObservedApis;
    private Retrofit retrofit;

    public RetrofitUtil(){
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(10,TimeUnit.MINUTES)
                .readTimeout(10,TimeUnit.MINUTES)
                .connectTimeout(10,TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Apis.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mObservedApis = retrofit.create(ObservedApis.class);
    }
    /**
     * 双重检验锁机制
     * @return
     */
    public  static  RetrofitUtil getInstance(){
        if (instance == null){
            synchronized (RetrofitUtil.class){
                if (instance == null){
                    instance = new RetrofitUtil();
                }
            }
        }
        return  instance;
    }
   //get请求
    public  void  get(String url,ICallBack callBack){
        mObservedApis.get(url)
                //执行在哪个线程
        .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservier(callBack));
    }
    //post请求
    public  void  post(String url, Map<String,String> params,ICallBack callBack){
        if (params == null){
            params = new HashMap<>();
        }
        mObservedApis.post(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservier(callBack));
    }
    private Observer  getObservier(final ICallBack callBack){

        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (callBack!=null){
                    callBack.failure(e.getMessage());
                    Log.i("Tag",e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    if (callBack!=null){
                        callBack.success(result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (callBack!=null){
                        callBack.failure(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

   public  interface ICallBack{
        void  success(String result);
        void  failure(String error);
    }
}

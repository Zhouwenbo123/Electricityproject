package com.bawei.electricityproject.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
    public static Context instance;
    //绘制页面设计图宽
    public final static float DESIGN_WIDTH = 750;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
        //沉浸式状态栏
        immersive();
        //初始化Fresco
        initFresco();
        Fresco.initialize(this);
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void initFresco() {

    }

    private void immersive() {
        Point mPoint = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(mPoint);
        getResources().getDisplayMetrics().xdpi = mPoint.x / DESIGN_WIDTH * 72f;
        //DESIGN_WIDTH  设计宽度
    }

    public static Context getContext() {
        return instance;
    }
}

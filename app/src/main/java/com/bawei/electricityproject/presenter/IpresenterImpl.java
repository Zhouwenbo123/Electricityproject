package com.bawei.electricityproject.presenter;

import com.bawei.electricityproject.model.IModelImpl;
import com.bawei.electricityproject.net.NetCallback;
import com.bawei.electricityproject.view.IView;

import java.util.Map;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/20 14:39,周文博
 * Description:
 */
public class IpresenterImpl implements  Ipresenter{
    private IModelImpl mIModelImpl;
    private IView mIView;
    public  IpresenterImpl(IView mIView){
        this.mIView = mIView;
        //实例化
        mIModelImpl = new IModelImpl();
    }

    @Override
    public void getRequestIpresenter(String url, Class clazz) {
        mIModelImpl.getRequestModel(url, clazz, new NetCallback() {
            @Override
            public void success(Object object) {
                mIView.success(object);
            }

            @Override
            public void failure(String error) {
                mIView.failure(error);
            }
        });
    }

    @Override
    public void postRequestIpresenter(String url, Map<String, String> parmas, Class clazz) {
        mIModelImpl.postRequestModel(url,parmas,clazz, new NetCallback() {
            @Override
            public void success(Object object) {
                    mIView.success(object);
            }

            @Override
            public void failure(String error) {
                    mIView.failure(error);
            }
        });
    }

    //解绑
    public  void  deatch(){
        //解绑M层
        if (mIModelImpl!=null){
            mIModelImpl = null;
        }
        //解绑V层
        if (mIView!=null){
            mIView = null;
        }
    }
}

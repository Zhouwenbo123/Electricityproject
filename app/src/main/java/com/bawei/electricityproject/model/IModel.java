package com.bawei.electricityproject.model;

import com.bawei.electricityproject.net.NetCallback;

import java.util.Map;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/20 14:23,周文博
 * Description:
 */
public interface IModel {
    //get请求
    void  getRequestModel(String url, Class clazz, NetCallback netCallback);
    //post请求
    void  postRequestModel(String url, Map<String,String> params ,Class clazz, NetCallback netCallback);
}

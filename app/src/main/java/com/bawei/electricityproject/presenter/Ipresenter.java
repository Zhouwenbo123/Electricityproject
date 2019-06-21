package com.bawei.electricityproject.presenter;

import java.util.Map;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/20 14:36,周文博
 * Description:
 */
public interface Ipresenter {
    //get请求
    void  getRequestIpresenter(String url,Class clazz);
    //post请求
    void  postRequestIpresenter(String url, Map<String,String> parmas,Class clazz);
}

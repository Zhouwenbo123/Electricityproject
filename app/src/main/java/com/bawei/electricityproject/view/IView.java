package com.bawei.electricityproject.view;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/20 14:41,周文博
 * Description:
 */
public interface IView {
    void  success(Object object);//请求成功
    void  failure(String error);////请求失败  服务器宕机,,
}

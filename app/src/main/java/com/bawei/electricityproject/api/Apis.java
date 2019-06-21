package com.bawei.electricityproject.api;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/12 20:01,周文博
 * Description:
 */
public class Apis {
    public  static  boolean isRelease = false;//测试环境还是正式环境
    public static final String BASE_URL = isRelease?"http://mobile.bwstudent.com/small/":"http://172.17.8.100/small/";
    //登录
    public static  final String LOGIN_URL ="user/v1/login";
    //注册
    public static  final String REG_URL ="user/v1/register";
    //首页轮播图的接口
    public static final String SHOW_BANNER_URL="commodity/v1/bannerShow";
    //首页商品的接口
    public static final String SHOW_SHOP_URL="commodity/v1/commodityList";
}

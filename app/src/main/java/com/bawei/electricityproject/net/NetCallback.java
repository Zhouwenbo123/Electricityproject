package com.bawei.electricityproject.net;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/17 20:02,周文博
 * Description:
 */
public interface NetCallback {
    void success(Object object);
    void failure(String error);
}

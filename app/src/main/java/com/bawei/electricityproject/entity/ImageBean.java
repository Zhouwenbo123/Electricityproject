package com.bawei.electricityproject.entity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/21 11:09,周文博
 * Description:
 */
public class ImageBean extends SimpleBannerInfo {
    public String imgUrl;
    @Override
    public Object getXBannerUrl() {
        return imgUrl;
    }
}

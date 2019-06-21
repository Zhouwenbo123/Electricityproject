package com.bawei.electricityproject.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.electricityproject.R;
import com.bawei.electricityproject.adapter.ShowFashionShopAdapter;
import com.bawei.electricityproject.adapter.ShowLiveAdapter;
import com.bawei.electricityproject.adapter.ShowNewShopAdapter;
import com.bawei.electricityproject.api.Apis;
import com.bawei.electricityproject.base.BaseFragment;
import com.bawei.electricityproject.entity.HomeBannerBean;
import com.bawei.electricityproject.entity.ImageBean;
import com.bawei.electricityproject.entity.ShowShopBean;
import com.bawei.electricityproject.presenter.IpresenterImpl;
import com.bawei.electricityproject.view.IView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/6/12 20:30,周文博
 * Description:
 */
public class HomeFragment extends BaseFragment implements IView {
    @BindView(R.id.cate_list)
    ImageView show_navigation;
    @BindView(R.id.search)
    ImageView show_search;
    @BindView(R.id.show_new_recy)
    RecyclerView show_new_recy;
    private ShowNewShopAdapter showNewShopAdapter;
    @BindView(R.id.show_fashion_recy)
    RecyclerView show_fashion_recy;
    private ShowFashionShopAdapter showFashionShopAdapter;
    @BindView(R.id.show_live_recy)
    RecyclerView show_live_recy;
    private ShowLiveAdapter showLiveAdapter;
    private IpresenterImpl mIpresenterImpl;
    List<ImageBean> listCarousel = new ArrayList<>(); //播放轮播的图片集合
    @BindView(R.id.home_banner)
    XBanner xBanner;
    private final int COUNT_ITEM = 2;
    private ShowShopBean.ResultBean.RxxpBean newlist;
    private  ShowShopBean.ResultBean.MlssBean fashionlist;
    private  ShowShopBean.ResultBean.PzshBean livelist;
    @BindView(R.id.show_image_new)
    ImageView show_image_new;
    @BindView(R.id.show_image_fashion)
    ImageView show_image_fashion;
    @BindView(R.id.show_image_live)
    ImageView show_image_live;

    @Override
    protected int getlayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        //互绑
        mIpresenterImpl = new IpresenterImpl(this);
    }

    @Override
    protected void initData() {
        carousel(); //轮播图
        //发送商品的请求
        initShopUrl();
        //热销商品
        initNewRecy();
        //魔力时尚
        initFashionRecy();
        //品质生活
        iniyLiveRecy();
    }

    private void iniyLiveRecy() {
        //布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), COUNT_ITEM);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        show_live_recy.setLayoutManager(gridLayoutManager);
        //设置适配器
        showLiveAdapter = new ShowLiveAdapter(getActivity());
        show_live_recy.setAdapter(showLiveAdapter);
    }

    private void initFashionRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        show_fashion_recy.setLayoutManager(linearLayoutManager);
        //设置适配器
        showFashionShopAdapter = new ShowFashionShopAdapter(getActivity());
        show_fashion_recy.setAdapter(showFashionShopAdapter);
    }

    private void initNewRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        show_new_recy.setLayoutManager(linearLayoutManager);
        //设置适配器
        showNewShopAdapter = new ShowNewShopAdapter(getActivity());
        show_new_recy.setAdapter(showNewShopAdapter);
    }

    private void initShopUrl() {
        mIpresenterImpl.getRequestIpresenter(Apis.SHOW_SHOP_URL, ShowShopBean.class);
    }

    private void carousel() {
        //Banner
        mIpresenterImpl.getRequestIpresenter(Apis.SHOW_BANNER_URL,HomeBannerBean.class);
        xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(getActivity(), "点击了" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void success(Object object) {
        //banner
        if (object instanceof  HomeBannerBean){
            HomeBannerBean homeBannerBean = (HomeBannerBean) object;
           List<HomeBannerBean.ResultBean> result = homeBannerBean.getResult();
            if (listCarousel.size() == 0) {//如果集合为空
                //则添加
                for (int i = 0; i < result.size(); i++) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.imgUrl = result.get(i).getImageUrl();
                    listCarousel.add(imageBean);
                }
            }
            // 为XBanner绑定数据
            xBanner.setBannerData(listCarousel);
            // XBanner适配数据
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide
                            .with(getContext())
                            .load(((ImageBean) model).getXBannerUrl())
                            .into((ImageView) view);
                }
            });
        }
        //商品信息
        if (object instanceof ShowShopBean) {
            ShowShopBean showShopBean = (ShowShopBean) object;

            newlist = showShopBean.getResult().getRxxp();
            fashionlist = showShopBean.getResult().getMlss();
            livelist = showShopBean.getResult().getPzsh();
            //新品热销
            showNewShopAdapter.setList(showShopBean.getResult().getRxxp().getCommodityList());
            //魔力时尚
            showFashionShopAdapter.setList(showShopBean.getResult().getMlss().getCommodityList());
            //品质生活
            showLiveAdapter.setList(showShopBean.getResult().getPzsh().getCommodityList());
        }
    }

    @Override
    public void failure(String error) {
        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
    }
}

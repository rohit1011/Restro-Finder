package com.nextnepalit.ecommerce;

import android.app.Application;

import com.nextnepalit.ecommerce.utils.PicassoImageLoadingService;

import ss.com.bannerslider.Slider;

public class EcommerceApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Slider.init(new PicassoImageLoadingService());

    }
}

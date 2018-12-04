package com.nextnepalit.ecommerce.ui.adapters;

import com.nextnepalit.ecommerce.data.models.SlideShow;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    private List<SlideShow> mSliderList;
    public MainSliderAdapter(List<SlideShow> sliderList){
        mSliderList = sliderList;
    }
    @Override
    public int getItemCount() {
        if (mSliderList == null){
            return 0;
        }
        else {
            return mSliderList.size();
        }
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(mSliderList.get(position).getImage());
    }
}
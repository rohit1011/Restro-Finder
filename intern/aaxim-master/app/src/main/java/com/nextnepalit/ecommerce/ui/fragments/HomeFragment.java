package com.nextnepalit.ecommerce.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.HomeData;
import com.nextnepalit.ecommerce.data.models.SlideShow;
import com.nextnepalit.ecommerce.ui.adapters.CategoriesRecyclerAdapter;
import com.nextnepalit.ecommerce.ui.adapters.FeaturedProductsRecyclerAdapter;
import com.nextnepalit.ecommerce.ui.adapters.MainSliderAdapter;
import com.nextnepalit.ecommerce.utils.Connectivity;
import com.nextnepalit.ecommerce.viewmodels.HomeViewModel;
import com.squareup.picasso.Picasso;

import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {

    FeaturedProductsRecyclerAdapter sectionOneProductsRecyclerAdapter, featuredProductsRecyclerAdapter, sectionFourProductsRecyclerAdapter,sectionFiveProductsRecyclerAdapter,sectionSixProductsRecyclerAdapter,sectionSevenProductsRecyclerAdapter;
    CategoriesRecyclerAdapter categoriesRecyclerAdapter;
    LinearLayoutManager layoutManager, layoutManager2, layoutManager3, layoutManager4;
    RecyclerView categoriesRecyclerView, sectionOneProductRecyclerView, featuredProductsRecyclerView, sectionFourProductsRecyclerView, sectionFiveProductsRecyclerView, sectionSixProductsRecyclerView, sectionSevenProductsRecyclerView;
    private MainSliderAdapter mainSliderAdapter;
    ImageView deals_image, ad2_image,ad3_image;
    private HomeViewModel mViewModel;
    private Slider mainBannerSlider;
    private TextView sectionOne_textview, featured_textview, sectionFour_textview, sectionfive_textview, sectionsix_textview, sectionseven_textview;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        if (mViewModel == null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        }
        mainBannerSlider = view.findViewById(R.id.mainBannerSlider);
        deals_image = view.findViewById(R.id.deals_image);
        ad2_image = view.findViewById(R.id.ad2_image);
        ad3_image = view.findViewById(R.id.ad3_image);

        sectionOne_textview = view.findViewById(R.id.sectionOne_textview);
        sectionOneProductRecyclerView = view.findViewById(R.id.sectionOneProductsRecyclerView);
        sectionOneProductsRecyclerAdapter = new FeaturedProductsRecyclerAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        sectionOneProductRecyclerView.setLayoutManager(layoutManager);
        sectionOneProductRecyclerView.setAdapter(sectionOneProductsRecyclerAdapter);

        featured_textview = view.findViewById(R.id.featured_textview);
        featuredProductsRecyclerView = view.findViewById(R.id.featuredProductsRecyclerView);
        layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        featuredProductsRecyclerAdapter = new FeaturedProductsRecyclerAdapter(getContext());
        featuredProductsRecyclerView.setLayoutManager(layoutManager2);
        featuredProductsRecyclerView.setAdapter(featuredProductsRecyclerAdapter);

        sectionFour_textview = view.findViewById(R.id.sectionFour_textview);
        sectionFourProductsRecyclerView = view.findViewById(R.id.sectionFourProductsRecyclerView);
        layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        sectionFourProductsRecyclerAdapter = new FeaturedProductsRecyclerAdapter(getContext());
        sectionFourProductsRecyclerView.setLayoutManager(layoutManager3);
        sectionFourProductsRecyclerView.setAdapter(sectionFourProductsRecyclerAdapter);

        sectionfive_textview = view.findViewById(R.id.sectionfive_textview);
        sectionFiveProductsRecyclerView = view.findViewById(R.id.sectionfiveRecyclerView);
        layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        sectionFiveProductsRecyclerAdapter = new FeaturedProductsRecyclerAdapter(getContext());
        sectionFiveProductsRecyclerView.setLayoutManager(layoutManager3);
        sectionFiveProductsRecyclerView.setAdapter(sectionFiveProductsRecyclerAdapter);

        sectionsix_textview = view.findViewById(R.id.sectionsix_textview);
        sectionSixProductsRecyclerView = view.findViewById(R.id.sectionsixRecyclerView);
        layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        sectionSixProductsRecyclerAdapter = new FeaturedProductsRecyclerAdapter(getContext());
        sectionSixProductsRecyclerView.setLayoutManager(layoutManager3);
        sectionSixProductsRecyclerView.setAdapter(sectionSixProductsRecyclerAdapter);

        sectionseven_textview = view.findViewById(R.id.sectionseven_textview);
        sectionSevenProductsRecyclerView = view.findViewById(R.id.sectionsevenRecyclerView);
        layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        sectionSevenProductsRecyclerAdapter = new FeaturedProductsRecyclerAdapter(getContext());
        sectionSevenProductsRecyclerView.setLayoutManager(layoutManager3);
        sectionSevenProductsRecyclerView.setAdapter(sectionSevenProductsRecyclerAdapter);

        categoriesRecyclerView = view.findViewById(R.id.categories_recyclerview);
        layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerAdapter = new CategoriesRecyclerAdapter(getContext());
        categoriesRecyclerView.setLayoutManager(layoutManager4);
        categoriesRecyclerView.setAdapter(categoriesRecyclerAdapter);

        if (Connectivity.isConnected(getContext()) && Connectivity.isConnectedFast(getContext())) {
            mViewModel.getHomedata().observe(this, new Observer<HomeData>() {
                @Override
                public void onChanged(@Nullable HomeData homeData) {
                    sectionOne_textview.setText(homeData.getData().getSectionone().getTitle());
                    featured_textview.setText(homeData.getData().getSectionthree().getTitle());
                    sectionFour_textview.setText(homeData.getData().getSectionfour().getTitle());
                    sectionfive_textview.setText(homeData.getData().getSectionfive().getTitle());
                    sectionsix_textview.setText(homeData.getData().getSectionsix().getTitle());
                    sectionseven_textview.setText(homeData.getData().getSectionseeven().getTitle());

                    sectionOneProductsRecyclerAdapter.setFeaturedProductsList(homeData.getData().getSectionone().getSectionData());
                    featuredProductsRecyclerAdapter.setFeaturedProductsList(homeData.getData().getSectionthree().getSectionData());
                    sectionFourProductsRecyclerAdapter.setFeaturedProductsList(homeData.getData().getSectionfour().getSectionData());
                    sectionFiveProductsRecyclerAdapter.setFeaturedProductsList(homeData.getData().getSectionfive().getSectionData());
                    sectionSixProductsRecyclerAdapter.setFeaturedProductsList(homeData.getData().getSectionsix().getSectionData());
                    sectionSevenProductsRecyclerAdapter.setFeaturedProductsList(homeData.getData().getSectionseeven().getSectionData());
                    categoriesRecyclerAdapter.setCategoryList(homeData.getData().getCategories());

                    Picasso.get().load(Uri.parse(homeData.getData().getAdsone())).into(deals_image);
                    Picasso.get().load(Uri.parse(homeData.getData().getAdstwo())).into(ad2_image);
                    Picasso.get().load(Uri.parse(homeData.getData().getAdsthree())).into(ad3_image);
                    mainSliderAdapter = new MainSliderAdapter(homeData.getData().getSlideshows());
                    mainBannerSlider.setAdapter(mainSliderAdapter);

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
    }

    private LinearLayoutManager newLLM() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }
}

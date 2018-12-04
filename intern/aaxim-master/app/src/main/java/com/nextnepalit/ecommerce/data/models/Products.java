package com.nextnepalit.ecommerce.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("latestProducts")
    @Expose
    private List<LatestProducts> latestProducts = null;
    @SerializedName("featuredProducts")
    @Expose
    private List<FeaturedProducts> featuredProducts = null;
    @SerializedName("popularProducts")
    @Expose
    private List<PopularProducts> popularProducts = null;

    public List<LatestProducts> getLatestProducts() {
        return latestProducts;
    }

    public void setLatestProducts(List<LatestProducts> latestProducts) {
        this.latestProducts = latestProducts;
    }

    public List<FeaturedProducts> getFeaturedProducts() {
        return featuredProducts;
    }

    public void setFeaturedProducts(List<FeaturedProducts> featuredProducts) {
        this.featuredProducts = featuredProducts;
    }

    public List<PopularProducts> getPopularProducts() {
        return popularProducts;
    }

    public void setPopularProducts(List<PopularProducts> popularProducts) {
        this.popularProducts = popularProducts;
    }

}
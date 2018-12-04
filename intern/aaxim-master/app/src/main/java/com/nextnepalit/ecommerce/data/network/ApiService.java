package com.nextnepalit.ecommerce.data.network;

import com.nextnepalit.ecommerce.data.models.CategoryData;
import com.nextnepalit.ecommerce.data.models.HomeData;
import com.nextnepalit.ecommerce.data.models.ProductDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("category")
    Single<CategoryData> getCategoriesData();

    @GET("front")
    Single<HomeData> getAllHomeData();

    @GET("product/{id}")
    Single<ProductDetails> getProductDetails(@Path("id") String id);
}

package com.nextnepalit.ecommerce.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nextnepalit.ecommerce.data.models.HomeData;
import com.nextnepalit.ecommerce.data.models.ProductDetails;
import com.nextnepalit.ecommerce.data.network.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDescriptionViewModel extends ViewModel {

    private MutableLiveData<ProductDetails> productDetailsMutableLiveData;
    public LiveData<ProductDetails> getProductDetails(String id){
        if (productDetailsMutableLiveData == null){
            productDetailsMutableLiveData = new MutableLiveData<>();
            loadProductDetailsData(id);
        }
        return productDetailsMutableLiveData;
    }

    private void loadProductDetailsData(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aaxim.com/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getProductDetails(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductDetails>() {
                    @Override
                    public void accept(ProductDetails productDetails) throws Exception {
                        productDetailsMutableLiveData.setValue(productDetails);
                    }
                });
    }
}

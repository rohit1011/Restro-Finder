package com.nextnepalit.ecommerce.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nextnepalit.ecommerce.data.models.Categories;
import com.nextnepalit.ecommerce.data.models.CategoryData;
import com.nextnepalit.ecommerce.data.models.HomeData;
import com.nextnepalit.ecommerce.data.network.ApiService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<HomeData> homeDataList;
    private MutableLiveData<List<Categories>>categoriesMutableLiveData;

    public LiveData<HomeData> getHomedata() {
        if (homeDataList == null) {
            homeDataList = new MutableLiveData<>();
            loadHomeData();
        }
        return homeDataList;
    }

    public LiveData<List<Categories>> getCategoriesData() {
        if (categoriesMutableLiveData == null) {
            categoriesMutableLiveData = new MutableLiveData<>();
            loadHomeData();
        }
        return categoriesMutableLiveData;
    }

    private void loadHomeData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aaxim.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getAllHomeData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeData>() {
                    @Override
                    public void accept(HomeData homeData) throws Exception {
                        homeDataList.setValue(homeData);
                    }
                });
        apiService.getCategoriesData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryData>() {
                    @Override
                    public void accept(CategoryData categoryData) throws Exception {
                        categoriesMutableLiveData.setValue(categoryData.getData());
                    }
                });

    }

}

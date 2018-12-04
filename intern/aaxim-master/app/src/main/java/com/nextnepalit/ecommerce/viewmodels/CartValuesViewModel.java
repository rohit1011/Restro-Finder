package com.nextnepalit.ecommerce.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nextnepalit.ecommerce.data.models.CartValues;
import com.nextnepalit.ecommerce.data.repos.CartItemsRepo;

import java.util.List;

public class CartValuesViewModel extends AndroidViewModel {

    private CartItemsRepo cartItemsRepo;
    private LiveData<List<CartValues>> cartValueList;

    public CartValuesViewModel(@NonNull Application application) {
        super(application);
        cartItemsRepo = new CartItemsRepo(application);
        cartValueList = cartItemsRepo.getCartValuesList();
    }

    public LiveData<List<CartValues>> getCartValueList(){
        return cartValueList;
    }

    public void deleteData(int id){
        cartItemsRepo.deleteData(id);
    }

    public LiveData<List<CartValues>> getCartValue(int id){
        return cartItemsRepo.getCartValue(id);
    }

    public void setCartValueList(CartValues cartValue) {
        cartItemsRepo.insertData(cartValue);
    }
    public void updateCartAmount(int id, int amount){
        cartItemsRepo.updateCartAmount(id, amount);
    }
}

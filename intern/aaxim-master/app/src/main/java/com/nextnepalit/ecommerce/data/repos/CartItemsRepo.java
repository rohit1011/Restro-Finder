package com.nextnepalit.ecommerce.data.repos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.nextnepalit.ecommerce.data.AppDatabase;
import com.nextnepalit.ecommerce.data.models.CartValues;

import java.util.List;

public class CartItemsRepo {
    private CartValueDao cartValueDao;
    private LiveData<List<CartValues>> cartValuesList;

    public CartItemsRepo(Application application){
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        cartValueDao = appDatabase.userDao();
        cartValuesList = cartValueDao.getAllCartValues();
    }

    public LiveData<List<CartValues>> getCartValuesList(){
        return cartValuesList;
    }

    public LiveData<List<CartValues>> getCartValue(int id){
        return cartValueDao.getCartValue(id);
    }

    public void insertData(CartValues cartValues){
        new insertAsyncTask(cartValueDao).execute(cartValues);
    }
    public void updateCartAmount(int id, int amount){
        IdAmountModel idAmountModel = new IdAmountModel();
        idAmountModel.setId(id);
        idAmountModel.setAmount(amount);
        new updateCartAsyncTask(cartValueDao).execute(idAmountModel);
    };

    public void deleteData(int id){
        new deleteAsync(cartValueDao).execute(id);
    }

    private static class insertAsyncTask extends AsyncTask<CartValues, Void, Void>{
        private CartValueDao cartValueDao;

        insertAsyncTask(CartValueDao cartValueDao){
            this.cartValueDao = cartValueDao;
        }
        @Override
        protected Void doInBackground(CartValues... cartValues) {
            cartValueDao.insertIntoCart(cartValues[0]);
            return null;
        }
    }


    private static class deleteAsync extends AsyncTask<Integer, Void, Void>{
        private CartValueDao cartValueDao;

        deleteAsync(CartValueDao cartValueDao){
            this.cartValueDao = cartValueDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            cartValueDao.deleteItemFromCart(integers[0]);
            return null;
        }
    }

    private static class updateCartAsyncTask extends AsyncTask<IdAmountModel, Void, Void>{
        private CartValueDao cartValueDao;
        updateCartAsyncTask(CartValueDao cartValueDao){
            this.cartValueDao = cartValueDao;
        }
        @Override
        protected Void doInBackground(IdAmountModel... idAmountModels) {
            cartValueDao.updateCartItems(idAmountModels[0].getId(), idAmountModels[0].getAmount());
            return null;
        }
    }

    private class IdAmountModel{
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        int id;
        int amount;
    }
}

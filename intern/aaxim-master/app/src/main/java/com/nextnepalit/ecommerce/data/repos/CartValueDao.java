package com.nextnepalit.ecommerce.data.repos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nextnepalit.ecommerce.data.models.CartValues;

import java.util.List;

@Dao
public interface CartValueDao {
    @Query("SELECT * FROM cartvalues")
    LiveData<List<CartValues>> getAllCartValues();

    @Query("SELECT * FROM cartvalues WHERE id = :id")
    LiveData<List<CartValues>> getCartValue(int id);

    @Query("UPDATE cartvalues SET numberOfItems = :amount WHERE id = :id")
    void updateCartItems(int id, int amount);

    @Query("DELETE FROM cartValues")
    void deleteAllCart();

    @Query("DELETE FROM cartvalues WHERE id = :id")
    void deleteItemFromCart(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIntoCart(CartValues cartValues);
}

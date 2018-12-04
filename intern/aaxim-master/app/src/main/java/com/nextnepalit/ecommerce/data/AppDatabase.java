package com.nextnepalit.ecommerce.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nextnepalit.ecommerce.data.models.CartValues;
import com.nextnepalit.ecommerce.data.repos.CartValueDao;

@Database(entities = {CartValues.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartValueDao userDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "database").build();
                }
            }
        }
        return INSTANCE;
    }

}


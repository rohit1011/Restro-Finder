package com.wa_v_er.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wa-v-er on 12/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name ="registerdb";
    static int version = 7;
    SQLiteDatabase db = this.getWritableDatabase();
String createrest="CREATE TABLE if not exists `resturant`" +
        " ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "`name` TEXT, " +
        "`address` TEXT, " +
        "`otime` INTEGER," +
        " `ctime` INTEGER," +
        "`lat` REAL, " +
        "`long` DOUBLE,"+
    "`alls` INTEGER)";
    //private static final String upgrade = "ALTER TABLE "
      //      + "resturant" + " ADD COLUMN " + "alls" + " INTEGER;";
    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createrest);
    }
    public void Restinfo(ContentValues cv){
        getWritableDatabase().insert("resturant","",cv);
    }

    public void delete()
    {
        db.delete("resturant",null,null);

    }

    public ArrayList<Restinfo> getrestList() {
        ArrayList<Restinfo> list = new ArrayList<>();
        String getrestlistsql = "SELECT * FROM `resturant`";
        Cursor c = getReadableDatabase().rawQuery(getrestlistsql, null);

        while (c.moveToNext()) {
            Restinfo Restinfo= new Restinfo();
            Restinfo.id = c.getString(c.getColumnIndex("id"));
            Restinfo.name = c.getString(c.getColumnIndex("name"));
            Restinfo.address = c.getString(c.getColumnIndex("address"));
            Restinfo.otime = Integer.parseInt(c.getString(c.getColumnIndex("otime")));
            Restinfo.ctime= Integer.parseInt(c.getString(c.getColumnIndex("ctime")));
            Restinfo.lat = Float.parseFloat(c.getString(c.getColumnIndex("lat")));
            Restinfo.longi = Float.parseFloat(c.getString(c.getColumnIndex("long")));
            Restinfo.alls = Integer.parseInt(c.getString(c.getColumnIndex("alls")));

            list.add(Restinfo);
        }
        c.close();
        return list;


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(createrest);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /*if (oldVersion<7){
           db.execSQL(upgrade);
           Log.i(""+oldVersion, "onUpgrade: "+newVersion);
       }
*/
    }

}

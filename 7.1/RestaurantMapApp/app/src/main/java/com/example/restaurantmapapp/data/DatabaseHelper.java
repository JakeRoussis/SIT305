package com.example.restaurantmapapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import androidx.annotation.Nullable;

import com.example.restaurantmapapp.model.Restaurant;
import com.example.restaurantmapapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" +
                Util.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Util.NAME + " TEXT," +
                Util.LAT + " REAL," +
                Util.LON + " REAL)";
        db.execSQL(CREATE_RESTAURANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE = "DROP TABLE IF EXISTS";
        db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);
    }

    public long insertLocation(Restaurant restaurant)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, restaurant.getName());
        contentValues.put(Util.LAT, restaurant.getLat());
        contentValues.put(Util.LON, restaurant.getLon());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);

        db.close();
        return newRowId;
    }


    public List<Restaurant> fetchAllLocations()
    {
        List<Restaurant> restaurantlist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String selectAllLocations = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectAllLocations, null);

        if (cursor.moveToFirst())
        {
            do {
                Restaurant restaurant = new Restaurant();

                restaurant.setName(cursor.getString(cursor.getColumnIndex(Util.NAME)));
                restaurant.setLat(cursor.getDouble(cursor.getColumnIndex(Util.LAT)));
                restaurant.setLon(cursor.getDouble(cursor.getColumnIndex(Util.LON)));

                restaurantlist.add(restaurant);

            } while (cursor.moveToNext());
        }

        db.close();;

        return restaurantlist;
    }

    public boolean checkData()
    {
        SQLiteDatabase db = getReadableDatabase();
        String countData = "SELECT count(*) FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(countData, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) return true;
        else return false;
    }
}

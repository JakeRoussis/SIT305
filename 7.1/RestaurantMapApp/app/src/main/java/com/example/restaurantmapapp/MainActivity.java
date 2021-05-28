package com.example.restaurantmapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.restaurantmapapp.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
    }

    public void addRestaurants(View v)
    {
        Intent addPlaceIntent = new Intent(this, AddRestaurant.class);
        startActivity(addPlaceIntent);
    }

    public void showRestaurants(View v)
    {
        if (db.checkData()) {
            Intent showAllIntent = new Intent(this, RestaurantMap.class);
            startActivity(showAllIntent);
        } else {
            Toast.makeText(this, "You must add a restaurant first!", Toast.LENGTH_SHORT).show();
        }
    }
}
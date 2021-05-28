package com.example.restaurantmapapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.restaurantmapapp.data.DatabaseHelper;
import com.example.restaurantmapapp.model.Restaurant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper db;
    List<Restaurant> restaurantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHelper(this);
        restaurantList = db.fetchAllLocations();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Loop for each entry in location table
        for (int i = 0; i < restaurantList.size(); i++)
        {
            // Retrieving the data
            String name = restaurantList.get(i).getName();
            double lat = restaurantList.get(i).getLat();
            double lon = restaurantList.get(i).getLon();

            // Setting up the marker
            LatLng location = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(location).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8));
        }
    }
}

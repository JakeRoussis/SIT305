package com.example.restaurantmapapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.restaurantmapapp.data.DatabaseHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class AddRestaurant extends AppCompatActivity {
    // Declaring Variables
    EditText editTextPlaceName, editTextLocation;
    double lat, lon;
    String placeName;

    LocationManager locationManager;
    LocationListener locationListener;
    AutocompleteSupportFragment autocompleteSupportFragment;
    DatabaseHelper db;

    private static final String TAG = "Running";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        editTextPlaceName = findViewById(R.id.editTextPlaceName);
        editTextLocation = findViewById(R.id.editTextLocation);
        lat = 0;
        lon = 0;
        db = new DatabaseHelper(this);

        setupPlaces();
        setupLocationManager();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0, locationListener);
            }
        }
    }

    public void locationButtonClicked(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        StringBuilder string = new StringBuilder();
        string.append(lat);
        string.append(" , ");
        string.append(lon);

        editTextLocation.setText(string);
    }

    public void showMapButtonClicked(View view)
    {
        if (lat != 0 && lon != 0)  // If the user gave the location
        {
            placeName = editTextPlaceName.getText().toString();

            if (placeName.matches("")) {
                Toast.makeText(this, "A location name must be entered!", Toast.LENGTH_SHORT).show();
            } else {
                Intent showMapIntent = new Intent(this, ShowMap.class);
                showMapIntent.putExtra("lat", lat);
                showMapIntent.putExtra("lon", lon);
                showMapIntent.putExtra("placeName", placeName);
                startActivity(showMapIntent);
            }
        } else {
            Toast.makeText(this, "Select a location!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveButtonClicked(View view)
    {
        if (lat != 0 && lon != 0)
        {
            placeName = editTextPlaceName.getText().toString();

            if (placeName.matches("")) {
                Toast.makeText(this, "A location name must be entered!", Toast.LENGTH_SHORT).show();
            } else {
                com.example.restaurantmapapp.model.Restaurant restaurant = new com.example.restaurantmapapp.model.Restaurant();
                restaurant.setName(placeName);
                restaurant.setLat(lat);
                restaurant.setLon(lon);

                long result = db.insertLocation(restaurant);

                if (result > 0) {
                    Toast.makeText(this, "Location saved!", Toast.LENGTH_SHORT).show();
                    Intent finishedIntent = new Intent(AddRestaurant.this, MainActivity.class);
                    startActivity(finishedIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Operation failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Select a location!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(AddRestaurant.this, MainActivity.class);
        startActivity(backIntent);
        finish();
    }


    private void setupPlaces() {
        // Initialize the SDK
        Places.initialize(getApplicationContext(), getString(R.string.PLACES_API_KEY));
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {

                lat = place.getLatLng().latitude;
                lon = place.getLatLng().longitude;
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    private void setupLocationManager() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(@NonNull Location location) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}
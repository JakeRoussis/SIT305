package com.example.tourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlacesAdapter.clickListener {

    private RecyclerView destinationRecyclerView, placesRecyclerView;
    private DestinationAdapter destinationAdapter;
    private PlacesAdapter placesAdapter;
    List<Destination> destinationList = new ArrayList<>();
    List<Places> placesList = new ArrayList<>();
    int [] imageListH = {R.drawable.eureka_tower, R.drawable.empire_state_building, R.drawable.golden_gate_bridge, R.drawable.great_wall_china};
    int [] imageList = {R.drawable.eureka_tower, R.drawable.empire_state_building, R.drawable.golden_gate_bridge, R.drawable.great_wall_china};
    String [] titleList = {"Eureka Tower", "Empire State Building", "Golden Gate Bridge", "Great Wall of China"};
    String [] descriptionList = {"Eureka Tower is a 297.3 m skyscraper located in the Southbank precinct of Melbourne, Victoria, Australia. Construction began in August 2002 and the exterior was completed on 1 June 2006",
            "The Empire State Building is a 102-story Art Deco skyscraper in Midtown Manhattan in New York City, United States. It was designed by Shreve, Lamb and Harmon and built from 1930 to 1931",
            "The Golden Gate Bridge is a suspension bridge spanning the Golden Gate, the one-mile-wide strait connecting San Francisco Bay and the Pacific Ocean.",
            "The Great Wall of China is a series of fortifications that were built across the historical northern borders of ancient Chinese states and Imperial China as protection against various nomadic groups from the Eurasian Steppe."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        destinationRecyclerView = findViewById(R.id.destinationRecyclerView);
        destinationAdapter = new DestinationAdapter(destinationList, MainActivity.this);
        destinationRecyclerView.setAdapter(destinationAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        destinationRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(destinationRecyclerView.HORIZONTAL);
        for (int i = 0; i < imageListH.length; i++) {
            Destination destination = new Destination(i, imageListH[i]);
            destinationList.add(destination);
        }

        placesRecyclerView = findViewById(R.id.placesRecyclerView);
        placesAdapter = new PlacesAdapter(placesList, this,this);
        placesRecyclerView.setAdapter(placesAdapter);
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < imageList.length; i++) {
            Places places = new Places(i, imageList[i], titleList[i], descriptionList[i]);
            placesList.add(places);
        }
    }

    @Override
    public void onClick(int position){
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new EurekaTowerFragment();
                break;
            case 1:
                fragment = new EmpireStateFragment();
                break;
            case 2:
                fragment = new GoldenGateFragment();
                break;
            case 3:
                fragment = new GreatWallFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() < 1 ) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("fragment");
            fragmentTransaction.replace(R.id.fragment, fragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
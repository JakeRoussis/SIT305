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

==========================================================================================================
##########################################################################################################
##########################################################################################################
==========================================================================================================

package com.example.tourism;

public class Places {

    private int image, id;
    private String title, description;

    public Places(int id, int image, String title, String description) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.id = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String description) {
        this.description = description;
    }
}

==========================================================================================================
##########################################################################################################
##########################################################################################################
==========================================================================================================

package com.example.tourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PrivateKey;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesAdapterViewHolder> {

    private clickListener listener;
    private List<Places> placesList;
    private Context context;

    public PlacesAdapter(List<Places> placesList, Context context, clickListener listener) {
        this.placesList = placesList;
        this.context = context;
        this.listener = listener;
    }

    public interface clickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public PlacesAdapter.PlacesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.places, parent, false );
        return new PlacesAdapterViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.PlacesAdapterViewHolder holder, int position) {
        holder.imgPlace.setImageResource(placesList.get(position).getImage());
        holder.txtPlaceTitle.setText(placesList.get(position).getTitle());
        holder.txtPlaceDescription.setText(placesList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public class PlacesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private clickListener clickListener;
        public ImageView imgPlace;
        public TextView txtPlaceTitle, txtPlaceDescription;
        public PlacesAdapterViewHolder(@NonNull View itemView, clickListener listener) {
            super(itemView);
            imgPlace = itemView.findViewById(R.id.imgPlace);
            txtPlaceTitle = itemView.findViewById(R.id.txtPlaceTitle);
            txtPlaceDescription = itemView.findViewById(R.id.txtPlaceDescription);
            this.clickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getAdapterPosition());
        }
    }
}

==========================================================================================================
##########################################################################################################
##########################################################################################################
==========================================================================================================

package com.example.tourism;

public class Destination {

    private int image;
    private int id;

    public Destination(int id, int image){
        this.id = id;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


==========================================================================================================
##########################################################################################################
##########################################################################################################
==========================================================================================================

package com.example.tourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationAdapterViewHolder> {

    private List<Destination> destinationList;
    private Context context;

    public DestinationAdapter(List<Destination> destinationList, Context context){
        this.destinationList = destinationList;
        this.context = context;
    }

    @NonNull
    @Override
    public DestinationAdapter.DestinationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.destinations, parent, false);
        return new DestinationAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationAdapter.DestinationAdapterViewHolder holder, int position) {
        holder.imgDestination.setImageResource(destinationList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public class DestinationAdapterViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgDestination;
        public DestinationAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDestination = itemView.findViewById(R.id.imgDestination);
        }
    }

}

==========================================================================================================
##########################################################################################################
##########################################################################################################
==========================================================================================================

package com.example.tourism;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DefaultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultFragment newInstance(String param1, String param2) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_default, container, false);
    }
}
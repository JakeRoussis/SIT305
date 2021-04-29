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
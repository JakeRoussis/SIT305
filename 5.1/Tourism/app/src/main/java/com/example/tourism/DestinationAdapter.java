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

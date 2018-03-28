package com.example.valentino.traveloptimizer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.City;
import com.example.valentino.traveloptimizer.models.Trip;

import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class TripsAdapter extends RecyclerView.Adapter {
    private List<Trip> tripsDataSet;
    private Context mContext;

    public TripsAdapter(List<Trip> trips, Context context) {
        tripsDataSet = trips;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Trip data = tripsDataSet.get(position);
        TripViewHolder tripHolder = ((TripViewHolder) holder);
        tripHolder.tripsCityTextView.setText(data.city);
        int imageId = mContext.getResources().getIdentifier(data.city.toLowerCase(), "drawable", mContext.getPackageName());
        tripHolder.tripsCityImageView.setImageResource(imageId);
        tripHolder.tripsDateTextView.setText("Jan 1 - Jan 5, 2018");
        tripHolder.tripsCountdownTextView.setText("3 Months Ago");
        tripHolder.tripsPlacesCountTextView.setText(data.places.size() + " Places Visited");
    }

    @Override
    public int getItemCount() {
        return tripsDataSet.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView tripsCityTextView;
        public TextView tripsDateTextView;
        public TextView tripsCountdownTextView;
        public TextView tripsPlacesCountTextView;
        public ImageView tripsCityImageView;

        public TripViewHolder(View itemView) {
            super(itemView);
            tripsCityTextView = itemView.findViewById(R.id.tripsCityTextView);
            tripsCityImageView = itemView.findViewById(R.id.tripsCityImageView);
            tripsDateTextView = itemView.findViewById(R.id.tripsDateTextView);
            tripsCountdownTextView = itemView.findViewById(R.id.tripsCountdownTextView);
            tripsPlacesCountTextView = itemView.findViewById(R.id.tripsPlacesCountTextView);
        }
    }

}

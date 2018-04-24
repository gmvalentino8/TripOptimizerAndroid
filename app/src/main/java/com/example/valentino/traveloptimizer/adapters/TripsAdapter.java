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

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class TripsAdapter extends RecyclerView.Adapter {
    private List<Trip> tripsDataSet;
    private Context mContext;

    private Comparator<Trip> comparator = new Comparator<Trip>() {
        @Override
        public int compare(Trip a, Trip b) {
            return (int) (b.getStartDate() - a.getStartDate());
        }
    };

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
        tripHolder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return tripsDataSet.size();
    }

    public void addItems(List<Trip> data) {
        tripsDataSet.clear();
        tripsDataSet.addAll(data);
        Collections.sort(tripsDataSet, comparator);
        notifyDataSetChanged();
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

        private void bindData(Trip trip) {
            tripsCityTextView.setText(trip.getCity());
            int imageId = itemView.getContext().getResources()
                    .getIdentifier(trip.getCity().toLowerCase(),
                            "drawable", itemView.getContext().getPackageName());
            tripsCityImageView.setImageResource(imageId);
            Calendar startDate = Calendar.getInstance();
            startDate.setTimeInMillis(trip.getStartDate());
            Calendar endDate = Calendar.getInstance();
            endDate.setTimeInMillis(trip.getEndDate());
            tripsDateTextView.setText(trip.getStartDateString() + " - "+ trip.getEndDateString());
            tripsCountdownTextView.setText("3 Months Ago");
            //        tripHolder.tripsPlacesCountTextView.setText(data.getItinerary().split(" ").length + " Places Visited");
        }
    }

}

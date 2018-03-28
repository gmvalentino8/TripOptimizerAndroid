package com.example.valentino.traveloptimizer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.Place;

import java.util.List;

/**
 * Created by Valentino on 3/28/18.
 */

public class PlacesAdapter extends RecyclerView.Adapter {

    private List<Place> placesDataSet;
    private Context mContext;

    public PlacesAdapter(List<Place> places, Context context) {
        placesDataSet = places;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Place data = placesDataSet.get(position);
        PlacesAdapter.PlaceViewHolder placeHolder = ((PlacesAdapter.PlaceViewHolder) holder);
        placeHolder.placeNameTextView.setText(data.name);
        placeHolder.placeCategoryTextView.setText(data.category);
        placeHolder.placeTimeTextView.setText(data.openTime + " - " + data.closeTime);
        placeHolder.placeAddressTextView.setText(data.address);
    }

    @Override
    public int getItemCount() {
        return placesDataSet.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        public TextView placeNameTextView;
        public TextView placeCategoryTextView;
        public TextView placeTimeTextView;
        public TextView placeAddressTextView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            placeNameTextView = itemView.findViewById(R.id.placeNameTextView);
            placeCategoryTextView = itemView.findViewById(R.id.placeCategoryTextView);
            placeTimeTextView = itemView.findViewById(R.id.placeTimesTextView);
            placeAddressTextView = itemView.findViewById(R.id.placeAddressTextView);
        }
    }
}

package com.example.valentino.traveloptimizer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.Place;

import java.util.List;
import java.util.Set;

/**
 * Created by Valentino on 3/28/18.
 */

public class PlacesAdapter extends RecyclerView.Adapter {

    private List<Place> placesDataSet;
    private Set<String> selectedIdSet;
    private Context mContext;

    public PlacesAdapter(List<Place> places, Set<String> selectedIdSet, Context context) {
        this.placesDataSet = places;
        this.selectedIdSet = selectedIdSet;
        this.mContext = context;
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

        if (selectedIdSet.contains(data.getPlaceId())) {
            ((PlaceViewHolder) holder).placeCardView.setCardBackgroundColor(Color.GRAY);
        }

        placeHolder.placeNameTextView.setText(data.getName());
        placeHolder.placeCategoryTextView.setText(data.getCategory());
        placeHolder.placeTimeTextView.setText(data.getOpenTime() + " - " + data.getCloseTime());
        placeHolder.placeAddressTextView.setText(data.getAddress());
    }

    @Override
    public int getItemCount() {
        return placesDataSet.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        public CardView placeCardView;
        public TextView placeNameTextView;
        public TextView placeCategoryTextView;
        public TextView placeTimeTextView;
        public TextView placeAddressTextView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            placeCardView = itemView.findViewById(R.id.placeCardView);
            placeNameTextView = itemView.findViewById(R.id.placeNameTextView);
            placeCategoryTextView = itemView.findViewById(R.id.placeCategoryTextView);
            placeTimeTextView = itemView.findViewById(R.id.placeTimesTextView);
            placeAddressTextView = itemView.findViewById(R.id.placeAddressTextView);
        }
    }
}

package com.example.valentino.traveloptimizer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Valentino on 3/28/18.
 */

public class PlacesAdapter extends RecyclerView.Adapter {

    private List<Place> placesDataSet;

    public PlacesAdapter(List<Place> places) {
        this.placesDataSet = places;
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
        placeHolder.BindData(data);
    }

    public void addItems(List<Place> data) {
        placesDataSet.clear();
        if (data != null) {
            placesDataSet.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addItem(Place data) {
        placesDataSet.add(data);
        notifyDataSetChanged();
    }

    public void removeItem(Place data) {
        placesDataSet.remove(data);
        notifyDataSetChanged();
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
        public LinearLayout tripAdviserRatingLayout;
        public ImageView taRating1;
        public ImageView taRating2;
        public ImageView taRating3;
        public ImageView taRating4;
        public ImageView taRating5;
        public ArrayList<ImageView> tripAdviserRatings = new ArrayList<>();
        public LinearLayout yelpRatingLayout;
        public ImageView yelpRating1;
        public ImageView yelpRating2;
        public ImageView yelpRating3;
        public ImageView yelpRating4;
        public ImageView yelpRating5;
        public ArrayList<ImageView> yelpRatings = new ArrayList<>();

        public PlaceViewHolder(View itemView) {
            super(itemView);
            placeCardView = itemView.findViewById(R.id.placeCardView);
            placeNameTextView = itemView.findViewById(R.id.placeNameTextView);
            placeCategoryTextView = itemView.findViewById(R.id.placeCategoryTextView);
            placeTimeTextView = itemView.findViewById(R.id.placeTimesTextView);
            placeAddressTextView = itemView.findViewById(R.id.placeAddressTextView);
            tripAdviserRatingLayout = itemView.findViewById(R.id.tripAdviserRatings);
            taRating1 = itemView.findViewById(R.id.taStar1);
            taRating2 = itemView.findViewById(R.id.taStar2);
            taRating3 = itemView.findViewById(R.id.taStar3);
            taRating4 = itemView.findViewById(R.id.taStar4);
            taRating5 = itemView.findViewById(R.id.taStar5);
            tripAdviserRatings.add(taRating1);
            tripAdviserRatings.add(taRating2);
            tripAdviserRatings.add(taRating3);
            tripAdviserRatings.add(taRating4);
            tripAdviserRatings.add(taRating5);
            yelpRatingLayout = itemView.findViewById(R.id.yelpRatings);
            yelpRating1 = itemView.findViewById(R.id.yelpStar1);
            yelpRating2 = itemView.findViewById(R.id.yelpStar2);
            yelpRating3 = itemView.findViewById(R.id.yelpStar3);
            yelpRating4 = itemView.findViewById(R.id.yelpStar4);
            yelpRating5 = itemView.findViewById(R.id.yelpStar5);
            yelpRatings.add(yelpRating1);
            yelpRatings.add(yelpRating2);
            yelpRatings.add(yelpRating3);
            yelpRatings.add(yelpRating4);
            yelpRatings.add(yelpRating5);
        }

        public void BindData(Place data) {
            placeNameTextView.setText(data.getName());
            placeCategoryTextView.setText(data.getCategory());
            placeTimeTextView.setText(data.getOpenTime() + " - " + data.getCloseTime());
            String address = data.getAddress().replaceAll(", ", "");
            placeAddressTextView.setText(address);
            if (data.getTripAdvisorRating() == null) {
                tripAdviserRatingLayout.setVisibility(View.INVISIBLE);
            } else {
                setTripAdviserRating(data.getTripAdvisorRating());
            }
            if (data.getYelpRating() == null) {
                yelpRatingLayout.setVisibility(View.INVISIBLE);
            } else {
                setYelpRating(data.getYelpRating());
            }
        }

        private void setTripAdviserRating(double rating) {
            double i = 0.;
            for (; i < rating; i++) {
                tripAdviserRatings.get((int) i).setBackgroundResource(R.drawable.ic_star);
            }
            if (i - 0.5 == rating) {
                tripAdviserRatings.get((int) rating).setBackgroundResource(R.drawable.ic_star_half);
            }
        }

        private void setYelpRating(double rating) {
            double i = 0.;
            for (; i < rating; i++) {
                yelpRatings.get((int) i).setBackgroundResource(R.drawable.ic_star);
            }
            if (i - 0.5 == rating) {
                yelpRatings.get((int) rating).setBackgroundResource(R.drawable.ic_star_half);
            }
        }
    }

}

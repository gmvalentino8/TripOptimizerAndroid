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
import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class CitiesAdapter extends RecyclerView.Adapter {
    private List<City> citiesDataSet;
    private Context mContext;

    public CitiesAdapter(List<City> cities, Context context) {
        citiesDataSet = cities;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        City data = citiesDataSet.get(position);
        ((CityViewHolder) holder).cityTextView.setText(data.name);
        int imageId = mContext.getResources().getIdentifier(data.image, "drawable", mContext.getPackageName());
        ((CityViewHolder) holder).cityImageView.setImageResource(imageId);
    }

    @Override
    public int getItemCount() {
        return citiesDataSet.size();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        public TextView cityTextView;
        public ImageView cityImageView;

        public CityViewHolder(View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            cityImageView = itemView.findViewById(R.id.cityImageView);
        }
    }

}

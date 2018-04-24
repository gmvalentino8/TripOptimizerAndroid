package com.example.valentino.traveloptimizer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.activities.SelectPlacesActivity;
import com.example.valentino.traveloptimizer.adapters.PlacesAdapter;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.models.Place;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripPlacesFragment extends Fragment {

    List<Place> tripPlaces;
    Trip currTrip;
    private RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public TripPlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripPlaces = new ArrayList<>();
        currTrip = (Trip) getArguments().getSerializable("Trip");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPlaces();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trip_places, container, false);
        recyclerView = root.findViewById(R.id.placesRecyclerView);
        adapter = new PlacesAdapter(tripPlaces);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) { }

            @Override
            public void onLongClick(View view, final int position) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_remove, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Place selectedPlace = tripPlaces.get(position);
                        deletePlace(selectedPlace);
                        return true;
                    }
                });
                popup.show();
            }
        }));
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SelectPlacesActivity.class);
                i.putExtra("Trip", currTrip);
                startActivity(i);
            }
        });
        return root;
    }

    public void getPlaces() {
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<List<Place>> call = apiInterface.getTripPlaces(currTrip.getTripId());
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                adapter.addItems(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.d("Get Trip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    public void deletePlace(final Place place) {
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<Void> call = apiInterface.deleteTripPlace(currTrip.getTripId(), place.getPlaceId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                adapter.removeItem(place);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Get Trip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}

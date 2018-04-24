package com.example.valentino.traveloptimizer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.adapters.PlacesAdapter;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.models.Place;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectRecommendedFragment extends Fragment {
    private Trip currTrip;
    private List<String> selectedPlaces;
    private List<Place> places;
    private Place place;

    private RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public SelectRecommendedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        places = new ArrayList<>();

        if (getArguments() != null) {
            currTrip = (Trip) getArguments().getSerializable("Trip");
            if (currTrip != null && currTrip.getPlaces() != null) {
                selectedPlaces = currTrip.getPlaces();
            }
        }
        getPlaces();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_select_recommended, container, false);
        recyclerView = root.findViewById(R.id.placesRecyclerView);
        adapter = new PlacesAdapter(places);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Place item = places.get(position);
                putPlace(item.getPlaceId());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        return root;
    }

    private void getPlaces() {
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<List<Place>> call = apiInterface.getAllPlaces(currTrip.getCity(), "Things to do");
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                places = response.body();
                Log.d("Places", "" + response.body());
                adapter.addItems(places);
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.d("ViewPlacesFragment", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void putPlace(String pid) {
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<Void> call = apiInterface.postTripPlace(currTrip.getTripId(), pid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Post Trip", "Successful");
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("PutTrip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

}

package com.example.valentino.traveloptimizer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.adapters.PlacesAdapter;
import com.example.valentino.traveloptimizer.adapters.TripsAdapter;
import com.example.valentino.traveloptimizer.models.Place;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SelectPlacesFragment extends Fragment {
    private Trip currTrip;
    private List<Place> places;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public SelectPlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        places = new ArrayList<>();
        places.add(new Place("Test 1", "Test Address 1, Chicago, IL, USA",
                "Restaurant", "10:00AM", "11:00pm"));
        places.add(new Place("Test 2", "Test Address 2, Chicago, IL, USA",
                "Sightseeing", "8:00AM", "5:00pm"));
        places.add(new Place("Test 3", "Test Address 3, Chicago, IL, USA",
                "Shopping", "9:00AM", "9:00pm"));
        places.add(new Place("Test 4", "Test Address 4, Chicago, IL, USA",
                "Entertainment", "11:00AM", "11:30pm"));
        places.add(new Place("Test 5", "Test Address 5, Chicago, IL, USA",
                "Park", "7:00AM", "4:30pm"));
        places.add(new Place("Test 6", "Test Address 6, Chicago, IL, USA",
                "Museum", "8:00AM", "8:00pm"));
        if (getArguments() != null) {
            currTrip = (Trip) getArguments().getSerializable("Trip");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_select_places, container, false);

        recyclerView = root.findViewById(R.id.placesRecyclerView);
        adapter = new PlacesAdapter(places, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), places.get(position).name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        return root;
    }

}

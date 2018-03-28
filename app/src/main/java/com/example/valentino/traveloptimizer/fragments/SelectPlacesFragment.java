package com.example.valentino.traveloptimizer.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectPlacesFragment extends Fragment {
    private boolean editing;
    private Trip currTrip;
    private List<Place> places;
    private Set<String> selectedIdSet;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public SelectPlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedIdSet = new HashSet<>();
        selectedIdSet.add("2");
        places = new ArrayList<>();
        places.add(new Place("1","Test 1", "Test Address 1, Chicago, IL, USA",
                "Restaurant", "10:00AM", "11:00pm"));
        places.add(new Place("2","Test 2", "Test Address 2, Chicago, IL, USA",
                "Sightseeing", "8:00AM", "5:00pm"));
        places.add(new Place("3","Test 3", "Test Address 3, Chicago, IL, USA",
                "Shopping", "9:00AM", "9:00pm"));
        places.add(new Place("4","Test 4", "Test Address 4, Chicago, IL, USA",
                "Entertainment", "11:00AM", "11:30pm"));
        places.add(new Place("5","Test 5", "Test Address 5, Chicago, IL, USA",
                "Park", "7:00AM", "4:30pm"));
        places.add(new Place("6","Test 6", "Test Address 6, Chicago, IL, USA",
                "Museum", "8:00AM", "8:00pm"));

        if (getArguments() != null) {
            editing = getArguments().getBoolean("Editing", false);
            currTrip = (Trip) getArguments().getSerializable("Trip");
            if (editing && currTrip != null) {
                selectedIdSet = new HashSet<>(currTrip.places);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_select_places, container, false);

        recyclerView = root.findViewById(R.id.placesRecyclerView);
        adapter = new PlacesAdapter(places, selectedIdSet, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Place item = places.get(position);
                if (selectedIdSet.contains(item.placeId)) {
                    // Remove item
                    selectedIdSet.remove(item.placeId);
                    ((CardView) view).setCardBackgroundColor(Color.WHITE);

                }
                else {
                    // Add item
                    selectedIdSet.add(item.placeId);
                    ((CardView) view).setCardBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewTripsFragment viewTripsFragment = new ViewTripsFragment();
                currTrip.places = new ArrayList<>(selectedIdSet);
                // Update Database Here

                Bundle args = new Bundle();
                args.putSerializable("Trip", currTrip);
                viewTripsFragment.setArguments(args);
                FragmentManager manager = getActivity().getFragmentManager();
                if (manager.getBackStackEntryCount() > 0) {
                    FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
                    manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                manager.beginTransaction()
                        .replace(R.id.content, viewTripsFragment)
                        .commit();
            }
        });

        return root;
    }

}

package com.example.valentino.traveloptimizer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.activities.SelectCityActivity;
import com.example.valentino.traveloptimizer.adapters.CitiesAdapter;
import com.example.valentino.traveloptimizer.adapters.TripsAdapter;
import com.example.valentino.traveloptimizer.models.City;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ViewTripsFragment extends Fragment {
    private List<Trip> trips;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ViewTripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trips = new ArrayList<>();
        trips.add(new Trip("1", "Chicago"));
        trips.add(new Trip("2", "Tokyo"));
        trips.add(new Trip("3", "Seoul"));
        trips.add(new Trip("asdf", "London"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_trips, container, false);

        recyclerView = root.findViewById(R.id.tripsRecyclerView);
        System.out.println("" + trips.size());
        adapter = new TripsAdapter(trips, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TripDetailsFragment tripDetailsFragment = new TripDetailsFragment();
                Bundle args = new Bundle();
                args.putSerializable("Trip", trips.get(position));
                tripDetailsFragment.setArguments(args);
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, tripDetailsFragment)
                        .addToBackStack("tripDetails")
                        .commit();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCityFragment selectCityFragment = new SelectCityFragment();
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, selectCityFragment)
                        .addToBackStack("selectCity")
                        .commit();
            }
        });

        return root;

    }
}

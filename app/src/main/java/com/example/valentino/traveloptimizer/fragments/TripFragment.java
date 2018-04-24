package com.example.valentino.traveloptimizer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.Trip;

public class TripFragment extends Fragment {

    Trip currTrip;

    public TripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currTrip = (Trip) getArguments().getSerializable("Trip");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Trip to " + currTrip.getCity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trip, container, false);

        FragmentTabHost tabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
        /** Important: Must use child FragmentManager. */
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        Bundle args = new Bundle();
        args.putSerializable("Trip", currTrip);
        tabHost.addTab(tabHost.newTabSpec("Details").setIndicator("Details"),
                TripDetailsFragment.class, args);

        tabHost.addTab(tabHost.newTabSpec("Places").setIndicator("Places"),
                TripPlacesFragment.class, args);

        tabHost.addTab(tabHost.newTabSpec("Itinerary").setIndicator("Itinerary"),
                ItineraryFragment.class, args);

        return root;
    }

}

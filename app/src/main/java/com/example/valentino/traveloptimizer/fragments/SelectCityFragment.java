package com.example.valentino.traveloptimizer.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;
import com.example.valentino.traveloptimizer.adapters.CitiesAdapter;
import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.City;

import java.util.ArrayList;
import java.util.List;

public class SelectCityFragment extends Fragment {
    private List<City> supportedCities;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Trip currTrip;


    public SelectCityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        supportedCities = new ArrayList<City>();
        supportedCities.add(new City("Chicago", "chicago"));
        supportedCities.add(new City("Tokyo", "tokyo"));
        supportedCities.add(new City("Seoul", "seoul"));
        supportedCities.add(new City("Hong Kong", "hongkong"));
        supportedCities.add(new City("Paris", "paris"));
        supportedCities.add(new City("London", "london"));
        supportedCities.add(new City("Bangkok", "bangkok"));
        supportedCities.add(new City("Dubai", "dubai"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_city, container, false);

        recyclerView = root.findViewById(R.id.citiesRecyclerView);
        adapter = new CitiesAdapter(supportedCities, getContext());
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (currTrip == null) {
                    currTrip = new Trip();
                    currTrip.setCity(supportedCities.get(position).name);
                }

                CreateTripFragment createTripFragment = new CreateTripFragment();
                Bundle args = new Bundle();
                //args.putString("City", supportedCities.get(position).name);
                args.putSerializable("Trip", currTrip);
                createTripFragment.setArguments(args);
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, createTripFragment)
                        .addToBackStack("selectCity")
                        .commit();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return root;
    }

}

package com.example.valentino.traveloptimizer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.adapters.TripsAdapter;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTripsFragment extends Fragment {
    private List<Trip> trips;
    private RecyclerView recyclerView;
    private TripsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ViewTripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trips = new ArrayList<>();
        getActivity().setTitle("Trips");
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
                TripFragment tripFragment = new TripFragment();
                Bundle args = new Bundle();
                args.putSerializable("Trip", trips.get(position));
                tripFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, tripFragment)
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
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, selectCityFragment)
                        .addToBackStack("selectCity")
                        .commit();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserTrips();
        getActivity().setTitle("Your Trips");
    }

    private void getUserTrips() {
        CommonDependencyProvider commonDependencyProvider = new CommonDependencyProvider();
        User user = commonDependencyProvider.getAppHelper().getLoggedInUser();
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<List<Trip>> call = apiInterface.getUserTrips(user.getEmail());
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                System.out.println(response.body());
                List<Trip> resource = response.body();
                trips = resource;
                adapter.addItems(trips);
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Log.d("ViewTripsFragment", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}

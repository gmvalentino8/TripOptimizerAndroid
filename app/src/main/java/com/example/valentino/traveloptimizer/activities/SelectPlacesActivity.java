package com.example.valentino.traveloptimizer.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.util.StringUtils;
import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.adapters.PlacesAdapter;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.fragments.ItineraryFragment;
import com.example.valentino.traveloptimizer.fragments.SelectRecommendedFragment;
import com.example.valentino.traveloptimizer.fragments.SelectRestaurantsFragment;
import com.example.valentino.traveloptimizer.fragments.SelectThingsToDoFragment;
import com.example.valentino.traveloptimizer.fragments.TripDetailsFragment;
import com.example.valentino.traveloptimizer.fragments.TripPlacesFragment;
import com.example.valentino.traveloptimizer.models.Place;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPlacesActivity extends AppCompatActivity {

    private Trip currTrip;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_places);

        currTrip = (Trip) getIntent().getSerializableExtra("Trip");
        toolbar = findViewById(R.id.topToolBar);
        setSupportActionBar(toolbar);
        setTitle("Add a Place");


        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        Bundle args = new Bundle();
        args.putSerializable("Trip", currTrip);
        tabHost.addTab(tabHost.newTabSpec("Suggested").setIndicator("Suggested"),
                SelectRecommendedFragment.class, args);

        tabHost.addTab(tabHost.newTabSpec("Things to do").setIndicator("Things to do"),
                SelectThingsToDoFragment.class, args);

        tabHost.addTab(tabHost.newTabSpec("Restaurants").setIndicator("Restaurants"),
                SelectRestaurantsFragment.class, args);

    }
}

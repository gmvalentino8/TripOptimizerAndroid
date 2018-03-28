package com.example.valentino.traveloptimizer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.adapters.CitiesAdapter;
import com.example.valentino.traveloptimizer.fragments.CreateTripFragment;
import com.example.valentino.traveloptimizer.models.City;
import com.example.valentino.traveloptimizer.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SelectCityActivity extends AppCompatActivity {

    private List<City> supportedCities;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        supportedCities = new ArrayList<City>();
        supportedCities.add(new City("Chicago", "chicago"));
        supportedCities.add(new City("Tokyo", "tokyo"));
        supportedCities.add(new City("Seoul", "seoul"));
        supportedCities.add(new City("Hong Kong", "hongkong"));
        supportedCities.add(new City("Paris", "paris"));
        supportedCities.add(new City("London", "london"));
        supportedCities.add(new City("Bangkok", "bangkok"));
        supportedCities.add(new City("Dubai", "dubai"));


        recyclerView = findViewById(R.id.citiesRecyclerView);
        adapter = new CitiesAdapter(supportedCities, getApplicationContext());
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(getApplicationContext(), supportedCities.get(position).name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

}

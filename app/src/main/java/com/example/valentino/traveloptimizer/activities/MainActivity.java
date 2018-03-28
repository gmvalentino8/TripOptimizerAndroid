package com.example.valentino.traveloptimizer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.fragments.SelectCityFragment;
import com.example.valentino.traveloptimizer.fragments.ViewTripsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewTripsFragment testFragment = new ViewTripsFragment();
        getFragmentManager().beginTransaction().add(R.id.content, testFragment).commit();
    }
}

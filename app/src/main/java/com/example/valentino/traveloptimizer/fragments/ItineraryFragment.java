package com.example.valentino.traveloptimizer.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.models.Place;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItineraryFragment extends Fragment implements OnMapReadyCallback {

    List<Place> tripPlaces = new ArrayList<>();
    Trip currTrip;
    GoogleMap map;
    MapView mapView;

    public ItineraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currTrip = (Trip) getArguments().getSerializable("Trip");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_itinerary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(currTrip.getStartLat(), currTrip.getStartLng())));
        getTrip();
    }

    public void getTrip() {
        CommonDependencyProvider commonDependencyProvider = new CommonDependencyProvider();
        User user = commonDependencyProvider.getAppHelper().getLoggedInUser();
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<List<Trip>> call = apiInterface.getTripData(user.getEmail(), currTrip.getTripId());
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                currTrip = response.body().get(0);
                getItinerary();
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Log.d("Get Trip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    public void getPlaces() {
        tripPlaces.clear();
        tripPlaces.add(new Place("0", "Black Dog", "Test", 40.113590, -88.207565, "Restaurant", "", ""));
        tripPlaces.add(new Place("1", "Seven Saints", "Test", 40.116413, -88.241989, "Restaurant", "", ""));
        tripPlaces.add(new Place("2", "Maize", "Test", 40.110371, -88.238907, "Restaurant", "", ""));
        tripPlaces.add(new Place("3", "Ko Fusion", "Test", 40.118542, -88.2243523, "Restaurant", "", ""));
        ArrayList<Marker> markers = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Place place : tripPlaces) {
            LatLng coordinates = new LatLng(place.getLat(), place.getLng());
            Marker marker = map.addMarker(new MarkerOptions().position(coordinates)
                            .title(place.getName()));
            marker.showInfoWindow();
            markers.add(marker);
            builder.include(coordinates);
        }
        if (markers.size() > 1) {
            for (int i = 1; i < markers.size(); i++) {
                map.addPolyline(new PolylineOptions()
                        .add(markers.get(i-1).getPosition(), markers.get(i).getPosition())
                        .width(5)
                        .color(Color.BLACK));
            }
        }
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 40);
        map.moveCamera(cu);
    }

    public void getItinerary() {
        ApiInterface apiInterface = ApiClient.getApiInstance();
        // TODO: Change to get trip itinerary
        Call<List<Place>> call = apiInterface.getTripPlaces(currTrip.getTripId());
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                tripPlaces = response.body();

                ArrayList<Marker> markers = new ArrayList<>();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                Marker startMarker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(currTrip.getStartLat(), currTrip.getStartLng()))
                        .title(currTrip.getStartName()));
                startMarker.showInfoWindow();
                markers.add(startMarker);

                for (Place place : tripPlaces) {
                    LatLng coordinates = new LatLng(place.getLat(), place.getLng());
                    Marker marker = map.addMarker(new MarkerOptions().position(coordinates)
                            .title(place.getName()));
                    marker.showInfoWindow();
                    markers.add(marker);
                    builder.include(coordinates);
                }
                if (markers.size() > 1) {
                    for (int i = 1; i < markers.size(); i++) {
                        map.addPolyline(new PolylineOptions()
                                .add(markers.get(i-1).getPosition(), markers.get(i).getPosition())
                                .width(5)
                                .color(Color.BLACK));
                    }
                }
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 200);
                map.moveCamera(cu);
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.d("Get Trip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

}

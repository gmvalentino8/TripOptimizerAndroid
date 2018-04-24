package com.example.valentino.traveloptimizer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.activities.EditTripActivity;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailsFragment extends Fragment {
    private String city;
    private Trip currTrip;
    private Calendar arrivalDate = Calendar.getInstance();
    private Calendar departureDate = Calendar.getInstance();
    private LatLng accommodationLatLng;
    private EditText arrivalDateEditText;
    private EditText arrivalTimeEditText;
    private EditText departureDateEditText;
    private EditText departureTimeEditText;
    private EditText accommodationEditText;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currTrip = (Trip) getArguments().getSerializable("Trip");
            city = currTrip.getCity();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getTrip();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trip_details, container, false);
        arrivalDateEditText = root.findViewById(R.id.arrivalDateEditText);
        arrivalDateEditText.setFocusable(false);
        arrivalDateEditText.setClickable(false);
        arrivalTimeEditText = root.findViewById(R.id.arrivalTimeEditText);
        arrivalTimeEditText.setFocusable(false);
        arrivalTimeEditText.setClickable(false);
        departureDateEditText = root.findViewById(R.id.departureDateEditText);
        departureDateEditText.setFocusable(false);
        departureDateEditText.setClickable(false);
        departureTimeEditText = root.findViewById(R.id.departureTimeEditText);
        departureTimeEditText.setFocusable(false);
        departureTimeEditText.setClickable(false);
        accommodationEditText = root.findViewById(R.id.accommodationEditText);
        accommodationEditText.setFocusable(false);
        accommodationEditText.setClickable(false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditTripActivity.class);
                i.putExtra("Trip", currTrip);
                startActivity(i);
            }
        });

        FloatingActionButton fab2 = root.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrip();
            }
        });

        return root;
    }

    public void getTrip() {
        CommonDependencyProvider commonDependencyProvider = new CommonDependencyProvider();
        User user = commonDependencyProvider.getAppHelper().getLoggedInUser();
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<List<Trip>> call = apiInterface.getTripData(user.getEmail(), currTrip.getTripId());
        Log.d("API CALL", "Trip Id : " + currTrip.getTripId());
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                Log.d("API CALL", "Response : "+response.body());
                currTrip = response.body().get(0);
                arrivalDate.setTimeInMillis(currTrip.getStartDate());
                setDateLabel(arrivalDate, arrivalDateEditText);
                setTimeLabel(arrivalDate, arrivalTimeEditText);
                departureDate.setTimeInMillis(currTrip.getEndDate());
                setDateLabel(departureDate, departureDateEditText);
                setTimeLabel(departureDate, departureTimeEditText);
                accommodationEditText.setText(currTrip.getStartName());
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Log.d("Get Trip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    public void setDateLabel(final Calendar date, EditText label) {
        String myFormat = "MMMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(" " + sdf.format(date.getTime()));
    }

    public void setTimeLabel(final Calendar date, EditText label) {
        String myFormat = "hh:mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(" " + sdf.format(date.getTime()));
    }

    private void deleteTrip() {
        CommonDependencyProvider commonDependencyProvider = new CommonDependencyProvider();
        User user = commonDependencyProvider.getAppHelper().getLoggedInUser();
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<Void> call = apiInterface.deleteTrip(user.getEmail(), currTrip.getTripId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DeleteTrip", "Successful");
                getActivity().getFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("DeleteTrip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

}

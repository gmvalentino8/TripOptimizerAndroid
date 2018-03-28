package com.example.valentino.traveloptimizer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.models.Trip;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
            city = currTrip.city;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trip_details, container, false);
        TextView cityName = root.findViewById(R.id.tripCityLabel);
        cityName.setText("Trip to " + city);
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
        arrivalDate.setTimeInMillis(currTrip.startDate);
        setDateLabel(arrivalDate, arrivalDateEditText);
        setTimeLabel(arrivalDate, arrivalTimeEditText);
        departureDate.setTimeInMillis(currTrip.endDate);
        setDateLabel(departureDate, departureDateEditText);
        setTimeLabel(departureDate, departureTimeEditText);
        accommodationEditText.setText(currTrip.startName);
        return root;
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

}

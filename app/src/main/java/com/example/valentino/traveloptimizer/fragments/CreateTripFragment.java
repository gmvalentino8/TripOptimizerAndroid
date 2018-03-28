package com.example.valentino.traveloptimizer.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.valentino.traveloptimizer.R;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTripFragment extends Fragment implements View.OnClickListener {
    private static final String CITY_PARAM = "City";

    private String city;
    private Calendar arrivalDate = Calendar.getInstance();
    private Calendar departureDate = Calendar.getInstance();
    private EditText arrivalDateEditText;
    private EditText arrivalTimeEditText;
    private EditText departureDateEditText;
    private EditText departureTimeEditText;
    private EditText accommodationEditText;
    private Button nextButton;

    public CreateTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getString(CITY_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_trip, container, false);
        TextView cityName = root.findViewById(R.id.tripCityLabel);
        cityName.setText("Trip to " + city);
        arrivalDateEditText = root.findViewById(R.id.arrivalDateEditText);
        arrivalDateEditText.setFocusable(false);
        arrivalDateEditText.setClickable(true);
        arrivalDateEditText.setOnClickListener(this);
        arrivalTimeEditText = root.findViewById(R.id.arrivalTimeEditText);
        arrivalTimeEditText.setFocusable(false);
        arrivalTimeEditText.setClickable(true);
        arrivalTimeEditText.setOnClickListener(this);
        departureDateEditText = root.findViewById(R.id.departureDateEditText);
        departureDateEditText.setFocusable(false);
        departureDateEditText.setClickable(true);
        departureDateEditText.setOnClickListener(this);
        departureTimeEditText = root.findViewById(R.id.departureTimeEditText);
        departureTimeEditText.setFocusable(false);
        departureTimeEditText.setClickable(true);
        departureTimeEditText.setOnClickListener(this);
        nextButton = root.findViewById(R.id.tripsNextButton);
        nextButton.setOnClickListener(this);

        return root;
    }


    @Exclude
    public void getDate(final Calendar dateCalendar, final EditText label) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateCalendar.set(Calendar.YEAR, year);
                dateCalendar.set(Calendar.MONTH, month);
                dateCalendar.set(Calendar.DAY_OF_MONTH, day);
                setDateLabel(dateCalendar, label);
            }
        };

        new DatePickerDialog(getContext(), date, arrivalDate.get(Calendar.YEAR), arrivalDate.get(Calendar.MONTH),
                arrivalDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setDateLabel(final Calendar date, EditText label) {
        String myFormat = "MMMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(" " + sdf.format(date.getTime()));
    }

    @Exclude
    public void getTime(final Calendar dateCalendar, final EditText label) {
        final TimePickerDialog.OnTimeSetListener date = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                dateCalendar.set(Calendar.HOUR_OF_DAY, hour);
                dateCalendar.set(Calendar.MINUTE, minute);
                setTimeLabel(dateCalendar, label);
            }
        };

        new TimePickerDialog(getContext(), date, arrivalDate
                .get(Calendar.HOUR_OF_DAY), arrivalDate.get(Calendar.MINUTE),
                false).show();
    }

    public void setTimeLabel(final Calendar date, EditText label) {
        String myFormat = "hh:mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(" " + sdf.format(date.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrivalDateEditText:
                getDate(arrivalDate, arrivalDateEditText);
                break;
            case R.id.arrivalTimeEditText:
                getTime(arrivalDate, arrivalTimeEditText);
                break;
            case R.id.departureDateEditText:
                getDate(departureDate, departureDateEditText);
                break;
            case R.id.departureTimeEditText:
                getTime(departureDate, departureTimeEditText);
                break;
            case R.id.tripsNextButton:
                break;
        }
    }
}

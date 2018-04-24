package com.example.valentino.traveloptimizer.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.api.ApiClient;
import com.example.valentino.traveloptimizer.api.ApiInterface;
import com.example.valentino.traveloptimizer.fragments.ViewTripsFragment;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTripActivity extends AppCompatActivity implements View.OnClickListener {
    final int REQUEST_PLACE_PICKER = 1;

    private Trip currTrip;
    private Calendar arrivalDate = Calendar.getInstance();
    private Calendar departureDate = Calendar.getInstance();
    private Double accommodationLat;
    private Double accommodationLng;
    private EditText arrivalDateEditText;
    private EditText arrivalTimeEditText;
    private EditText departureDateEditText;
    private EditText departureTimeEditText;
    private EditText accommodationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        currTrip = (Trip) getIntent().getSerializableExtra("Trip");

        TextView cityName = findViewById(R.id.tripCityLabel);
        cityName.setText("Trip to " + currTrip.getCity());
        arrivalDateEditText = findViewById(R.id.arrivalDateEditText);
        arrivalDateEditText.setFocusable(false);
        arrivalDateEditText.setClickable(true);
        arrivalDateEditText.setOnClickListener(this);
        arrivalTimeEditText = findViewById(R.id.arrivalTimeEditText);
        arrivalTimeEditText.setFocusable(false);
        arrivalTimeEditText.setClickable(true);
        arrivalTimeEditText.setOnClickListener(this);
        departureDateEditText = findViewById(R.id.departureDateEditText);
        departureDateEditText.setFocusable(false);
        departureDateEditText.setClickable(true);
        departureDateEditText.setOnClickListener(this);
        departureTimeEditText = findViewById(R.id.departureTimeEditText);
        departureTimeEditText.setFocusable(false);
        departureTimeEditText.setClickable(true);
        departureTimeEditText.setOnClickListener(this);
        accommodationEditText = findViewById(R.id.accommodationEditText);
        accommodationEditText.setFocusable(false);
        accommodationEditText.setClickable(true);
        accommodationEditText.setOnClickListener(this);
            arrivalDate.setTimeInMillis(currTrip.getStartDate());
            setDateLabel(arrivalDate, arrivalDateEditText);
            setTimeLabel(arrivalDate, arrivalTimeEditText);
            departureDate.setTimeInMillis(currTrip.getEndDate());
            setDateLabel(departureDate, departureDateEditText);
            setTimeLabel(departureDate, departureTimeEditText);
            accommodationEditText.setText(currTrip.getStartName());
            accommodationLat = currTrip.getStartLat();
            accommodationLng = currTrip.getStartLng();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

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

        new DatePickerDialog(this, date, arrivalDate.get(Calendar.YEAR), arrivalDate.get(Calendar.MONTH),
                arrivalDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setDateLabel(final Calendar date, EditText label) {
        String myFormat = "MMMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(" " + sdf.format(date.getTime()));
    }

    public void getTime(final Calendar dateCalendar, final EditText label) {
        final TimePickerDialog.OnTimeSetListener date = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                dateCalendar.set(Calendar.HOUR_OF_DAY, hour);
                dateCalendar.set(Calendar.MINUTE, minute);
                setTimeLabel(dateCalendar, label);
            }
        };

        new TimePickerDialog(this, date, arrivalDate
                .get(Calendar.HOUR_OF_DAY), arrivalDate.get(Calendar.MINUTE),
                false).show();
    }

    public void setTimeLabel(final Calendar date, EditText label) {
        String myFormat = "hh:mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        label.setText(" " + sdf.format(date.getTime()));
    }

    public void onPickButtonClick() {
        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {
            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            LatLng accommodationLatLng = place.getLatLng();
            accommodationLat = accommodationLatLng.latitude;
            accommodationLng = accommodationLatLng.longitude;
            String attributions = PlacePicker.getAttributions(data);
            Log.d("Place Picker", "Lat Long: "+ accommodationLat + ", " + accommodationLng);
            if (attributions == null) {
                attributions = "";
            }
            if (place.getPlaceTypes().get(0) == 0) {
                accommodationEditText.setText(address);
            }
            else {
                accommodationEditText.setText(name);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
            case R.id.accommodationEditText:
                onPickButtonClick();
                break;
            case R.id.fab:
                currTrip.setStartDate(arrivalDate.getTimeInMillis());
                currTrip.setEndDate(departureDate.getTimeInMillis());
                currTrip.setStartLat(accommodationLat);
                currTrip.setStartLng(accommodationLng);
                currTrip.setStartName(accommodationEditText.getText().toString());
                putTrip(currTrip);

                getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private void putTrip(Trip trip) {
        CommonDependencyProvider commonDependencyProvider = new CommonDependencyProvider();
        User user = commonDependencyProvider.getAppHelper().getLoggedInUser();
        ApiInterface apiInterface = ApiClient.getApiInstance();
        Call<Void> call = apiInterface.putTripData(user.getEmail(), trip.getTripId(), trip);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("PostTrip", "Successful");
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("PostTrip", "Retrofit failed to get data");
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}

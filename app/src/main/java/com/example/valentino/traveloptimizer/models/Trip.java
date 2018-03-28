package com.example.valentino.traveloptimizer.models;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class Trip implements Serializable {
    public String tripId;
    public String city;
    public double startLat;
    public double startLng;
    public String startName;
    public long startDate;
    public long endDate;
    public String itinerary;
    public List<String> places;

    public Trip() {}

    public Trip(String id, String city) {
        this.tripId = id;
        this.city = city;
        this.places = new ArrayList<>();
    }

    public Trip(String city, double startLat, double startLng, String startName, long startDate, long endDate) {
        this.city = city;
        this.startLat = startLat;
        this.startLng = startLng;
        this.startName = startName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

package com.example.valentino.traveloptimizer.models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class Trip {
    public String tripId;
    public String city;
    public Double startLat;
    public Double startLng;
    public Date startDate;
    public Date endDate;
    public String itinerary;
    public List<String> places;

    public Trip(String id, String city) {
        this.tripId = id;
        this.city = city;
        this.places = new ArrayList<>();
    }
}

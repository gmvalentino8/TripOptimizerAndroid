package com.example.valentino.traveloptimizer.models;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class Trip implements Serializable {
    @SerializedName("tid")
    private String tripId;
    @SerializedName("city")
    private String city;
    @Expose(serialize = false, deserialize = false)
    private double startLat;
    @Expose(serialize = false, deserialize = false)
    private double startLng;
    @SerializedName("startLocation")
    private String startName;
    @SerializedName("startDate")
    private long startDate;
    @SerializedName("endDate")
    private long endDate;
    @SerializedName("itinerary")
    private String itinerary;
    @SerializedName("email")
    private String email;
    private List<String> places;

    public Trip() {}

    public Trip(String id, String city) {
        this.tripId = id;
        this.city = city;
        this.places = new ArrayList<>();
    }

    public Trip(String id, String city, double startLat, double startLng, String startName, long startDate, long endDate, String itinerary) {
        this.tripId = id;
        this.city = city;
        this.startLat = startLat;
        this.startLng = startLng;
        this.startName = startName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itinerary = itinerary;
        places = Arrays.asList(itinerary.split(" "));
    }


    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public List<String> getPlaces() {
        if (itinerary == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(itinerary.split(" "));
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }
}

package com.example.valentino.traveloptimizer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Valentino on 3/27/18.
 */

public class Place {
    @SerializedName("pid")
    private String placeId;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("latitude")
    private Double lat;
    @SerializedName("longitude")
    private Double lng;
    @SerializedName("category")
    private String category;
    @SerializedName("openTime")
    private String openTime;
    @SerializedName("closeTime")
    private String closeTime;
    @SerializedName("city")
    private String city;
    @SerializedName("tripRating")
    private Double tripAdvisorRating;
    @SerializedName("yelpRating")
    private Double yelpRating;

    public Place(String placeId, String name, String address, Double lat, Double lng, String category, String openTime, String closeTime) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.city = "Chicago";
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTripAdvisorRating() {
        return tripAdvisorRating;
    }

    public void setTripAdvisorRating(Double tripAdvisorRating) {
        this.tripAdvisorRating = tripAdvisorRating;
    }

    public Double getYelpRating() {
        return yelpRating;
    }

    public void setYelpRating(Double yelpRating) {
        this.yelpRating = yelpRating;
    }
}

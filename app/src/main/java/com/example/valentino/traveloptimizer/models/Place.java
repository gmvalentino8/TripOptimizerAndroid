package com.example.valentino.traveloptimizer.models;

/**
 * Created by Valentino on 3/27/18.
 */

public class Place {
    public String placeId;
    public String name;
    public String address;
    public String lat;
    public String lng;
    public String category;
    public String openTime;
    public String closeTime;
    public String city;

    public Place(String placeId, String name, String address, String category, String openTime, String closeTime) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.city = "Chicago";
    }

}

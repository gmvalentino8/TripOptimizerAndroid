package com.example.valentino.traveloptimizer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Valentino on 3/28/18.
 */

public class TestApiTrip {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Trip> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Trip> getData() {
        return data;
    }

    public void setData(List<Trip> data) {
        this.data = data;
    }


}

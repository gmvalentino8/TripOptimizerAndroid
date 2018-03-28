package com.example.valentino.traveloptimizer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Valentino on 3/28/18.
 */

public class TestApiPlace {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<Place> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Place> getData() {
        return data;
    }

    public void setData(List<Place> data) {
        this.data = data;
    }
}

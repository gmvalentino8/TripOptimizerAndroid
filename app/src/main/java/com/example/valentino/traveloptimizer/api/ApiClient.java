package com.example.valentino.traveloptimizer.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client for making api calls using Retrofit.
 */

public class ApiClient {

    private static String ROOT_URL = "https://jc1ghs43d3.execute-api.us-east-2.amazonaws.com/";

    private static Retrofit getClientInstance() {
        return new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiInterface getApiInstance() {
        return getClientInstance().create(ApiInterface.class);
    }

}

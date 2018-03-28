package com.example.valentino.traveloptimizer.api;

import com.example.valentino.traveloptimizer.models.Place;
import com.example.valentino.traveloptimizer.models.TestApiPlace;
import com.example.valentino.traveloptimizer.models.TestApiTrip;
import com.example.valentino.traveloptimizer.models.Trip;
import com.example.valentino.traveloptimizer.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for implemented API calls.
 */

public interface ApiInterface {

    @POST("prod/user")
    Call<Trip> postUserData(@Body User user);

    @GET("prod/user/{email}")
    Call<User> getUserData(@Path("email") String email);

    @GET("prod/user/{email}/trip")
    Call<List<Trip>> getUserTrips(@Path("email") String email);

    @GET("prod/user/{email}/trip")
    Call<TestApiTrip> testGetUserTrips(@Path("email") String email);

    @POST("prod/user/{email}/trip/")
    Call<Void> postTripData(@Path("email") String email, @Body Trip trip);

    @GET("prod/trip/{email}/trip/{tid}")
    Call<List<Trip>> getTripData(@Path("email") String email, @Path("tid") String tid);

    @PUT("prod/user/{email}/trip/{tid}")
    Call<Void> putTripData(@Path("email") String email, @Path("tid") String tid, @Body Trip trip);

    @DELETE("prod/user/{email}/trip/{tid}")
    Call<Void> deleteTrip(@Path("email") String email, @Path("tid") String tid);

    @GET("/prod/place")
    Call<List<Place>> getAllPlaces();

    @GET("/prod/place")
    Call<TestApiPlace> testGetAllPlaces();
}

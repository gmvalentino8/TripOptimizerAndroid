package com.example.valentino.traveloptimizer.api;

import com.example.valentino.traveloptimizer.models.Place;
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
    Call<Void> postUserData(@Body User user);

    @GET("prod/user/{email}")
    Call<List<User>> getUserData(@Path("email") String email);

    @GET("prod/user/{email}/trip")
    Call<List<Trip>> getUserTrips(@Path("email") String email);

    @POST("prod/user/{email}/trip/")
    Call<Void> postTripData(@Path("email") String email, @Body Trip trip);

    @GET("prod/user/{email}/trip/{tid}")
    Call<List<Trip>> getTripData(@Path("email") String email, @Path("tid") String tid);

    @PUT("prod/user/{email}/trip/{tid}")
    Call<Void> putTripData(@Path("email") String email, @Path("tid") String tid, @Body Trip trip);

    @DELETE("prod/user/{email}/trip/{tid}")
    Call<Void> deleteTrip(@Path("email") String email, @Path("tid") String tid);

    @GET("/prod/place")
    Call<List<Place>> getAllPlaces(@Query("city") String city, @Query("category") String category);

    @GET("/prod/place/{pid}")
    Call<List<Place>> getPlaceData(@Path("pid") String pid);

    @GET("/prod/trip/{tid}/place")
    Call<List<Place>> getTripPlaces(@Path("tid") String tid);

    @GET("/prod/trip/{tid}/place")
    Call<List<Place>> getTripItinerary(@Path("tid") String tid);

    @DELETE("/prod/trip/{tid}/place/{pid}")
    Call<Void> deleteTripPlace(@Path("tid") String tid, @Path("pid") String pid);

    @GET("prod/place/recommended")
    Call<List<Place>> getRecommendedPlaces(@Query("city") String city, @Query("age") String age);

    @POST("/prod/trip/{tid}/place/{pid}")
    Call<Void> postTripPlace(@Path("tid") String tid, @Path("pid") String pid);
}

package com.example.dishessurrounding2.service;


import com.example.dishessurrounding2.model.StoreFeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StoreFeedAPIServices {

    @GET("store_feed")
    Call<StoreFeedResponse> fetchStoreFeed(
            @Query("lat") String lat, @Query("lng") String lng,
            @Query("offset") Integer offset, @Query("limit") Integer limit);
}

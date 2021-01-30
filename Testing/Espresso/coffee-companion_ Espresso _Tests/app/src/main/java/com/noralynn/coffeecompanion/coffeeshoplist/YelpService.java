package com.noralynn.coffeecompanion.coffeeshoplist;

import com.noralynn.coffeecompanion.coffeeshoplist.models.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YelpService {

    @GET("businesses/search")
    Call<Search> searchBusinesses(
            @Header("Authorization") String authHeader,
            @Query("location") String location,
            @Query("categories") String categories);

}

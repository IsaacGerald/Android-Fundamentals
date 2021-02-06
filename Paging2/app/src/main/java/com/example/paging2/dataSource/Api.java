package com.example.paging2.dataSource;


import com.example.paging2.models.StackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("answers")
    Call<StackResponse> getAnswers(
            @Query("page") int page,
            @Query("pagesize") int size,
            @Query("site") String site
    );

}

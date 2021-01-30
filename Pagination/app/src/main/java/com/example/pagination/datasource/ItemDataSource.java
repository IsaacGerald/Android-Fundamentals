package com.example.pagination.datasource;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.pagination.models.Item;
import com.example.pagination.models.StackResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {
    private static final String TAG = ItemDataSource.class.getSimpleName();
    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "stackoverflow";



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Item> callback) {
     Api mApiService = ApiClient.getInstance().buildService(Api.class);
      mApiService.getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME).enqueue(new Callback<StackResponse>() {
          @Override
          public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
              Log.d(TAG, "onResponse: loading initial data ");
              if (response.body() != null){
                  callback.onResult(response.body().items, null, FIRST_PAGE + 1);
              }else {
                  Log.d(TAG, "onResponse: Retry loading initial data");
              }

          }

          @Override
          public void onFailure(Call<StackResponse> call, Throwable t) {
              Log.d(TAG, "onFailure: loadInitial " + t.getMessage());
          }
      });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Item> callback) {
     Api mApiService = ApiClient.getInstance().buildService(Api.class);
      mApiService.getAnswers(params.key, PAGE_SIZE, SITE_NAME).enqueue(new Callback<StackResponse>() {
          @Override
          public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
              Log.d(TAG, "onResponse: loading data before..");
              if (response.body() != null){
                  Integer key = (params.key > 0) ? params.key - 1 : null;
                  callback.onResult(response.body().items, key);
              }else {
                  Log.d(TAG, "onResponse: Retry loading data before");
              }
          }

          @Override
          public void onFailure(Call<StackResponse> call, Throwable t) {
              Log.d(TAG, "onFailure: loadBefore " + t.getMessage());
          }
      });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Item> callback) {

     Api mApiService = ApiClient.getInstance().buildService(Api.class);
     mApiService.getAnswers(params.key, PAGE_SIZE, SITE_NAME).enqueue(new Callback<StackResponse>() {
         @Override
         public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
             Log.d(TAG, "onResponse: loading data after...");
             if (response.body() != null){
                 Integer key = response.body().has_more ? params.key + 1 : null;
                 callback.onResult(response.body().items, key);
             }else {
                 Log.d(TAG, "onResponse: Retry loading data after"  );
             }

         }

         @Override
         public void onFailure(Call<StackResponse> call, Throwable t) {
             Log.d(TAG, "onFailure: loadAfter " + t.getMessage());
         }
     });

    }
}

package com.example.paging2.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.paging2.models.Item;
import com.example.paging2.models.StackResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {
    private static final String TAG = ItemDataSource.class.getSimpleName();
    public  static int PAGE_SIZE = 50;
    final int FIRST_PAGE = 1;
    final String SITE_NAME = "stackoverflow";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Item> callback) {
        Api apiClient = ApiClient.getInstance().buildService(Api.class);
        apiClient.getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackResponse>() {
                    @Override
                    public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().items, null, FIRST_PAGE + 1);
                        }else{
                            Log.d(TAG, "onResponse: loadInitial , retry loading");
                        }
                    }

                    @Override
                    public void onFailure(Call<StackResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: loadInitial onFailure " + t.getMessage());
                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Item> callback) {
        Api api = ApiClient.getInstance().buildService(Api.class);
        api.getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackResponse>() {
                    @Override
                    public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {

                        if (response.body() != null) {
                            Integer key = params.key > 1 ? params.key - 1 : null;
                            callback.onResult(response.body().items, key);
                        }else {
                            Log.d(TAG, "onResponse: loadBefore Retry loading");
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
        Api api = ApiClient.getInstance().buildService(Api.class);
        api.getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackResponse>() {
                    @Override
                    public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
                        Log.d(TAG, "onResponse LoadAfter: fetching data..." + response.body());
                        if (response.body() != null) {
                            Integer key = (response.body().has_more) ? params.key + 1 : null;
                            Log.d(TAG, "onResponse: loadAfter..Loading ");
                            callback.onResult(response.body().items, key);
                        }else {
                            Log.d(TAG, "onResponse: loadAfter Retry loading");
                        }


                    }

                    @Override
                    public void onFailure(Call<StackResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: loadAfter " + t.getMessage() );
                    }
                });
    }
}

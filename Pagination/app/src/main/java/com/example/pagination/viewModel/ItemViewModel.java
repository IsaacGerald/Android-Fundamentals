package com.example.pagination.viewModel;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.pagination.datasource.Api;
import com.example.pagination.datasource.ApiClient;
import com.example.pagination.datasource.ItemDataSource;
import com.example.pagination.datasource.ItemDataSourceFactory;
import com.example.pagination.models.Item;
import com.example.pagination.models.StackResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemViewModel extends AndroidViewModel {
    private static final String TAG = "ViewModel";

     public LiveData<PagedList<Item>> itemPageList;
     public LiveData<PageKeyedDataSource<Integer, Item>> liveDataSource;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGE_SIZE)
                .build();

        itemPageList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }

    public LiveData<PagedList<Item>> getItemPageList() {
        return itemPageList;
    }

    public LiveData<PageKeyedDataSource<Integer, Item>> getLiveDataSource() {
        return liveDataSource;
    }
    //    private MutableLiveData<StackResponse> mDataResponse = new MutableLiveData<>();
//
//
//
//    public LiveData<StackResponse> getStackResponse(){
//        getResponseApiCall();
//        return mDataResponse;
//    }
//
//    private void getResponseApiCall(){
//        Api apiServices = ApiClient.getInstance().buildService(Api.class);
//        Call<StackResponse> mCall = apiServices.getAnswers(1, 50, "stackoverflow");
//        mCall.enqueue(new Callback<StackResponse>() {
//            @Override
//            public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
//                if (response != null){
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mDataResponse.postValue(response.body());
//                        }
//                    }, 4000);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<StackResponse> call, Throwable t) {
//               mDataResponse.postValue(null);
//            }
//        });
//
//    }
}

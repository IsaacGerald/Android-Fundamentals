package com.example.paging2.dataSource;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String URL = "https://api.stackexchange.com/2.2/";
    private static ApiClient INSTANCE;

    //Create logger
    private static HttpLoggingInterceptor logger = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    //create okHttp client
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder()
            .addInterceptor(logger);

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());
    private static Retrofit retrofit = builder.build();

    public static <s> s buildService(Class<s> serviceType){
        return retrofit.create(serviceType);
    }

    public static synchronized ApiClient getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ApiClient();
        }

        return INSTANCE;
    }




}

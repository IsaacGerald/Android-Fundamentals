package com.noralynn.coffeecompanion.coffeeshoplist;

import android.Manifest.permission;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;

import com.noralynn.coffeecompanion.CoffeeShopsIdlingResource;
import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.coffeeshoplist.models.Businesses;
import com.noralynn.coffeecompanion.coffeeshoplist.models.Search;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShopListActivity.COFFEE_SHOPS_BUNDLE_KEY;
import static com.noralynn.coffeecompanion.ApiConstants.CONSUMER_KEY;



class CoffeeShopListViewPresenter {
    private static final String TAG = CoffeeShopListActivity.class.getSimpleName();
    @NonNull
    private CoffeeShopListView coffeeShopListView;

    @Nullable
    private CoffeeShopListModel coffeeShopListModel;

    @Nullable
    private CoffeeShopsIdlingResource mShopsIdlingResource;

    @NonNull
    private Callback<Search> coffeeShopSearchCallback = new Callback<Search>() {
        @Override
        public void onResponse(Call<Search> call, Response<Search> response) {
            if (response != null){
                Log.d(TAG, "onResponse: " + response.body());
                Search searchResponse = response.body();
                List<Businesses> business= searchResponse.businesses;
                handleSearchResponse(business);
            }else{
                Log.d(TAG, "onResponse: response is null");
            }

            // FOR OUR TESTS:
            notifyIdlingResource();

        }

        @Override
        public void onFailure(Call<Search> call, Throwable t) {

            Log.e("CoffeeShop", "onFailure()", t);
            coffeeShopListView.showMessage(R.string.error_unable_to_load_coffee_shops);

//             FOR OUR TESTS:
            notifyIdlingResource();

        }
    };

    CoffeeShopListViewPresenter(@NonNull CoffeeShopListView coffeeShopListView) {
        this.coffeeShopListView = coffeeShopListView;
    }

    void onCreate(@Nullable Bundle savedInstanceState, boolean locationPermissionHasBeenGranted) {
        coffeeShopListModel = getCoffeeShopListModelFromBundle(savedInstanceState, locationPermissionHasBeenGranted);

        if (coffeeShopListModel.getCoffeeShops() != null) {
            // We already have our coffee shops; just return.
            coffeeShopListView.displayCoffeeShops(coffeeShopListModel);
            return;
        }

        if (!coffeeShopListModel.hasLocationPermission()) {
            coffeeShopListView.displayPermissionRequest();
        } else {
            onPermissionGranted();
        }
    }

    private CoffeeShopListModel getCoffeeShopListModelFromBundle(@Nullable Bundle savedInstanceState, boolean locationPermissionHasBeenGranted) {
        CoffeeShopListModel model = null;
        if (null != savedInstanceState) {
            model = savedInstanceState.getParcelable(COFFEE_SHOPS_BUNDLE_KEY);
        }
        return model != null ? model : new CoffeeShopListModel(locationPermissionHasBeenGranted);
    }


    private void sendYelpCoffeeShopSearchRequest() {
        // First, let's see if we can get our coordinates. If not, we'll have to listen
        // for changes in device Location, because the device may not have registered
        // a Location update yet.
//       CoordinateOptions coordinateOptions = getCoordinateOptions();
//        if (null == coordinateOptions) {
//            listenForLocationUpdates();
//            return;
//        }

//        double latitude = 29.7485868638914;
//        double longitude = -95.4620253208921;



      // YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
//       YelpAPI yelpAPI = apiFactory.createAPI();
//
//        Map<String, String> params = new HashMap<>();
//        params.put("category_filter", "coffee");
//        params.put("sort", "1");

        Map<String, String> params = new HashMap<>();
        params.put("categories", "coffee");
//        params.put("sort", "1");
//        Call<SearchResponse> call = yelpAPI.search(coordinateOptions, params);


        YelpService mYelpService = ServiceBuilder.buildService(YelpService.class);
        Call<Search> call = mYelpService
                .searchBusinesses("Bearer " + CONSUMER_KEY,
                        "Houston",
                        "coffee");

        call.enqueue(coffeeShopSearchCallback);

    }

    private void handleSearchResponse(@Nullable List<Businesses> businesses) {
        if (businesses == null || businesses.size() == 0) {
            coffeeShopListView.showMessage(R.string.no_nearby_coffee_shops);
            return;
        }

        List<CoffeeShop> coffeeShops = new ArrayList<>(businesses.size());
        for (Businesses business : businesses) {
            CoffeeShop coffeeShop = new CoffeeShop(business);
            coffeeShops.add(coffeeShop);
        }

        coffeeShopListModel.setCoffeeShops(coffeeShops);
        coffeeShopListView.displayCoffeeShops(coffeeShopListModel);
        if (coffeeShops.size() > 0) {
            coffeeShopListView.showShareButton();
        } else {
            coffeeShopListView.hideShareButton();
        }
    }

    @Nullable
    private CoordinateOptions getCoordinateOptions() {
//        Location location = getCurrentLocation();
//        if (null == location) {
//            listenForLocationUpdates();
//            return null;
//        }

        double latitude = 29.7485868638914;
        double longitude = -95.4620253208921;


//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        double altitude = location.getAltitude();

        return CoordinateOptions.builder()
                .latitude(latitude)
                .longitude(longitude)
                //.altitude(altitude)
                .build();
    }

    @Nullable
    private Location getCurrentLocation() {
        Context context = coffeeShopListView.getContext();
        // Make sure we really do have permission before trying to access location!
        if (ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CoffeeShopListActivity", "We don't have permission to get the current device location...");
            return null;
        }

        // Get the user's current location
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        String locationProvider = getLocationProvider(manager);
        if (null == locationProvider) {
            return null;
        }

        return manager.getLastKnownLocation(locationProvider);
    }

    @NonNull
    @VisibleForTesting
    CoffeeShopsIdlingResource getCoffeeShopsIdlingResources(){
        if (null == mShopsIdlingResource){
            mShopsIdlingResource = new CoffeeShopsIdlingResource();
            if (coffeeShopListModel.getCoffeeShops() != null && !coffeeShopListModel.getCoffeeShops().isEmpty()){
                mShopsIdlingResource.onSearchCompleted();
            }
        }

        return mShopsIdlingResource;
    }

    //FOR OUR TEST
    private void notifyIdlingResource() {
        if (null != mShopsIdlingResource){
            mShopsIdlingResource.onSearchCompleted();
        }

    }

    private String getLocationProvider(@NonNull LocationManager manager) {
        String locationProvider = null;
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        return locationProvider;
    }

    private void listenForLocationUpdates() {
        Context context = coffeeShopListView.getContext();
        final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CoffeeShopListActivity", "We don't have permission to listen to the current device location...");
            return;
        }

        manager.requestLocationUpdates(getLocationProvider(manager), 60000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                manager.removeUpdates(this);
                sendYelpCoffeeShopSearchRequest();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    void onShareClicked() {
        List<CoffeeShop> coffeeShops = coffeeShopListModel.getCoffeeShops();
        if (null != coffeeShops && coffeeShops.size() > 0) {
            coffeeShopListView.sendShareIntent(coffeeShopListModel.getCoffeeShops().get(0));
        }
    }

    void onPermissionGranted() {
        coffeeShopListModel.setHasLocationPermission(true);
        sendYelpCoffeeShopSearchRequest();
    }

    void onRequestPermissionFailed() {
        coffeeShopListModel.setHasLocationPermission(false);
        coffeeShopListView.showPermissionError();
    }

    @NonNull
    CoffeeShopListModel getCoffeeShopListModel() {
        return coffeeShopListModel;
    }

    public void onClickCoffeeShop(@Nullable CoffeeShop coffeeShop) {
        if (null == coffeeShop) {
            return;
        }

        coffeeShopListView.openCoffeeShopDetailsActivity(coffeeShop);
    }
}

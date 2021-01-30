package com.noralynn.coffeecompanion.beveragelist;

import android.content.Context;

import androidx.annotation.NonNull;

import com.noralynn.coffeecompanion.common.Beverage;

interface BeverageListView {

    void startCoffeeShopListActivity();

    void startBeverageActivity(@NonNull Beverage beverage);

    void displayBeverages(@NonNull BeverageListModel beverageListModel);

    @NonNull
    Context getContext();

}

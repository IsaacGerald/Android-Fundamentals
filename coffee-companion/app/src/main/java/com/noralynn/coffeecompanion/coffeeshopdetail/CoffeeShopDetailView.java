package com.noralynn.coffeecompanion.coffeeshopdetail;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface CoffeeShopDetailView {

    void displayCoffeeShop(@Nullable CoffeeShopDetailModel coffeeShopDetailModel);

    void sendMapsIntent(@NonNull CoffeeShopDetailModel coffeeShopDetailModel);

}

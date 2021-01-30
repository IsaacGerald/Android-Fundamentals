package com.noralynn.coffeecompanion.coffeeshoplist.idlingresource;


import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import com.bumptech.glide.request.ResourceCallback;

public class CoffeeShopsIdlingResource implements IdlingResource {

    @Nullable
    private ResourceCallback resourceCallback;

    private boolean isIdle = false;

    @Override
    public String getName() {
        return CoffeeShopsIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(@Nullable ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void onSearchCompleted() {
        isIdle = true;
        notifyResourceCallback();
    }

    private void notifyResourceCallback() {
        if (resourceCallback != null && isIdle) {
            resourceCallback.onTransitionToIdle();
        }
    }
}

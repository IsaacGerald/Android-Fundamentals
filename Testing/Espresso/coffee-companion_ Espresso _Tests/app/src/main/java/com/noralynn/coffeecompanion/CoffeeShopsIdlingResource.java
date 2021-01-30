package com.noralynn.coffeecompanion;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

public class CoffeeShopsIdlingResource implements IdlingResource {

    @Nullable
    public ResourceCallback mResourceCallback;

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
    public void registerIdleTransitionCallback(ResourceCallback callback) {
         this.mResourceCallback = callback;
    }

    public void onSearchCompleted(){
         isIdle = true;
         notifyResourceCallBack();
    }

    public void notifyResourceCallBack(){
        if (mResourceCallback != null && isIdle){
              mResourceCallback.onTransitionToIdle();
        }
    }
}

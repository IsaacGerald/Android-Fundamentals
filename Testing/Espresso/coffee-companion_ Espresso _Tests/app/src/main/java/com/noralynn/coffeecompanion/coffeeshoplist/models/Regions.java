package com.noralynn.coffeecompanion.coffeeshoplist.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Regions implements Parcelable {
    private Center center;

    protected Regions(Parcel in) {
    }

    public static final Creator<Regions> CREATOR = new Creator<Regions>() {
        @Override
        public Regions createFromParcel(Parcel in) {
            return new Regions(in);
        }

        @Override
        public Regions[] newArray(int size) {
            return new Regions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


    private class Center{
        private String latitude;
        private String longitude;
    }



}

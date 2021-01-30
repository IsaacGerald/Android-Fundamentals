package com.noralynn.coffeecompanion.coffeeshoplist.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Search implements Parcelable {

    public List<Businesses> businesses;

    private Regions region;

    private Integer total;


    protected Search(Parcel in) {
        businesses = in.createTypedArrayList(Businesses.CREATOR);
        region = in.readParcelable(Regions.class.getClassLoader());
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readInt();
        }
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

    public List<Businesses> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Businesses> businesses) {
        this.businesses = businesses;
    }

    public Regions getRegions() {
        return region;
    }

    public void setRegions(Regions regions) {
        this.region = regions;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(businesses);
        dest.writeParcelable(region, flags);
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(total);
        }
    }
}

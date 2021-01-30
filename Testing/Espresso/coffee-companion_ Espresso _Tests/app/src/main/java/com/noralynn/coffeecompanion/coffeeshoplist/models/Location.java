package com.noralynn.coffeecompanion.coffeeshoplist.models;

import android.os.Parcel;
import android.os.Parcelable;

 public class Location implements Parcelable {

    public String address1;
    public String address2;
    public String address3;
    public String  city;
    public String  zip_code;
    public String  country;
    public String  state;

     protected Location(Parcel in) {
         address1 = in.readString();
         address2 = in.readString();
         address3 = in.readString();
         city = in.readString();
         zip_code = in.readString();
         country = in.readString();
         state = in.readString();
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(address1);
         dest.writeString(address2);
         dest.writeString(address3);
         dest.writeString(city);
         dest.writeString(zip_code);
         dest.writeString(country);
         dest.writeString(state);
     }

     @Override
     public int describeContents() {
         return 0;
     }

     public static final Creator<Location> CREATOR = new Creator<Location>() {
         @Override
         public Location createFromParcel(Parcel in) {
             return new Location(in);
         }

         @Override
         public Location[] newArray(int size) {
             return new Location[size];
         }
     };

     public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}

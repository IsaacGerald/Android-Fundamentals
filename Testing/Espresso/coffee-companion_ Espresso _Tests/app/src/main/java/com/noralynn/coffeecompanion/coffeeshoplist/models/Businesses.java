package com.noralynn.coffeecompanion.coffeeshoplist.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

 public class Businesses implements Parcelable {

    public String id;
    public ArrayList<Category> categories;
    public String alias;
    public String name;
    public String image_url;
    public Boolean is_closed;
    public String url;
    public String review_count;
    public double rating;
    public Coordinate coordinates;
    public String price;
    public Location location;
    public String phone;
    public String display_phone;
    public double distance;


    protected Businesses(Parcel in) {
       id = in.readString();
       alias = in.readString();
       name = in.readString();
       image_url = in.readString();
       byte tmpIs_closed = in.readByte();
       is_closed = tmpIs_closed == 0 ? null : tmpIs_closed == 1;
       url = in.readString();
       review_count = in.readString();
       rating = in.readDouble();
       price = in.readString();
       phone = in.readString();
       display_phone = in.readString();
       distance = in.readDouble();
    }

    public static final Creator<Businesses> CREATOR = new Creator<Businesses>() {
       @Override
       public Businesses createFromParcel(Parcel in) {
          return new Businesses(in);
       }

       @Override
       public Businesses[] newArray(int size) {
          return new Businesses[size];
       }
    };

    @Override
    public int describeContents() {
       return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(id);
       dest.writeString(alias);
       dest.writeString(name);
       dest.writeString(image_url);
       dest.writeByte((byte) (is_closed == null ? 0 : is_closed ? 1 : 2));
       dest.writeString(url);
       dest.writeString(review_count);
       dest.writeDouble(rating);
       dest.writeString(price);
       dest.writeString(phone);
       dest.writeString(display_phone);
       dest.writeDouble(distance);
    }
 }

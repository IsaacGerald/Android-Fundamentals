package com.noralynn.coffeecompanion.coffeeshoplist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.noralynn.coffeecompanion.R;


class CoffeeShopViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private TextView mNameTextView;

    @NonNull
    private TextView mDistanceTextView;

    @NonNull
    private RatingBar mRatingBar;

    @NonNull
    private TextView mIsClosedTextView;
    private final ImageView mImageView;

    CoffeeShopViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
        mDistanceTextView = (TextView) itemView.findViewById(R.id.distance_text);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.rating);
        mIsClosedTextView = (TextView) itemView.findViewById(R.id.is_closed_text);
        mImageView = (ImageView)itemView.findViewById(R.id.imageView);
    }

    void bind(@NonNull CoffeeShop coffeeShop) {
        Context context = itemView.getContext();

        mNameTextView.setText(coffeeShop.getName());
        mRatingBar.setRating((float)coffeeShop.getRating());
        Glide.with(mImageView)
                .load(coffeeShop.getImageUrl())
                .into(mImageView);

        String availabilityMessage;
        if (coffeeShop.isClosed()) {
            availabilityMessage = context.getString(R.string.closed);
        } else {
            availabilityMessage = context.getString(R.string.open);
        }
        mIsClosedTextView.setText(availabilityMessage);

        double distance = coffeeShop.getDistance();
        if (distance != 0.0d) {
            mDistanceTextView.setVisibility(View.VISIBLE);
            mDistanceTextView.setText(coffeeShop.getHumanReadableDistance());
        } else {
            mDistanceTextView.setVisibility(View.GONE);
        }
    }

}

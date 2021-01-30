package com.google.codelabs.mdc.java.shrine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

public class ProductCardViewHolder extends RecyclerView.ViewHolder {


    public final TextView mProductTitle;
    public final TextView mProductPrice;
    public final NetworkImageView mProductImage;

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Find and store views from itemView

        mProductImage = itemView.findViewById(R.id.product_image);
        mProductTitle = itemView.findViewById(R.id.product_title);
        mProductPrice = itemView.findViewById(R.id.product_price);

    }
}

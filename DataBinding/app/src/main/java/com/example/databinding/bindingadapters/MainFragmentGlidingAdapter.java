package com.example.databinding.bindingadapters;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethods;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.databinding.R;
import com.example.databinding.utils.StringUtil;


public class MainFragmentGlidingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, int imageUrl){

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_dollar_sign_background)
                .error(R.drawable.ic_dollar_sign_background);


                Glide.with(view.getContext())
                        .setDefaultRequestOptions(options)
                        .load(imageUrl)
                        .into(view);


    }

    @BindingAdapter({"requestListener", "imageResource"})
    public static void bindRequestListener(ImageView view, RequestListener requestListener, int imageResource){

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_dollar_sign_background)
                .error(R.drawable.ic_dollar_sign_background);


                Glide.with(view.getContext())
                        .setDefaultRequestOptions(options)
                        .load(imageResource)
                        .listener(requestListener)
                        .into(view);



    }
}

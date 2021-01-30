package com.example.databinding.models;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.databinding.BR;
import com.example.databinding.IMainActivity;

public class CartItemViewModel extends BaseObservable {
    private static final String TAG = CartItemViewModel.class.getSimpleName();

    private CartItem mCartItem;

    @Bindable
    public CartItem getCartItem() {
        return mCartItem;
    }


    public void setCartItem(CartItem cartItem) {
        Log.d(TAG, "setCartItem: updating cart");
        mCartItem = cartItem;
        notifyPropertyChanged(BR.cartItem);
    }

    public String getQuantityString(CartItem cartItem) {
        return ("qty: " + String.valueOf(cartItem.getQuantity()));
    }

    public void increaseQuantity(Context context) {
        CartItem cartItem = getCartItem();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        setCartItem(cartItem);

        IMainActivity iMainActivity = (IMainActivity) context;
        iMainActivity.updateQuantity(cartItem.getProduct(), 1);
    }

    public void decreaseQuantity(Context context) {
        CartItem cartItem = getCartItem();
        IMainActivity iMainActivity = (IMainActivity) context;
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            setCartItem(cartItem);
            iMainActivity.updateQuantity(cartItem.getProduct(), -1);
        } else if (cartItem.getQuantity() == 1){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            setCartItem(cartItem);
            iMainActivity.removeCartItem(cartItem);
        }


    }
}

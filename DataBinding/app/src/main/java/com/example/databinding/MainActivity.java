package com.example.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.databinding.databinding.ActivityMainBinding;
import com.example.databinding.models.CartItem;
import com.example.databinding.models.CartViewModel;
import com.example.databinding.models.Product;
import com.example.databinding.utils.PreferenceKeys;
import com.example.databinding.utils.Products;
import com.example.databinding.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IMainActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

   ActivityMainBinding mBinding;
   private Handler mCheckOutHandler;
   private Runnable mCheckOutRunnable;
   private int mCheckoutTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.cart.setOnTouchListener(new CartTouchListener());
        mBinding.proceedToCheckout.setOnClickListener(mCheckoutListener);


        init();
        getShoppingCart();
    }

    private void init(){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, new MainsFragment(), getString(R.string.fragment_main));
        transaction.commit();
    }

    @Override
    public void inflateViewProductFragment(Product products) {
        ViewProductFragment productFragment = new ViewProductFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.intent_product), products);
        productFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, productFragment, getString(R.string.fragment_view_product));
        transaction.addToBackStack(getString(R.string.fragment_view_product));
        transaction.commit();

    }
    public void checkOut(){
        mBinding.progressBar.setVisibility(View.VISIBLE);
        mCheckOutHandler = new Handler(Looper.getMainLooper());
        mCheckOutRunnable = new Runnable() {
            @Override
            public void run() {
                mCheckOutHandler.postDelayed(mCheckOutRunnable, 200);
                mCheckoutTimer += 200;
                if (mCheckoutTimer >= 1600){
                    emptyCart();
                    mBinding.progressBar.setVisibility(View.GONE);
                    mCheckOutHandler.removeCallbacks(mCheckOutRunnable);
                    mCheckoutTimer = 0;
                }
            }
        };
        mCheckOutRunnable.run();
    }

    private void emptyCart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        for (String serialNumber : serialNumbers){
            editor.remove(serialNumber);
            editor.commit();
        }

        editor.remove(PreferenceKeys.shopping_cart);
        editor.commit();

        Toast.makeText(this, "thanks for shopping!", Toast.LENGTH_SHORT).show();
        removeViewCartFragment();
        getShoppingCart();
    }

    private void removeViewCartFragment() {

        getSupportFragmentManager().popBackStack();
        ViewCartFragment fragment = (ViewCartFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null){
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    public View.OnClickListener mCheckoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkOut();
        }
    };

    private void getShoppingCart(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        Products products = new Products();
        List<CartItem> cartItemList = new ArrayList<>();
        for (String serialNumber : serialNumbers){
            int quantity = preferences.getInt(serialNumber, 0);
            cartItemList.add(new CartItem(products.PRODUCT_MAP.get(serialNumber), quantity));

        }

        CartViewModel cartViewModel = new CartViewModel();
        cartViewModel.setCart(cartItemList);

        try{
            cartViewModel.setCartVisible(mBinding.getCartView().isCartVisible());

        } catch (NullPointerException e) {
            Log.d(TAG, "getShoppingCart: NullPointerException " + e.getMessage());
        }

        mBinding.setCartView(cartViewModel);

    }
private static class CartTouchListener implements View.OnTouchListener{

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
          v.setBackgroundColor(v.getContext().getResources().getColor(R.color.blue4));
          v.performClick();

          IMainActivity iMainActivity = (IMainActivity)v.getContext();
          iMainActivity.inflateViewCartFragment();
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN){
            v.setBackgroundColor(v.getContext().getResources().getColor(R.color.blue6));


        }

        return true;
    }
}
    @Override
    public void showQuantityDialog() {
      ChooseQuantityDialog chooseQuantityDialog = new ChooseQuantityDialog();
      chooseQuantityDialog.show(getSupportFragmentManager(), getString(R.string.choose_quantity_dialog));
    }

    @Override
    public void setQuantity(int quantity) {
        Log.d(TAG, "setQuantity: selected quantity " + quantity);
        ViewProductFragment viewProductFragment = (ViewProductFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_product));
       if (viewProductFragment != null){
           viewProductFragment.mBinding.getProductView().setQuantity(quantity);
       }
    }

    @Override
    public void addToCart(Product product, int quantity) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        Set<String> serialNumbers = pref.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        serialNumbers.add(String.valueOf(product.getSerial_number()));
        editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers);
        editor.commit();

        int currentQuantity = pref.getInt(String.valueOf(product.getSerial_number()), 0);
        editor.putInt(String.valueOf(product.getSerial_number()), (currentQuantity + quantity));
        editor.commit();

        setQuantity(1);

        Toast.makeText(this, "added to cart", Toast.LENGTH_SHORT).show();
        getShoppingCart();

    }

    @Override
    public void inflateViewCartFragment() {
       ViewCartFragment fragment = (ViewCartFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

       if (fragment == null){
           fragment = new ViewCartFragment();
           transaction.replace(R.id.main_container,  fragment, getString(R.string.fragment_view_cart));
           transaction.addToBackStack(getString(R.string.fragment_view_cart));
           transaction.commit();
       }
    }

    @Override
    public void setCartVisibility(boolean visibility) {
     mBinding.getCartView().setCartVisible(visibility);
    }

    @Override
    public void updateQuantity(Product product, int quantity) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor = preferences.edit();

    int currentQuantity = preferences.getInt(String.valueOf(product.getSerial_number()), 0);
    editor.putInt(String.valueOf(product.getSerial_number()), (currentQuantity + quantity));
    editor.commit();

    getShoppingCart();


    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(String.valueOf(cartItem.getProduct().getSerial_number()));
        editor.commit();

        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        if (serialNumbers.size() == 1 ){
           editor.remove(PreferenceKeys.shopping_cart);
           editor.commit();
        }else {
            serialNumbers.remove(String.valueOf(cartItem.getProduct().getSerial_number()));
            editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers);
            editor.commit();
        }

        getShoppingCart();

        ViewCartFragment fragment = (ViewCartFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        if (fragment != null){
            fragment.updateCartItems();
        }

    }
}
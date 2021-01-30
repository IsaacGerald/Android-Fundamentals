package com.example.databinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.databinding.databinding.FragmentViewProductBinding;
import com.example.databinding.models.Product;


/**
 * Created by User on 2/6/2018.
 */

public class ViewProductFragment extends Fragment  {

    private static final String TAG = "ViewProductFragment";

    // Data binding
    FragmentViewProductBinding mBinding;
    //vars
    private Product mProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mProduct = bundle.getParcelable(getString(R.string.intent_product));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewProductBinding.inflate(inflater);
        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProduct(mProduct);
        productViewModel.setQuantity(1);
        mBinding.setProductView(productViewModel);
       mBinding.setIMainActivity((IMainActivity)getContext());

        return mBinding.getRoot();
    }


}














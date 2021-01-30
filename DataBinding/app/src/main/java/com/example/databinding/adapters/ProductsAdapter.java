package com.example.databinding.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databinding.IMainActivity;
import com.example.databinding.ItemProductViewModel;
import com.example.databinding.R;
import com.example.databinding.databinding.ProductItemBinding;
import com.example.databinding.models.Product;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by User on 2/6/2018.
 */

public class ProductsAdapter extends  RecyclerView.Adapter<ProductsAdapter.BindingHolder>{

    private static final String TAG = "ProductsAdapter";

    private List<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public ProductsAdapter(Context context, List<Product> products) {
        mProducts = products;
        mContext = context;
    }

    public void refreshList(List<Product> products){
        mProducts.clear();
        mProducts.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewDataBinding binding = DataBindingUtil.inflate(
//                LayoutInflater.from(mContext), R.layout.product_item, parent, false);
        ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.product_item, parent, false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Product product = mProducts.get(position);
        ItemProductViewModel productViewModel = new ItemProductViewModel();
        productViewModel.setProduct(product);
        holder.binding.setItemViewProduct(productViewModel);
//        holder.binding.setProduct(product);
//        holder.binding.setVariable(BR.product, product);
        holder.binding.setIMainActivity((IMainActivity)mContext);
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder{

//        ViewDataBinding binding;
        ProductItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }



}














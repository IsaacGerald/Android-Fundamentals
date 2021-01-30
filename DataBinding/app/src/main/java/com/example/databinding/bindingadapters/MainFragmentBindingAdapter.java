package com.example.databinding.bindingadapters;



import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databinding.adapters.ProductsAdapter;
import com.example.databinding.models.Product;

import java.util.List;


public class MainFragmentBindingAdapter {

    public static final int SPAN_COUNT = 2;

    @BindingAdapter("productsList")
    public static void setProductsList(RecyclerView view, List<Product> products){
        if (products == null){
            return;
        }

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null){
            view.setLayoutManager(new GridLayoutManager(view.getContext(), SPAN_COUNT));
        }

        ProductsAdapter adapter = (ProductsAdapter)view.getAdapter();
        if (adapter == null){
            adapter = new ProductsAdapter(view.getContext(), products);
            view.setAdapter(adapter);

        }

    }
}

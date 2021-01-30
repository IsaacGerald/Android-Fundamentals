package com.example.pagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pagination.adapters.ItemListAdapter;
import com.example.pagination.models.Item;
import com.example.pagination.viewModel.ItemViewModel;

public class MainActivity extends AppCompatActivity {
  private ItemViewModel mViewModel;
  private TextView mTextView;
  private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final ItemListAdapter mAdapter = new ItemListAdapter(this);

        mRecyclerView = findViewById(R.id.list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ItemViewModel.class);
        mViewModel.getItemPageList().observe(this, new Observer<PagedList<Item>>() {
            @Override
            public void onChanged(PagedList<Item> items) {
                mAdapter.submitList(items);
            }
        });
        mRecyclerView.setAdapter(mAdapter);



//        mViewModel.getStackResponse().observe(this, new Observer<StackResponse>() {
//            @Override
//            public void onChanged(StackResponse stackResponse) {
////                Toast.makeText(MainActivity.this,
////                        String.valueOf(stackResponse.mItems.size()), Toast.LENGTH_SHORT).show();
//                if (stackResponse != null){
//                    mTextView.setText(String.valueOf(stackResponse.getQuota_max()));
//                }else {
//                    Toast.makeText(MainActivity.this, "Loading data", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
    }
}
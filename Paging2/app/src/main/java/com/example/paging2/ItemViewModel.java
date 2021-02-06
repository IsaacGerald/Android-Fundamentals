package com.example.paging2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.paging2.dataSource.ItemDataSource;
import com.example.paging2.dataSource.ItemDataSourceFactory;
import com.example.paging2.models.Item;

public class ItemViewModel extends AndroidViewModel {
 LiveData<PagedList<Item>> itemPagedList;
 LiveData<PageKeyedDataSource<Integer, Item>> liveDataSource;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGE_SIZE)
                .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }
}

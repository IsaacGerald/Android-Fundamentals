package codingwithmitch.com.tabiandating;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import codingwithmitch.com.tabiandating.models.User;
import codingwithmitch.com.tabiandating.util.Users;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeFragment";
    public static final int NUM_COLUMNS = 2;

    private ArrayList<User> mMatches = new ArrayList<>();
    private StaggeredGridLayoutManager mLayoutManager;
    private MainRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private IMainActivity mInterface;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView: Started.");

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        findMatches();

       return view;
    }
    private void findMatches(){
        Users users = new Users();
        if (mMatches != null){
            mMatches.clear();
        }
        for (User user : users.USERS){
          mMatches.add(user);
        }

        if (mRecyclerViewAdapter == null){
            initRecyclerView();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity)context;
    }

    private void initRecyclerView() {
        mLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mMatches);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
    public void scrollToTop(){
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        findMatches();
        onItemsLoadComplete();

    }
    private void onItemsLoadComplete(){
       mRecyclerViewAdapter.notifyDataSetChanged();
       mSwipeRefreshLayout.setRefreshing(false);
    }
}
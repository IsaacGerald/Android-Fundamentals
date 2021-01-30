package com.example.drawernavigation;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.drawernavigation.adapter.MyPagerAdapter;
import com.example.drawernavigation.fragments.TabFragment1;
import com.example.drawernavigation.fragments.TabFragment2;
import com.example.drawernavigation.fragments.TabFragment3;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    public static final int TAB_FRAGMENT1 = 0;
    public static final int TAB_FRAGMENT2 = 1;
    public static final int TAB_FRAGMENT3 = 2;
    private TabFragment1 mTabFragment1;
    private TabFragment2 mTabFragment2;
    private TabFragment3 mTabFragment3;
    private MyPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set toolbar to support action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setTitleTextColor(Color.WHITE);
        mDrawerLayout = findViewById(R.id.drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        setViewPager();

    }

    private void setViewPager() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mTabFragment1 = new TabFragment1();
        mTabFragment2 = new TabFragment2();
        mTabFragment3 = new TabFragment3();

        mAdapter.addFragment(mTabFragment1);
        mAdapter.addFragment(mTabFragment2);
        mAdapter.addFragment(mTabFragment3);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.getTabAt(TAB_FRAGMENT1).setText(R.string.tab1);
        mTabLayout.getTabAt(TAB_FRAGMENT2).setText(R.string.tab2);
        mTabLayout.getTabAt(TAB_FRAGMENT3).setText(R.string.tab3);


    }
}
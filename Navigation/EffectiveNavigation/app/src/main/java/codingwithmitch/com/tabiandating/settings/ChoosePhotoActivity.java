package codingwithmitch.com.tabiandating.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import codingwithmitch.com.tabiandating.R;


public class ChoosePhotoActivity extends AppCompatActivity {

    private static final String TAG = "ChoosePhotoActivity";
    private static final int GALLERY_FRAGMENT = 0;
    private static final int PHOTO_FRAGMENT = 1;

    //fragments
    private GalleryFragment mGalleryFragment;
    private PhotoFragment mPhotoFragment;
    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;

    //widgets


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        mViewPager = findViewById(R.id.viewpager_container);
        setViewPager();


    }

    private void setViewPager(){
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mGalleryFragment = new GalleryFragment();
        mPhotoFragment = new PhotoFragment();
        mAdapter.addFragment(mGalleryFragment);
        mAdapter.addFragment(mPhotoFragment);

        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_bottom);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(ChoosePhotoActivity.GALLERY_FRAGMENT).setText(getString(R.string.tag_fragment_gallery));
        tabLayout.getTabAt(ChoosePhotoActivity.PHOTO_FRAGMENT).setText(getString(R.string.tag_fragment_photo));
    }



}











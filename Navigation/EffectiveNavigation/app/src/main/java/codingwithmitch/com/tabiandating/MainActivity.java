package codingwithmitch.com.tabiandating;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import codingwithmitch.com.tabiandating.models.FragmentTag;
import codingwithmitch.com.tabiandating.models.Message;
import codingwithmitch.com.tabiandating.models.User;
import codingwithmitch.com.tabiandating.settings.SettingsFragment;
import codingwithmitch.com.tabiandating.util.PreferenceKeys;

public class MainActivity extends AppCompatActivity implements IMainActivity,
BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public static final int CONNECTION_FRAGMENT = 1;
    public static final int HOME_FRAGMENT = 0;
    public static final int MESSAGES_FRAGMENT = 2;

    //Widgets
    private BottomNavigationViewEx mBottomNavigationViewEx;
    private ImageView mHeaderImage;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    //Fragments
    private SettingsFragment mSettingsFragment;
    private AgreementFragment mAgreementFragment;
    private HomeFragment mHomeFragment;
    private SavedConnectionsFragment mConnectionsFragment;
    private MessagesFragment mMessagesFragment;
    private ChatFragment mChatFragment;
    private ViewProfileFragment mViewProfileFragment;

    //Vars
    private ArrayList<String> mFragmentTags = new ArrayList<>();
    private ArrayList<FragmentTag> mFragments = new ArrayList<>();
    private int mExitCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        mNavigationView = findViewById(R.id.navigation_view);
        View headerView = mNavigationView.getHeaderView(0);
        mHeaderImage = headerView.findViewById(R.id.header_image);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        isFirstLogin();
        initBottomNavigationView();
        setHeaderImage();
        setNavigationViewListener();
        init();

    }
    private void initBottomNavigationView(){
        Log.d(TAG, "initBottomNavigationView: Initiating the bottom navigation view");
        mBottomNavigationViewEx.enableAnimation(false);
    }
    private void setHeaderImage(){
        Log.d(TAG, "setHeaderImage: Setting header image for Navigation drawer");
        Glide.with(this)
                .load(R.drawable.couple)
                .into(mHeaderImage);
    }
    private void init(){
        if (mHomeFragment == null){
            mHomeFragment = new HomeFragment();
            FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
            homeTransaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
            homeTransaction.commit();
            mFragmentTags.add(getString(R.string.tag_fragment_home));
            mFragments.add(new FragmentTag(mHomeFragment, getString(R.string.tag_fragment_home)));


        }
        else {
            mFragmentTags.remove(getString(R.string.tag_fragment_home));
            mFragmentTags.add(getString(R.string.tag_fragment_home));
        }

        setFragmentVisibilities(getString(R.string.tag_fragment_home));

    }
    private void hideBottomNavigation(){
        if (mBottomNavigationViewEx != null){
            mBottomNavigationViewEx.setVisibility(View.GONE);
        }
    }
    private void showBottomNavigation(){
        if (mBottomNavigationViewEx != null){
            mBottomNavigationViewEx.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int backStackCount = mFragmentTags.size();
        if (backStackCount > 1){
            String topFragment = mFragmentTags.get(backStackCount -1);
            String newTopFragment = mFragmentTags.get(backStackCount - 2 );
            setFragmentVisibilities(newTopFragment);
            mFragmentTags.remove(topFragment);

            mExitCount = 0;
        }
        else if (backStackCount == 1){
            String topFrag = mFragmentTags.get(backStackCount - 1);
            if (topFrag.equals(getString(R.string.tag_fragment_home))){
                mHomeFragment.scrollToTop();
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }else {
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }

        }
        if (mExitCount > 2){
            super.onBackPressed();
        }
    }
    private void setNavigationIcon(String tagname){
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = null;
        if (tagname.equals(getString(R.string.tag_fragment_home))){
            menuItem = menu.getItem(HOME_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_saved_connections))){
            menuItem = menu.getItem(CONNECTION_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_messages))){
            menuItem = menu.getItem(MESSAGES_FRAGMENT);
            menuItem.setChecked(true);
        }
    }

    private void setFragmentVisibilities(String tagName){
        if (tagName.equals(getString(R.string.tag_fragment_home))){
            showBottomNavigation();
        }else if (tagName.equals(getString(R.string.tag_fragment_messages))){
            showBottomNavigation();
        }else if (tagName.equals(getString(R.string.tag_fragment_saved_connections))){
            showBottomNavigation();
        }else if (tagName.equals(getString(R.string.tag_fragment_settings))){
            hideBottomNavigation();
        }else if (tagName.equals(getString(R.string.tag_fragment_view_profile))) {
            hideBottomNavigation();
        }else if (tagName.equals(getString(R.string.tag_fragment_chat))) {
            hideBottomNavigation();
        }else if (tagName.equals(getString(R.string.tag_fragment_agreement))) {
            hideBottomNavigation();
        }


        for (int i = 0; i < mFragments.size(); i++){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (tagName.equals(mFragments.get(i).getTag())){
                //show
                transaction.show(mFragments.get(i).getFragment());
            }else {
                //don't show
                transaction.hide(mFragments.get(i).getFragment());
            }
            transaction.commit();

            setNavigationIcon(tagName);

        }

    }

    private void setNavigationViewListener(){
        Log.d(TAG, "setNavigationViewListener: Initializing the navigation view listener");
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void isFirstLogin(){
        Log.d(TAG, "isFirstLogin: launching alert dialog");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);
        if (isFirstLogin){
            Log.d(TAG, "isFirstLogin: Launching alert dialog");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.first_time_user_message);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: Closing of the dialog");
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                            editor.commit();
                            dialogInterface.dismiss();

                        }
                    });
            alertDialogBuilder.setIcon(R.drawable.tabian_dating);
            alertDialogBuilder.setTitle(" ");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void inflateViewProfileFragment(User user) {

        if (mViewProfileFragment != null){
            getSupportFragmentManager().beginTransaction().remove(mViewProfileFragment).commitAllowingStateLoss();

        }
        mViewProfileFragment = new ViewProfileFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_user), user);
        mViewProfileFragment.setArguments(args);

        FragmentTransaction transactionViewProfile = getSupportFragmentManager().beginTransaction();
        transactionViewProfile.add(R.id.main_content_frame, mViewProfileFragment, getString(R.string.tag_fragment_view_profile));
        transactionViewProfile.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_view_profile));
        mFragments.add(new FragmentTag(mViewProfileFragment, getString(R.string.tag_fragment_view_profile)));

        setFragmentVisibilities(getString(R.string.tag_fragment_view_profile));


    }

    @Override
    public void onMessageSelected(Message message) {

        if (mChatFragment != null){
            getSupportFragmentManager().beginTransaction().remove(mChatFragment).commitAllowingStateLoss();
        }

        mChatFragment = new ChatFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_message), message);
        mChatFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mChatFragment, getString(R.string.tag_fragment_chat));
        transaction.commit();
        mFragmentTags.add(getString(R.string.tag_fragment_chat));
        mFragments.add(new FragmentTag(mChatFragment, getString(R.string.tag_fragment_chat)));

        setFragmentVisibilities(getString(R.string.tag_fragment_chat));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                mFragmentTags.clear();
                mFragmentTags = new ArrayList<>();
                init();
                break;
            case R.id.settings:
                Log.d(TAG, "onNavigationItemSelected: Settings Fragment ");
                if (mSettingsFragment == null){
                    mSettingsFragment = new SettingsFragment();
                    FragmentTransaction settingsTransaction = getSupportFragmentManager().beginTransaction();
                    settingsTransaction.add(R.id.main_content_frame, mSettingsFragment, getString(R.string.tag_fragment_settings));
                    settingsTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_settings));
                    mFragments.add(new FragmentTag(mSettingsFragment, getString(R.string.tag_fragment_settings)));
                }else{
                    mFragmentTags.remove(getString(R.string.tag_fragment_settings));
                    mFragmentTags.add(getString(R.string.tag_fragment_settings));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_settings));
                break;

            case R.id.agreement:
                Log.d(TAG, "onNavigationItemSelected: Agreement Fragment ");

                if (mAgreementFragment == null){
                    mAgreementFragment = new AgreementFragment();
                    FragmentTransaction agreementTransaction = getSupportFragmentManager().beginTransaction();
                    agreementTransaction.add(R.id.main_content_frame, mAgreementFragment, getString(R.string.tag_fragment_agreement));
                    agreementTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_agreement));
                    mFragments.add(new FragmentTag(mAgreementFragment, getString(R.string.tag_fragment_agreement)));

                }else{
                    mFragmentTags.remove(getString(R.string.tag_fragment_agreement));
                    mFragmentTags.add(getString(R.string.tag_fragment_agreement));

                }
                item.setChecked(true);
               setFragmentVisibilities(getString(R.string.tag_fragment_agreement));
                break;

            case R.id.bottom_nav_home:
                Log.d(TAG, "onNavigationItemSelected: Home Fragment ");
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
                    homeTransaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
                    homeTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                    mFragments.add(new FragmentTag(mHomeFragment, getString(R.string.tag_fragment_home)));

                }
                else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_home));
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_home));


                break;

            case R.id.bottom_nav_connections:
                Log.d(TAG, "onNavigationItemSelected: Connections Fragment");
                if (mConnectionsFragment == null){
                    mConnectionsFragment = new SavedConnectionsFragment();
                    FragmentTransaction connectionTransaction = getSupportFragmentManager().beginTransaction();
                    connectionTransaction.add(R.id.main_content_frame, mConnectionsFragment, getString(R.string.tag_fragment_saved_connections));
                    connectionTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
                    mFragments.add(new FragmentTag(mConnectionsFragment, getString(R.string.tag_fragment_saved_connections)));

                }
                else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_saved_connections));
                    mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_saved_connections));

                break;

            case R.id.bottom_nav_messages:
                Log.d(TAG, "onNavigationItemSelected: Messages Fragment");
                if (mMessagesFragment == null){
                    mMessagesFragment = new MessagesFragment();
                    FragmentTransaction messageFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    messageFragmentTransaction.add(R.id.main_content_frame, mMessagesFragment, getString(R.string.tag_fragment_messages));
                    messageFragmentTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_messages));
                    mFragments.add(new FragmentTag(mMessagesFragment, getString(R.string.tag_fragment_messages)));

                }else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_messages));
                    mFragmentTags.add(getString(R.string.tag_fragment_messages));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_messages));


                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}
package com.noralynn.coffeecompanion.coffeeshoplist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.noralynn.coffeecompanion.CoffeeShopsIdlingResource;
import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.coffeeshopdetail.CoffeeShopDetailActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
@RunWith(AndroidJUnit4.class)
public class CoffeeShopListActivityTest {
    private static final String TAG = "CoffeeShopListActivityT";
    private static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(99);
    private CoffeeShopsIdlingResource coffeeShopsIdlingResource;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public IntentsTestRule<CoffeeShopListActivity> intentsTestRule = new IntentsTestRule<CoffeeShopListActivity>(CoffeeShopListActivity.class);
//    {
//        @Override
//        protected Intent getActivityIntent() {
//            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//            CoffeeShopListModel fakeModel = new CoffeeShopListModel(true);
//            fakeModel.setCoffeeShops(getFakeCoffeeShops());
//            Intent result = new Intent(targetContext, CoffeeShopListActivity.class);
//            result.putExtra(CoffeeShopListActivity.COFFEE_SHOPS_BUNDLE_KEY, fakeModel);
//
//            return result;
//        }

//        @NonNull
//        private List<CoffeeShop> getFakeCoffeeShops() {
//            List<CoffeeShop> coffeeShops = new ArrayList<>(10);
//            for (int i = 0; i < 10; i++) {
//                coffeeShops.add(CoffeeShop.fake(i));
//            }
//            coffeeShops.add(0, fakeCoffeeShop);
//            return coffeeShops;
//        }
//};

    @Before
    public void setUp(){
        coffeeShopsIdlingResource = intentsTestRule.getActivity().getCoffeeShopsIdlingResources();
        IdlingRegistry.getInstance().register(coffeeShopsIdlingResource);
    }
    @After
    public void tearDown(){
        if (null != coffeeShopsIdlingResource){
            IdlingRegistry.getInstance().unregister(coffeeShopsIdlingResource);
        }
    }


    @Test
    public void testCoffeeShopClick_OpensCoffeeShopDetailsActivity(){
        onView(withId(R.id.coffee_shops_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(CoffeeShopDetailActivity.class.getName()));
    }



    @Test
    public void testShareButton_sendsCorrectShareIntent(){
        String message = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getResources()
                .getString(
                        R.string.coffee_shop_share_message,
                        fakeCoffeeShop.getHumanReadableDistance(),
                        fakeCoffeeShop.getName()
                );
        onView(withId(R.id.action_share))
                .perform(click());




       new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
           @Override
           public void run() {
               intended(allOf(
                       hasType("text/plain"),
                       hasAction(Intent.ACTION_SEND),
                       hasExtra(Intent.EXTRA_TEXT, message)
               ));
           }
       }, 5000);

    }


//    private static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(99);
//    @Rule
//    public IntentsTestRule<CoffeeShopListActivity> intentsTestRule = new IntentsTestRule<CoffeeShopListActivity>(CoffeeShopListActivity.class) {
//        @Override
//        protected Intent getActivityIntent() {
//            Intent intent = super.getActivityIntent();
//
//            CoffeeShopListModel fakeModel = new CoffeeShopListModel(true);
//            fakeModel.setCoffeeShops(getFakeCoffeeShops());
//
//            intent.putExtra(CoffeeShopListActivity.COFFEE_SHOPS_BUNDLE_KEY, fakeModel);
//            return intent;
//        }
//
//        @NonNull
//        private List<CoffeeShop> getFakeCoffeeShops() {
//            List<CoffeeShop> coffeeShops = new ArrayList<>(10);
//            for (int i = 0; i < 10; i++) {
//                coffeeShops.add(CoffeeShop.fake(i));
//            }
//            coffeeShops.add(0, fakeCoffeeShop);
//            return coffeeShops;
//        }
    };

//    @Nullable
//    private CoffeeShopsIdlingResource coffeeShopsIdlingResource;
//
//    @Before
//    public void setup() {
//        coffeeShopsIdlingResource = intentsTestRule.getActivity().getCoffeeShopsIdlingResource();
//        Espresso.registerIdlingResources(coffeeShopsIdlingResource);
//    }
//
//    @After
//    public void teardown() {
//        if (null != coffeeShopsIdlingResource) {
//            Espresso.unregisterIdlingResources(coffeeShopsIdlingResource);
//        }
//    }
//
//    @Test
//    public void testShareButton_sendsCorrectShareIntent() {
//        String message = InstrumentationRegistry
//                .getTargetContext()
//                .getResources()
//                .getString(
//                        R.string.coffee_shop_share_message,
//                        fakeCoffeeShop.getHumanReadableDistance(),
//                        fakeCoffeeShop.getName()
//                );
//
//        onView(withId(R.id.action_share)).perform(click());
//
//        intended(allOf(
//                hasType("text/plain"),
//                hasAction(Intent.ACTION_SEND),
//                hasExtra(Intent.EXTRA_TEXT, message))
//        );
//    }
//
//    @Test
//    public void testCoffeeShopClick_opensCoffeeShopDetailActivity() {
//        onView(withId(R.id.coffee_shops_recycler))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//        intended(hasComponent(CoffeeShopDetailActivity.class.getName()));
//    }


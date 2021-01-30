package com.noralynn.coffeecompanion.beveragelist;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.runner.AndroidJUnitRunner;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.beveragedetail.BeverageDetailActivity;
import com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShopListActivity;
import com.noralynn.coffeecompanion.common.Beverage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


public class BeverageListActivityTest {

    @Rule
    public IntentsTestRule<BeverageListActivity> intentsTestRule = new IntentsTestRule<>(BeverageListActivity.class);

    @Before
    public void stubCoffeeShopListActivityIntent() {

       Intent intent = new Intent();
       ActivityResult activityResult = new ActivityResult(Activity.RESULT_OK, intent);
       intending(hasComponent(CoffeeShopListActivity.class.getName()))
               .respondWith(activityResult);




    }

    @Test
    public void testMapFabClick_shouldOpenCoffeeShopListActivity() {
       onView(withId(R.id.map_fab))
               .perform(click());
       intended(hasComponent(CoffeeShopListActivity.class.getName()));


    }


    @Test
    public void testBeverageClick_shouldOpenBeverageListActivity() {
        onView(withId(R.id.beverages_recycler))
                .perform(RecyclerViewActions.scrollToPosition(7));
        onView(withText("Latte macchiato"))
                .check(matches(isDisplayed()));
        onView(withId(R.id.beverages_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        intended(hasComponent(BeverageDetailActivity.class.getName()));
        
        pressBack();

        onView(withText("Latte macchiato")).check(matches(isDisplayed()));

    }


    @Test
    public void testMapFloatingActionButton_shouldBeDisplayed() {
        onView(withId(R.id.map_fab)).check(matches(isDisplayed()));
    }


    @Test
    public void testToolbarImageView_shouldHaveContentDescription() {
        onView(withId(R.id.toolbar_image)).check(matches(withContentDescription(R.string.content_fox_mug)));
    }


    /**
     * The below test is trying to find a single AppCompatTextView, but since there are
     * multiple AppCompatTextViews in our view hierarchy, our test fails with an
     * AmbiguousViewMatcherException. We'll have to have to use a different ViewMatcher :-)
     */
    @Test
    public void testToolbarTitleText_shouldHaveCorrectText_fails() {

        onView(isAssignableFrom(AppCompatTextView.class))
                .check(matches(withText(R.string.app_name)));
    }


    /**
     * There we go! The AppCompatTextView we're looking for is a child of our Toolbar.
     * By combining matchers to specify this, Espresso is able to find our view and
     * perform our test.
     */
    @Test
    public void testToolbarTitleText_shouldHaveCorrectText() {
        onView(allOf(withParent(isAssignableFrom(Toolbar.class)),
                isAssignableFrom(AppCompatTextView.class))).check(matches(withText(R.string.app_name)));
    }


    @Test
    public void testBeveragesRecyclerViewItem_shouldHaveBeverageData() {
        Beverage beverage = new Beverage(
                "Flat white",
                "A flat white is an espresso based coffee " +
                        "beverage that is prepared by pouring steamed milk " +
                        "over a shot of espresso. This are typically smaller than " +
                        "lattes of cappuccinos and have a higher proportion of coffee to milk " +
                        "which allows the espresso flavor to really shine through.",
                "R.drawable.ic_flat_white"

        );

        onView(withId(R.id.beverages_recycler))
                .check(matches(hasBeverageDataForPosition(3, beverage)));

    }

    private Matcher<View> hasBeverageDataForPosition(final int position, final Beverage beverage) {
         return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
             @Override
             public void describeTo(Description description) {
                description.appendText("Item has beverage data at position " + position + " : ");
             }

             @Override
             protected boolean matchesSafely(RecyclerView recyclerView) {
                 if (recyclerView == null){
                     return false;
                 }

                 RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                 if (viewHolder == null){
                     return false;
                 }
                 return withChild(withText(beverage.getName())).matches(viewHolder.itemView);
             }
         };



    }


}
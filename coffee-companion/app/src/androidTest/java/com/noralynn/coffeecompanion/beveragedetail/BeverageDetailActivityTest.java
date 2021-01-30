package com.noralynn.coffeecompanion.beveragedetail;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.beveragelist.BeverageListActivity;
import com.noralynn.coffeecompanion.common.Beverage;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.LayoutAssertions.noEllipsizedText;
import static androidx.test.espresso.assertion.LayoutAssertions.noOverlaps;
import static com.noralynn.coffeecompanion.beveragedetail.BeverageDetailActivity.BEVERAGE_MODEL_BUNDLE_KEY;

public class BeverageDetailActivityTest {

    @NonNull
    private static final Beverage beverage = Beverage.fake();

    @Rule
    public ActivityTestRule<BeverageDetailActivity> mRule = new ActivityTestRule<BeverageDetailActivity>(BeverageDetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent results = new Intent(targetContext, BeverageDetailActivity.class);
            results.putExtra(BEVERAGE_MODEL_BUNDLE_KEY, beverage);

                return super.getActivityIntent();
        }
    };



    @Test
    public void testTitle_shouldBePositionedAboveBeverageImageView() {
        onView(withId(R.id.text_title)).check(isAbove(withId(R.id.image_beverage)));

    }


    @Test
    public void testDescription_shouldBeSetToCorrectBeverageDescriptionText() {
      onView(withId(R.id.text_description)).check(matches(withText(beverage.getDescription())));
    }


    @Test
    public void testImage_shouldBeDisplayed() {
        onView(withId(R.id.image_beverage)).check(matches(isDisplayed()));


    }


    @Test
    public void testDescription_shouldNotContainEllipsizedText() {
        onView(withId(R.id.text_description)).check(noEllipsizedText());

    }


    @Test
    public void testBeverageDetailActivity_shouldNotHaveAnyOverlaps() {
        onView(withId(R.id.activity_beverage)).check(noOverlaps());

    }


}
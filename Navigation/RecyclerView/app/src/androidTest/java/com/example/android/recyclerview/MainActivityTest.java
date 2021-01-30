package com.example.android.recyclerview;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private static final int ITEM_BELOW_THE_FOLD = 10;
    
    @Rule
    public  ActivityTestRule<MainActivity> mTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void scrollToPosition_checkItemClicked(){

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions
                .actionOnItemAtPosition(ITEM_BELOW_THE_FOLD, click()));
        String itemText = mTestRule.getActivity().getApplicationContext().getResources().getString(R.string.word_10);
        onView(withText(itemText)).check(matches(isDisplayed()));


    }

    @Test
    public void itemsWithText(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions
        .scrollTo(hasDescendant(withText("Word 8"))));
    }



}

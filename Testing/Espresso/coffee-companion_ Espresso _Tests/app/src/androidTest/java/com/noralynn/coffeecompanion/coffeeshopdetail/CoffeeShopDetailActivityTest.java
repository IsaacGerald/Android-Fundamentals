package com.noralynn.coffeecompanion.coffeeshopdetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static androidx.test.espresso.Espresso.onView;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnitRunner;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;

import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShop;
import static androidx.test.espresso.action.ViewActions.click;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CoffeeShopDetailActivityTest {

    public static  final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(99);

    @Rule
     public IntentsTestRule<CoffeeShopDetailActivity>
            mTestRule = new IntentsTestRule<CoffeeShopDetailActivity>(CoffeeShopDetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            CoffeeShopDetailModel fakeModel = new CoffeeShopDetailModel();
            fakeModel.setCoffeeShop(fakeCoffeeShop);
            Intent result = new Intent(targetContext, CoffeeShopDetailActivity.class);
            result.putExtra(CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY, fakeModel.getCoffeeShop());
            return result;
        }

        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            onWebView(withId(R.id.button_website)).forceJavascriptEnabled();
        }
    };


    @Test
    public void testMapButton_sendsMapViewLocationIntent(){
        Uri uri = Uri.parse("geo:0,0?q=" + fakeCoffeeShop.getAddress());
        onView(withId(R.id.button_map)).perform(click());

        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(uri)

        ));
    }

    @Test
    public void testClickWebsiteButton_OpensWebViewCoffeeShopUrl(){
        onView(withId(R.id.button_website))
                .perform(click());

/*-----------------checking if toast is displayed correctly on a button click-------------*/
        onWebView()
                .withElement(findElement(Locator.ID, "button"))
                .perform(webClick());
    //alters the state of what is shown on a webView
        onWebView().reset();

        onView(withText(R.string.app_name))
                .inRoot(withDecorView(not(is(mTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    }

































//    public static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(0);
//
//    @Rule
//    public IntentsTestRule<CoffeeShopDetailActivity> intentsTestRule = new IntentsTestRule<CoffeeShopDetailActivity>(CoffeeShopDetailActivity.class) {
//        @Override
//        protected Intent getActivityIntent() {
//            Intent intent = super.getActivityIntent();
//
//            CoffeeShopDetailModel fakeModel = new CoffeeShopDetailModel();
//            fakeModel.setCoffeeShop(fakeCoffeeShop);
//
//            intent.putExtra(CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY, fakeCoffeeShop);
//            return intent;
//        }
//    };
//
//    @Test
//    public void testMapButton_SendsMapViewLocationIntent() {
//        Uri desiredUri = Uri.parse("geo:0,0?q=" + fakeCoffeeShop.getAddress());
//
//        onView(withId(R.id.button_map)).perform(click());
//
//        intended(allOf(
//                hasAction(Intent.ACTION_VIEW),
//                hasData(desiredUri))
//        );
//    }

}
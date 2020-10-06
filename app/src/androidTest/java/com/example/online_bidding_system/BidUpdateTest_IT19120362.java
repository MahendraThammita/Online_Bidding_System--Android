package com.example.online_bidding_system;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BidUpdateTest_IT19120362 {

    @Rule
    public ActivityTestRule<HomePage> mActivityTestRule = new ActivityTestRule<>(HomePage.class);

    @Test
    public void bidUpdateTest_IT19120362() {
        DataInteraction constraintLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.HomeCardsList),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                3)))
                .atPosition(0);
        constraintLayout.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.displatAuctToBidButton), withText("Continue to Bid"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.placeBid_newBidValue),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("45000"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.placeBid_ConfirmBidBtn), withText("Confirm Bid"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout6),
                                        childAtPosition(
                                                withId(R.id.scrollView2),
                                                0)),
                                7)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("My Bids"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablay1),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.mybidmaxBidVal), withText("7500 Rs"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("7500 Rs")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

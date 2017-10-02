package com.hema.recipeapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by hema on 10/2/2017.
 */


@RunWith(AndroidJUnit4.class)
public class DetailActivityEspressotest {


    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testPlayerView() {
        onView(ViewMatchers.withId(R.id.my_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(ViewMatchers.withId(R.id.my_recycler_view2)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.playerView)).check(matches(isDisplayed()));
    }


}

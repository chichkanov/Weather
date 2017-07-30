package dvinc.yamblzhomeproject.ui;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.ui.base.MvpMainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MvpMainActivityTest {

    @Rule
    public ActivityTestRule<MvpMainActivity> ActivityTestRule = new ActivityTestRule<>(MvpMainActivity.class);

    @Test
    public void checkFragmentSwitchCorrect() {
        openDrawerAndSelectItem(R.string.select_city_head);
        onView(withId(R.id.et_select_city)).check(matches(isDisplayed()));

        openDrawerAndSelectItem(R.string.nav_head_settings);
        onView(withId(R.id.settingsUpdateTimeSpinner)).check(matches(isDisplayed()));

        openDrawerAndSelectItem(R.string.nav_head_weather);
        onView(withId(R.id.lastUpdateWeatherTextView)).check(matches(isDisplayed()));
    }

    private void openDrawerAndSelectItem(int itemId) {
        onView(allOf(withContentDescription(R.string.navigation_drawer_open),
                withParent(withId(R.id.toolbar)),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.design_menu_item_text),
                withText(itemId),
                isDisplayed()))
                .perform(click());
    }
}

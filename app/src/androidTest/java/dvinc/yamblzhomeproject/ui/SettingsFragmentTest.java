package dvinc.yamblzhomeproject.ui;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.ui.base.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        activityRule.getActivity().runOnUiThread(this::startMyFragment);
    }

    @Test
    public void checkForCorrectAutoUpdateTimeChange() {
        onView(allOf(withId(R.id.settingsUpdateTimeSpinner),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.text1),
                withText("45"), isDisplayed()))
                .perform(click())
                .check(matches(withText("45")));
    }

    @Test
    public void checkForCheckButtonCorrectWork() {
        onView(allOf(withId(R.id.updateCheckbox),
                isDisplayed()))
                .perform(click());
    }

    private SettingsFragments startMyFragment() {
        FragmentActivity activity = activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        SettingsFragments myFragment = new SettingsFragments();
        transaction.replace(R.id.fragmentContainer, myFragment, "frag");
        transaction.commit();
        return myFragment;
    }

}

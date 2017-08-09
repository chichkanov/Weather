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
import dvinc.yamblzhomeproject.ui.weather.WeatherFragment;

@RunWith(AndroidJUnit4.class)
public class WeatherFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        activityRule.getActivity().runOnUiThread(this::startMyFragment);
    }

    @Test
    public void checkElementsCorrectlyShowing() {
        //onView(withId(R.id.temperatureTextView)).check(matches(isDisplayed()));
        //onView(withId(R.id.pressureTextView)).check(matches(isDisplayed()));
    }

    private WeatherFragment startMyFragment() {
        FragmentActivity activity = activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        WeatherFragment myFragment = new WeatherFragment();
        transaction.replace(R.id.fragmentContainer, myFragment, "frag");
        transaction.commit();
        return myFragment;
    }
}

package dvinc.yamblzhomeproject.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SelectCityActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);


    /*@Before
    public void setUp() {
        activityRule.getActivity().runOnUiThread(this::startMyFragment);
    }
*/
    @Test
    public void checkAllElementsCorrectlyShowing() {
        onView(withId(R.id.et_select_city)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_select_city)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEditTextWorkingCorrectly() {
        onView(withId(R.id.et_select_city))
                .perform(typeText("Moscow"))
                .check(matches(withText("Moscow")));
    }

/*    private SelectCityActivity startMyFragment() {
        FragmentActivity activity = activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        SelectCityActivity myFragment = SelectCityActivity.newInstance();
        transaction.replace(R.id.fragmentContainer, myFragment, "frag");
        transaction.commit();
        return myFragment;
    }*/
}

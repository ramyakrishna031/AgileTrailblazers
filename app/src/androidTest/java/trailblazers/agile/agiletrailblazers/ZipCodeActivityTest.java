package trailblazers.agile.agiletrailblazers;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


public class ZipCodeActivityTest {

    @Rule
    public ActivityTestRule<ZipCodeActivity> mActivityTestRule = new ActivityTestRule<ZipCodeActivity>(ZipCodeActivity.class);
    private ZipCodeActivity mZipCodeActivity = null;
    private EditText mEdtZipCode;
    private Button mBtnSubmit;

    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(WeatherDetailsActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mZipCodeActivity = mActivityTestRule.getActivity();
        mEdtZipCode = (EditText) mZipCodeActivity.findViewById(R.id.editText_zipCode);
        mBtnSubmit = (Button) mZipCodeActivity.findViewById(R.id.button_submit);
    }

    @Test
    public void testViewsCreated() {

        assertNotNull(mZipCodeActivity);
        assertNotNull(mEdtZipCode);
        assertNotNull(mBtnSubmit);

    }

    @Test
    public void testLaunchWeatherDetailsActivity() {

        onView(withId(R.id.editText_zipCode)).perform(typeText("94040"), closeSoftKeyboard());
        onView(withId(R.id.button_submit)).perform(click());
        Activity weatherDeatilsActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor,5000);
        assertNotNull(weatherDeatilsActivity);

    }

    @After
    public void tearDown() throws Exception {
    }

}
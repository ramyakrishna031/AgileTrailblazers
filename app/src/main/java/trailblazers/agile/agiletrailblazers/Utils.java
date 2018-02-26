package trailblazers.agile.agiletrailblazers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String US_ZIP_CODE_REGEX = "^[0-9]{5}(?:-[0-9]{4})?$";
    private static final Pattern ZIP_PATTERN;

    static {
        ZIP_PATTERN = Pattern.compile(US_ZIP_CODE_REGEX);
    }

    /**
     * Method used to validate the US zip code.
     * @param pZipCode represents the US zip code value.
     * @return boolean value
     */
    public static boolean validateZipCode(String pZipCode) {
        return pZipCode != null && ZIP_PATTERN.matcher(pZipCode).matches();
    }

    /**
     * Method to check internet connectivity.
     * @return boolean value - true if network available
     */

    public static boolean isNetworkConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Method to hide the keyboard
     * @param activity
     */
    public static void hideSoftKeyboard(final Activity activity) {
        if (activity == null) {
            return;
        }
        View currentFocus = activity.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }


    public static String getTimeFromUnixTimeStamp(long unixSeconds){
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);
    }
}

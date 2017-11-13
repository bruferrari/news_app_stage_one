package com.bferrari.newsappstageone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bferrari on 12/11/17.
 */

public class CommonUtils {

    private CommonUtils() { }

    private static final String LOG_TAG = CommonUtils.class.getSimpleName();

    public static final String BASE_URL = "http://content.guardianapis.com/";
    public static final String SEARCH = "search?q=";
    public static final String FROM_DATE = "&from-date=";
    public static final String API_KEY = "&api-key=test";

    public static String nowInString() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static String parseDate(String dateInString) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = df.parse(dateInString);
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return df.format(parsedDate);
    }
}

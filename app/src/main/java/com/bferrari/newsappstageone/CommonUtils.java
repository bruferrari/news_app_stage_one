package com.bferrari.newsappstageone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bferrari on 12/11/17.
 */

public class CommonUtils {

    private CommonUtils() { }

    public static final String BASE_URL = "http://content.guardianapis.com/";
    public static final String SEARCH = "search?q=";
    public static final String SEARCH_FROM_DATE = "search?q&from-date=";
    public static final String API_KEY = "&api-key=test";

    public static String nowInString() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now);
    }
}

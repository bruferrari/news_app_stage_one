package com.bferrari.newsappstageone;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.bferrari.newsappstageone.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bferrari on 12/11/17.
 */

public class ArticlesTaskLoader extends AsyncTaskLoader<List<Article>> {

    private List<Article> mArticles = new ArrayList<>();

    private static final String LOG_TAG = ArticlesTaskLoader.class.getSimpleName();
    private static final String RESPONSE_FIELD = "response";
    private static final String RESULTS_FIELD = "results";

    private static final String TITLE_PROPERTY = "webTitle";
    private static final String CATEGORY_PROPERTY = "sectionName";
    private static final String DATE_PROPERTY = "webPublicationDate";
    private static final String URL_PROPERTY = "webUrl";
    private static final String CONTRIBUTOR_PROPERTY = "contributor";

    private String mCountry;
    private String mCategory;
    private SharedPreferences mSharedPrefs;
    private Context ctx;

    public ArticlesTaskLoader(Context context, List<Article> articles) {
        super(context);
        ctx = context;
        mArticles = articles;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        HttpRequestHandler httpHandler = new HttpRequestHandler();

        mSharedPrefs = ctx.getSharedPreferences(ctx.getString(R.string.preferences), Context.MODE_PRIVATE);

        mCountry = mSharedPrefs.getString(String.valueOf(PreferencesKeys.COUNTRY), PreferencesKeys.COUNTRY.getValue());
        mCategory = mSharedPrefs.getString(String.valueOf(PreferencesKeys.CATEGORY), PreferencesKeys.CATEGORY.getValue());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, 8, 15, 12, 34, 56);

        String requestUrl = CommonUtils.BASE_URL + CommonUtils.SEARCH
                + CommonUtils.SECTION + mCategory
                + CommonUtils.QUERY + mCountry
                + CommonUtils.FROM_DATE + CommonUtils.getFormattedDate(calendar.getTime())
                + CommonUtils.TO_DATE + CommonUtils.nowInString()
                + CommonUtils.API_KEY;
        Log.d(LOG_TAG, "generating request: " + requestUrl);

        String json = httpHandler.callService(requestUrl);

        if (!json.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject responseObject = jsonObject.getJSONObject(RESPONSE_FIELD);
                JSONArray resultsObject = responseObject.getJSONArray(RESULTS_FIELD);

                mArticles.clear();

                for (int i = 0; i < resultsObject.length(); i++) {
                    Article article = new Article();
                    article.setTitle(resultsObject.getJSONObject(i).getString(TITLE_PROPERTY));
                    article.setCategory(resultsObject.getJSONObject(i).getString(CATEGORY_PROPERTY));

                    String dateInString = resultsObject.getJSONObject(i).getString(DATE_PROPERTY);

                    article.setDate(CommonUtils.parseDate(dateInString));
                    article.setUrl(resultsObject.getJSONObject(i).getString(URL_PROPERTY));
                    String contributor = "";
                    try {
                        resultsObject.getJSONObject(i).getString(CONTRIBUTOR_PROPERTY);
                    } catch (JSONException e) {
                        Log.d(LOG_TAG, "no author found");
                    }

                    article.setAuthor(contributor);

                    mArticles.add(article);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return mArticles;
    }
}

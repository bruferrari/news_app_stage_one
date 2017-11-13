package com.bferrari.newsappstageone;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.bferrari.newsappstageone.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    public ArticlesTaskLoader(Context context, List<Article> articles) {
        super(context);
        mArticles = articles;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        HttpRequestHandler httpHandler = new HttpRequestHandler();
        String requestUrl = CommonUtils.BASE_URL
                + CommonUtils.SEARCH_FROM_DATE
                + CommonUtils.nowInString()
                + CommonUtils.API_KEY;
        Log.d(LOG_TAG, "generating request: " + requestUrl);

        String json = httpHandler.callService(requestUrl);

        if (!json.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject responseObject = jsonObject.getJSONObject(RESPONSE_FIELD);
                JSONArray resultsObject = responseObject.getJSONArray(RESULTS_FIELD);

                for (int i = 0; i < resultsObject.length(); i++) {
                    Article article = new Article();
                    article.setTitle(resultsObject.getJSONObject(i).getString(TITLE_PROPERTY));
                    article.setCategory(resultsObject.getJSONObject(i).getString(CATEGORY_PROPERTY));
                    article.setDate(resultsObject.getJSONObject(i).getString(DATE_PROPERTY));
                    article.setUrl(resultsObject.getJSONObject(i).getString(URL_PROPERTY));

                    mArticles.add(article);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return mArticles;
    }
}

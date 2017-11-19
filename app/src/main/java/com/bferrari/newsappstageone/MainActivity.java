package com.bferrari.newsappstageone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bferrari.newsappstageone.model.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mGeneralMessage;
    private SwipeRefreshLayout mRefreshLayout;

    private ArticleAdapter mAdapter = new ArticleAdapter();
    private List<Article> mData = new ArrayList<>();

    private static final int LOADER_ID = 0;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Not implemented yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bindUI();
        setupRecyclerView();
        setupRefreshLayout();

        if (CommonUtils.isNetworkAvailable(this)) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            mGeneralMessage.setText(R.string.err_internet_connection);
            displayGeneralMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }

        return true;
    }

    private void bindUI() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mGeneralMessage = findViewById(R.id.general_msg);
        mRefreshLayout = findViewById(R.id.swipe_refresh);
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        super.onResume();
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        return new ArticlesTaskLoader(this, mData);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Article>> loader, List<Article> data) {
        mProgressBar.setVisibility(View.GONE);

        mData = data;
        mAdapter.setData(mData);

        if (mData.isEmpty()) {
            mGeneralMessage.setText(R.string.err_news_not_available);
            displayGeneralMessage();
        } else {
            hideGeneralMessage();
        }

        Log.d(LOG_TAG, "Load finished!");
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Article>> loader) {
        mRecyclerView.setAdapter(mAdapter);
    }

    private void displayGeneralMessage() {
        mGeneralMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void hideGeneralMessage() {
        mGeneralMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}

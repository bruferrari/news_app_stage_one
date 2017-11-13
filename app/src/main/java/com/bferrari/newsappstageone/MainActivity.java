package com.bferrari.newsappstageone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bferrari.newsappstageone.model.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private ArticleAdapter mAdapter = new ArticleAdapter();
    private List<Article> mData = new ArrayList<>();

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

        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void bindUI() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
            Toast.makeText(this, "Not found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Article>> loader) {
        mRecyclerView.setAdapter(mAdapter);
    }
}

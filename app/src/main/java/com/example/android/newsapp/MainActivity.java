package com.example.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?q=debates&api-key=test&show-tags=contributor";
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private ListView mNewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mNewsListView = (ListView) findViewById(R.id.list);
        mNewsListView.setAdapter(mAdapter);
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mLoadingIndicator = findViewById(R.id.loading_indicator);
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
                mAdapter.clear();
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("api-key", "test")
                .appendQueryParameter("show-tags", "contributor");
        String url = builder.build().toString();
        return new NewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        if (news != null && !news.isEmpty()) {
            Log.println(Log.INFO, LOG_TAG, String.valueOf("MAIN: " + news));
            mAdapter.addAll(news);
        } else mEmptyStateTextView.setText(R.string.no_news);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
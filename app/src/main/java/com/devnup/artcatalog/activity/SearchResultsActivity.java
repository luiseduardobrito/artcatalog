package com.devnup.artcatalog.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EActivity
public class SearchResultsActivity extends Activity {

    @AfterViews
    void init() {
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
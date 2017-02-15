package org.eightfoldpath.newsreader;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private final String TAG = MainActivity.class.getSimpleName();
    private org.eightfoldpath.newsreader.ArticleArrayAdapter adapter = null;
    private TextView emptyStateTextView = null;
    private final int LOADER_KEY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView articleListView = (ListView) findViewById(R.id.list);
        emptyStateTextView = (TextView) findViewById(R.id.empty);
        articleListView.setEmptyView(emptyStateTextView);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.VISIBLE);

        if (checkNetworkConnectivity()) {
            adapter = new ArticleArrayAdapter(this, 0, new ArrayList<Article>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            articleListView.setAdapter(adapter);

            getSupportLoaderManager().restartLoader(LOADER_KEY, null, this);
        } else {
            emptyStateTextView.setText(R.string.no_network);
        }
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");

        ArticleLoader loader = new ArticleLoader(MainActivity.this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {

        Log.d(TAG, "onLoadFinished");

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.INVISIBLE);

        adapter.clear();
        adapter.setArticles(data);

        if (data.isEmpty()) {
            emptyStateTextView.setText(R.string.no_data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        Log.d(TAG, "onLoaderReset");
        adapter.setArticles(new ArrayList<Article>());
    }

    private boolean checkNetworkConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}

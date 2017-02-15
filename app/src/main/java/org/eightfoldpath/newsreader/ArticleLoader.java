package org.eightfoldpath.newsreader;

import org.eightfoldpath.utils.HttpUtils;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rick on 2/15/17.
 */

public class ArticleLoader extends AsyncTaskLoader<ArrayList<Article>> {

    private final String API_URL = "http://content.guardianapis.com/search?api-key=test";
    private static final String TAG = ArticleLoader.class.getSimpleName();
    private String query = null;

    public ArticleLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        Log.d(TAG, "loadInBackground - query:" + query);

        return QueryUtils.extractArticles(API_URL);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private static class QueryUtils {

        private QueryUtils() {
        }

        public static ArrayList<Article> extractArticles(String url) {

            ArrayList<Article> articles = new ArrayList<Article>();

            try {
                Log.d(TAG, "extractArticles");

                String httpResponse = HttpUtils.makeHttpRequest(HttpUtils.createUrl(url));
                JSONObject response = new JSONObject(httpResponse);

                JSONObject jsonResponse = response.getJSONObject("response");
                JSONArray jsonArticles = jsonResponse.getJSONArray("results");
                for (int i = 0; i < jsonArticles.length(); i++) {
                    JSONObject jsonArticle = jsonArticles.getJSONObject(i);
                    String title = jsonArticle.getString("webTitle");
                    String section = jsonArticle.getString("sectionName");
                    String articleUrl = jsonArticle.getString("webUrl");
                    articles.add(new Article(title, articleUrl, section));
                }

            } catch (Exception e) {
                Log.e("QueryUtils", "Problem parsing the article JSON results", e);
            }

            // Return the list of articles
            return articles;
        }

    }
}

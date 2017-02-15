package org.eightfoldpath.newsreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.eightfoldpath.newsreader.Article;
import org.eightfoldpath.newsreader.R;

import java.util.ArrayList;

/**
 * Created by rick on 2/14/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Article> articles;

    public ArticleArrayAdapter(Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        final Article currentArticle = articles.get(position);

        // Set a click listener to open a detail page in a browser
        rowView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.getInfoUrl())));

            }
        });

        TextView authorView = (TextView) rowView.findViewById(R.id.article_section);
        authorView.setText(articles.get(position).getSection());
        TextView nameView = (TextView) rowView.findViewById(R.id.article_title);
        nameView.setText(articles.get(position).getTitle());

        return rowView;
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    public void setArticles(ArrayList<Article> data) {
        articles.addAll(data);
        notifyDataSetChanged();
    }

}

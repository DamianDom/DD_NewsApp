package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Activity context, ArrayList<News> newsList) {
        super(context, 0, newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);
        TextView newsNameView = (TextView) listItemView.findViewById(R.id.name_text_view);
        newsNameView.setText(currentNews.getName());
        TextView newsSectionView = (TextView) listItemView.findViewById(R.id.section_text_view);
        newsSectionView.setText(currentNews.getSection());
        TextView newsAuthorView = (TextView) listItemView.findViewById(R.id.author_text_view);
        newsAuthorView.setText(currentNews.getAuthor());
        TextView newsDateView = (TextView) listItemView.findViewById(R.id.date_text_view);
        newsDateView.setText(currentNews.getDate());
        return listItemView;
    }
}
package com.example.android.newsapp;
public class News {
    private String mName;
    private String mSection;
    private String mDate;
    private String mAuthor;
    private String mUrl;
    public News(String name, String section, String date, String author, String url) {
        mName = name;
        mSection = section;
        mDate = date;
        mAuthor = author;
        mUrl = url;
    }
    public String getName() {
        return mName;
    }
    public String getSection() {
        return mSection;
    }
    public String getDate() {
        mDate = mDate.substring(0, 10);
        return mDate;
    }
    public String getAuthor() {
        return mAuthor;
    }
    public String getUrl() {
        return mUrl;
    }
}
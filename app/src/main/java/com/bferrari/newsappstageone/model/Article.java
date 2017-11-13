package com.bferrari.newsappstageone.model;

/**
 * Created by bferrari on 12/11/17.
 */

public class Article {

    private String title;
    private String category;
    private String date;
    private String url;

    public Article() { }

    public Article(String title, String category, String date) {
        this.title = title;
        this.category = category;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

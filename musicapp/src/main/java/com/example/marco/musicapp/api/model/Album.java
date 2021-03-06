package com.example.marco.musicapp.api.model;

import org.json.JSONArray;

/**
 * Created by marco on 14/11/17.
 */

public class Album {
    String title,cover;
    int sale_price,purchase_price,id;
    String release;
    JSONArray tracklist;

    public Album(String title, String cover, int sale_price, int purchase_price, int id, String release, JSONArray tracklist) {
        this.title = title;
        this.cover = cover;
        this.sale_price = sale_price;
        this.purchase_price = purchase_price;
        this.id = id;
        this.release = release;
        this.tracklist = tracklist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(int purchase_price) {
        this.purchase_price = purchase_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public JSONArray getTracklist() {
        return tracklist;
    }

    public void setTracklist(JSONArray tracklist) {
        this.tracklist = tracklist;
    }
}

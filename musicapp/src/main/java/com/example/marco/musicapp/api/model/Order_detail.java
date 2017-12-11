package com.example.marco.musicapp.api.model;

/**
 * Created by marco on 10/12/17.
 */

import org.json.JSONArray;

public class Order_detail {
    int quantity,order_id,id,album_id;

    public Order_detail(int quantity, int order_id, int id, int album_id) {
        this.quantity = quantity;
        this.order_id = order_id;
        this.id = id;
        this.album_id = album_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }
}

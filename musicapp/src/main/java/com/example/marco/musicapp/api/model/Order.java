package com.example.marco.musicapp.api.model;

/**
 * Created by marco on 10/12/17.
 */

import org.json.JSONArray;

public class Order {
    String total,payment;
    int id;
    String date;
    JSONArray jsonArray;

    public Order(String total, String payment, int id, JSONArray jsonArray, String date) {
        this.total = total;
        this.payment = payment;
        this.id = id;
        this.date = date;
        this.jsonArray = jsonArray;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}

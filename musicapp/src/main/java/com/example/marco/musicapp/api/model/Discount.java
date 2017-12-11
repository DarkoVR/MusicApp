package com.example.marco.musicapp.api.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marco on 10/12/17.
 */

public class Discount {
    Boolean used;
    int percentage_value,id;

    public Discount(Boolean used, int percentage_value, int id) {
        this.used = used;
        this.percentage_value = percentage_value;
        this.id = id;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public int getPercentage_value() {
        return percentage_value;
    }

    public void setPercentage_value(int percentage_value) {
        this.percentage_value = percentage_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

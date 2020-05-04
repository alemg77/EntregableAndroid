package com.example.entregableandroid.ApiML;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ElementoLista implements Serializable {
    private String id;
    private String title;
    private String thumbnail;
    private String price;

    public ElementoLista(String id, String title, String thumbnail, String price) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public ElementoLista(JSONObject jsonObject) throws JSONException {
        this.title = jsonObject.getString("title");
        this.thumbnail = jsonObject.getString("thumbnail");
        this.price = jsonObject.getString("price");
        this.id = jsonObject.getString("id");
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPrice() {
        return price;
    }
}

package com.example.entregableandroid.ApiML;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


@Entity(tableName = ElementoLista.TABLE_NAME)
public class ElementoLista implements Serializable {

    public final static String TABLE_NAME = "ElementoLista";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "price")
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

package com.example.entregableandroid.Modelo.ApiML;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = ItemListaAPI.TABLE_NAME)
public class ItemListaAPI implements Serializable {

    public final static String TABLE_NAME = "ItemLista";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COLUMN_ID)
    private String id;

    @ColumnInfo(name = "site_id")
    private String site_id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "permalink;")
    private String permalink;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    public ItemListaAPI(String id, String site_id, String title, String price, String permalink, String thumbnail) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.price = price;
        this.permalink = permalink;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
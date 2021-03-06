package com.example.entregableandroid.Modelo.ApiML;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = Item.TABLE_NAME)
public class Item implements Serializable {

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

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "vendedor")
    private String vendedor;

    @ColumnInfo(name = "imagenFirebase")
    private String imagenFirebase;

    public Item() {
    }

    public String getImagenFirebase() {
        return imagenFirebase;
    }

    public void setImagenFirebase(String imagenFirebase) {
        this.imagenFirebase = imagenFirebase;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
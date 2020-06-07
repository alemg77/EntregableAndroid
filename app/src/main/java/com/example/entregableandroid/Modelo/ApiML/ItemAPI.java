package com.example.entregableandroid.Modelo.ApiML;

import java.io.Serializable;
import java.util.List;

public class ItemAPI implements Serializable {
    private String id;
    private String site_id;
    private String title;
    private String seller_id;
    private String category_id;
    private String price;
    private String permalink;
    private String thumbnail;
    private String secure_thumbnail;
    private String descripcion;
    private List<Imagen> pictures;
    private ItemLocationAPI location;

    public ItemAPI() {
    }

    public ItemAPI(String id, String site_id, String title, String seller_id, String category_id, String price, String permalink, String thumbnail, String secure_thumbnail, String descripcion, List<Imagen> pictures, ItemLocationAPI location) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.seller_id = seller_id;
        this.category_id = category_id;
        this.price = price;
        this.permalink = permalink;
        this.thumbnail = thumbnail;
        this.secure_thumbnail = secure_thumbnail;
        this.descripcion = descripcion;
        this.pictures = pictures;
        this.location = location;
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

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getSecure_thumbnail() {
        return secure_thumbnail;
    }

    public void setSecure_thumbnail(String secure_thumbnail) {
        this.secure_thumbnail = secure_thumbnail;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Imagen> getPictures() {
        return pictures;
    }

    public void setPictures(List<Imagen> pictures) {
        this.pictures = pictures;
    }

    public ItemLocationAPI getLocation() {
        return location;
    }

    public void setLocation(ItemLocationAPI location) {
        this.location = location;
    }
}
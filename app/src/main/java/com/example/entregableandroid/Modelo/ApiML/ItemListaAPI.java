package com.example.entregableandroid.Modelo.ApiML;

public class ItemListaAPI {
    private String id;
    private String site_id;
    private String title;
    private String price;
    private String permalink;
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
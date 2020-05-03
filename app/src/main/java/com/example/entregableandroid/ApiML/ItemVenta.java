package com.example.entregableandroid.ApiML;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemVenta {
    private String titulo;
    private String price;
    private String id;
    private String urlImagen;
    private Double latitud,longitud;

    public ItemVenta(JSONObject jsonObject ) throws JSONException {
        this.id = jsonObject.getString("id");
        this.titulo = jsonObject.getString("title");
        this.urlImagen = jsonObject.getJSONArray("pictures").getJSONObject(0).getString("url");
        this.price = jsonObject.getString("price");
        this.latitud = jsonObject.getJSONObject("seller_address").getDouble("latitude");
        this.longitud = jsonObject.getJSONObject("seller_address").getDouble("longitude");
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }
}

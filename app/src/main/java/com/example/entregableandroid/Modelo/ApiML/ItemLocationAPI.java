package com.example.entregableandroid.Modelo.ApiML;

import org.json.JSONObject;

import java.io.Serializable;

public class ItemLocationAPI implements Serializable {
    private String address_line;
    private String zip_code;
    /*
    private JSONObject neighborhood;
    private JSONObject city;
    private JSONObject state;
    private JSONObject country;

     */
    private Double latitude;
    private Double longitude;

    public ItemLocationAPI(String address_line, String zip_code, JSONObject neighborhood, JSONObject city, JSONObject state, JSONObject country, Double latitude, Double longitude) {
        this.address_line = address_line;
        this.zip_code = zip_code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}





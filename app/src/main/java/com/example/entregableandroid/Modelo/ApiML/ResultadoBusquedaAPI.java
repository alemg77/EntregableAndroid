package com.example.entregableandroid.Modelo.ApiML;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusquedaAPI implements Serializable {
    private String site_id;
//    private String query;
//    private JSONObject paging;
    private List<ItemListaAPI> results;
//    private List<JSONObject> secondary_results;
//    private List<JSONObject> related_results;
//    private JSONObject sort;
//    private List<JSONObject> available_sorts;
//    private List<JSONObject> filters;


    public ResultadoBusquedaAPI(List<ItemListaAPI> results) {
        this.site_id = "Guardados localmente";
        this.results = results;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public List<ItemListaAPI> getResults() {
        return results;
    }

    public void setResults(List<ItemListaAPI> results) {
        this.results = results;
    }







}


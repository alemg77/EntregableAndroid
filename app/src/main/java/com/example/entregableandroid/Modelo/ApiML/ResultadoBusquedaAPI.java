package com.example.entregableandroid.Modelo.ApiML;

import org.json.JSONObject;

import java.util.List;

public class ResultadoBusquedaAPI {
    private String site_id;
    private String query;
    private JSONObject paging;
    private List<ItemListaAPI> results;
    private List<JSONObject> secondary_results;
    private List<JSONObject> related_results;
    private JSONObject sort;
    private List<JSONObject> available_sorts;
    private List<JSONObject> filters;
    private List<JSONObject> available_filters;


    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public JSONObject getPaging() {
        return paging;
    }

    public void setPaging(JSONObject paging) {
        this.paging = paging;
    }

    public List<ItemListaAPI> getResults() {
        return results;
    }

    public void setResults(List<ItemListaAPI> results) {
        this.results = results;
    }

    public List<JSONObject> getSecondary_results() {
        return secondary_results;
    }

    public void setSecondary_results(List<JSONObject> secondary_results) {
        this.secondary_results = secondary_results;
    }

    public List<JSONObject> getRelated_results() {
        return related_results;
    }

    public void setRelated_results(List<JSONObject> related_results) {
        this.related_results = related_results;
    }

    public JSONObject getSort() {
        return sort;
    }

    public void setSort(JSONObject sort) {
        this.sort = sort;
    }

    public List<JSONObject> getAvailable_sorts() {
        return available_sorts;
    }

    public void setAvailable_sorts(List<JSONObject> available_sorts) {
        this.available_sorts = available_sorts;
    }

    public List<JSONObject> getFilters() {
        return filters;
    }

    public void setFilters(List<JSONObject> filters) {
        this.filters = filters;
    }

    public List<JSONObject> getAvailable_filters() {
        return available_filters;
    }

    public void setAvailable_filters(List<JSONObject> available_filters) {
        this.available_filters = available_filters;
    }

}


package com.example.entregableandroid.Modelo.ApiML;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusqueda implements Serializable {

    public final static String BUSQUEDA_API = "Busqueda en la API";
    public final static String BUSQUEDA_FIREBASE = "Busqueda en Firebase";
    public final static String BUSQUEDA_DB_LOCAL = "Busque en BAse de Datos";

    private String origen;
    private List<Item> results;


    public ResultadoBusqueda(List<Item> results, String origen) {
        this.results = results;
        this.origen = origen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public List<Item> getResults() {
        return results;
    }

    public void setResults(List<Item> results) {
        this.results = results;
    }







}


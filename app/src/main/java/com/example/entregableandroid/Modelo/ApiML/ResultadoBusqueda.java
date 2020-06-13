package com.example.entregableandroid.Modelo.ApiML;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ResultadoBusqueda implements Serializable {

    //public final static String BUSQUEDA_VIEJA = "Busqueda ya mostrada";
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

    public void swapElementos ( int  fromposition, int toposition ){
        Collections.swap(results, fromposition, toposition);
    }

    public void eliminarElemento(int posicion ) {
        results.remove(posicion);
    }

    public void agregarElemento(int posicion, Item item) {
        results.add(posicion, item);
    }



}


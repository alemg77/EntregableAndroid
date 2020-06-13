package com.example.entregableandroid.Modelo.ApiML;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultadoBusqueda implements Serializable {

    //public final static String BUSQUEDA_VIEJA = "Busqueda ya mostrada";
    public final static String BUSQUEDA_API = "Busqueda en la API";
    public final static String BUSQUEDA_FIREBASE = "Busqueda en Firebase";
    public final static String BUSQUEDA_DB_LOCAL = "Busque en BAse de Datos";

    private String origen;
    private Integer pagina;
    private List<Item> results;

    public ResultadoBusqueda() {
        this.results = new ArrayList<>();
        this.origen = "Sin origen";
        this.pagina = 0;
    }

    public ResultadoBusqueda(List<Item> results) {
        this.results = results;
        this.origen = "Sin origen";
        this.pagina = 0;
    }

    public ResultadoBusqueda(List<Item> results, String origen) {
        this.results = results;
        this.origen = origen;
        this.pagina = 0;
    }

    public ResultadoBusqueda(List<Item> results, String origen, Integer pagina) {
        this.results = results;
        this.origen = origen;
        this.pagina = pagina;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
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

    public void agregarListaElementos ( List<Item> lista) {
        results.addAll(lista);
    }

}


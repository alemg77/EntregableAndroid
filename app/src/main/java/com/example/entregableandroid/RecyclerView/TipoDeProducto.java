package com.example.entregableandroid.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TipoDeProducto {

    private String nombre;
    private Integer id;

    public TipoDeProducto(String nombre, Integer id) {
        this.nombre = nombre;
        this.id = id;
    }

    public TipoDeProducto (){

    }

    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
        return id;
    }

    public List<TipoDeProducto> cargarTiposDeProductos() {
        List<TipoDeProducto> listaDeProductos = new ArrayList<>();
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        return listaDeProductos;
    }

}


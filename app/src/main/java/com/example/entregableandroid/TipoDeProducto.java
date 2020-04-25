package com.example.entregableandroid;

public class TipoDeProducto {

    private String nombre;
    private Integer id;

    public TipoDeProducto(String nombre, Integer id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
        return id;
    }
}


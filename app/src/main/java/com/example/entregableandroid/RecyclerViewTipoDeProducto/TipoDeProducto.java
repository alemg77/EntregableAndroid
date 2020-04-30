package com.example.entregableandroid.RecyclerViewTipoDeProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoDeProducto implements Serializable {

    private String nombre;
    private Integer id;

    public TipoDeProducto(String nombre, Integer id) {
        this.nombre = nombre;
        this.id = id;
    }

    public TipoDeProducto() {
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
        return id;
    }



}




package com.example.entregableandroid.RecyclerView;

import com.example.entregableandroid.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Serializable {
    private String nombre;
    private String descripcion;
    private Integer id;
    private int imagen;
    private Double precio;

    public Producto(String nombre, String descripcion, Double precio, Integer id, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.imagen = imagen;
        this.precio = precio;
    }

    public Producto(){}

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getId() {
        return id;
    }

    public int getImagen() {
        return imagen;
    }

    public Double getPrecio() {
        return precio;
    }


}

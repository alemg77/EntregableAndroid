package com.example.entregableandroid;

public class Silla {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer idProducto;
    private int imagen;

    public Silla(String nombre, String descripcion, Double precio, Integer idProducto, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idProducto = idProducto;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public int getImagen() {
        return imagen;
    }
}

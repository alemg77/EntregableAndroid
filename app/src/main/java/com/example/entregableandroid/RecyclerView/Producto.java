package com.example.entregableandroid.RecyclerView;

import com.example.entregableandroid.R;

import java.util.ArrayList;
import java.util.List;

public class Producto {
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

    public List<Producto> cargarSillas(){
        List<Producto> listaDeSillas = new ArrayList<>();
        listaDeSillas.add(new Producto("Banqueta", "Blanca, regulable en altura", 7200.0, 10, R.drawable.banqueta7200));
        listaDeSillas.add(new Producto("Sillon Ejecutivo", "Clasico, negro", 6800.0, 11, R.drawable.sillon_ejecutivo_6800));
        listaDeSillas.add(new Producto("Sillon Gerencial", "Para el generente", 12000.0, 12, R.drawable.sillon_gerencial));
        listaDeSillas.add(new Producto("Sillas comedor", "Clasicas, blancas", 7500.0, 13, R.drawable.sillas_comedorx4_7500));
        listaDeSillas.add(new Producto("Sillon Gerencial Alto", "Para el generente", 21000.0, 14, R.drawable.sillon_gerencial_alto));
        return listaDeSillas;
    }

    public List<Producto> cargarMesas() {
        List<Producto> listaDeMesas = new ArrayList<>();
        listaDeMesas.add(new Producto("Mesa Lateral", "Librero y adornos", 2000.0, 100, R.drawable.mesa_lateral));
        listaDeMesas.add(new Producto("Mesa industrial", "Para trabajar", 3000.0, 101, R.drawable.mesa_industrial));
        listaDeMesas.add(new Producto("Mesa comedor", "Para comer", 4000.0, 102, R.drawable.mesa_comedor));
        return listaDeMesas;
    }

}

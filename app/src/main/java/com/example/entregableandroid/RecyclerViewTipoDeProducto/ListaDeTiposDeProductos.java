package com.example.entregableandroid.RecyclerViewTipoDeProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaDeTiposDeProductos implements Serializable {
    ArrayList<TipoDeProducto> listaDeTipoDeProductos;

    public ListaDeTiposDeProductos(ArrayList<TipoDeProducto> listaDeTipoDeProductos) {
        this.listaDeTipoDeProductos = listaDeTipoDeProductos;
    }

    public ListaDeTiposDeProductos(){

    }

    public List<TipoDeProducto> getListaDeTipoDeProductos() {
        return listaDeTipoDeProductos;
    }

    public ListaDeTiposDeProductos cargarTiposDeProductos() {
        ArrayList<TipoDeProducto> lista = new ArrayList<>();
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        lista.add(new TipoDeProducto("Sillas", 50));
        lista.add(new TipoDeProducto("Mesas", 51));
        return new ListaDeTiposDeProductos(lista);
    }
}

package com.example.entregableandroid.RecyclerView;

import com.example.entregableandroid.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaDeProductos implements Serializable {
    private List<Producto> listaDeProductos;

    public ListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }

    public ListaDeProductos() { }

    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public ListaDeProductos Sillas() {
        ArrayList<Producto> listaDeSillas = new ArrayList<>();
        listaDeSillas.add(new Producto("Banqueta", "Blanca, regulable en altura", 7200.0, 10, R.drawable.banqueta7200));
        listaDeSillas.add(new Producto("Sillon Ejecutivo", "Clasico, negro", 6800.0, 11, R.drawable.sillon_ejecutivo_6800));
        listaDeSillas.add(new Producto("Sillon Gerencial", "Para el generente", 12000.0, 12, R.drawable.sillon_gerencial));
        listaDeSillas.add(new Producto("Sillas comedor", "Clasicas, blancas", 7500.0, 13, R.drawable.sillas_comedorx4_7500));
        listaDeSillas.add(new Producto("Sillon Gerencial Alto", "Para el generente", 21000.0, 14, R.drawable.sillon_gerencial_alto));
        return new ListaDeProductos(listaDeSillas);
    }

    public ListaDeProductos Mesas() {
        ArrayList<Producto> listaDeMesas = new ArrayList<>();
        listaDeMesas.add(new Producto("Mesa Lateral", "Librero y adornos", 2000.0, 100, R.drawable.mesa_lateral));
        listaDeMesas.add(new Producto("Mesa industrial", "Para trabajar", 3000.0, 101, R.drawable.mesa_industrial));
        listaDeMesas.add(new Producto("Mesa comedor", "Para comer", 4000.0, 102, R.drawable.mesa_comedor));
        return new ListaDeProductos(listaDeMesas);
    }
}

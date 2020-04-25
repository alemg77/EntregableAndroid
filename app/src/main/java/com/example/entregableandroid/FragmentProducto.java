package com.example.entregableandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentProducto extends Fragment {

    RecyclerView recyclerViewProducto;
    List<Producto> listaDeProductos;
    AvisosMainActivity listener;

    public FragmentProducto ( List<Producto> listaDeProductos, AvisosMainActivity listener){
        this.listaDeProductos = listaDeProductos;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_frag_tipo_producto, container, false);
        recyclerViewProducto = inflate.findViewById(R.id.FragTipoProductoRecuclerView);
        ProductoAdapter productoAdapter = new ProductoAdapter(listaDeProductos, listener);
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager((Context)listener);
        recyclerViewProducto.setLayoutManager(dosLayoutManager);
        recyclerViewProducto.setAdapter(productoAdapter);
        return inflate;
    }
}


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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragTipoProducto extends Fragment {

    RecyclerView recyclerViewTipoProducto;

    List<TipoDeProducto> listaTiposProducto;
    AvisosMainActivity listener;

    public FragTipoProducto(List<TipoDeProducto> listaDeProductos, AvisosMainActivity listener) {
        this.listaTiposProducto = listaDeProductos;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_frag_tipo_producto, container, false);
        recyclerViewTipoProducto = inflate.findViewById(R.id.FragTipoProductoRecuclerView);
        TipoDeProductosAdapter tipoDeProductosAdapter = new TipoDeProductosAdapter(listaTiposProducto, listener);
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager((Context)listener);
        recyclerViewTipoProducto.setLayoutManager(dosLayoutManager);
        recyclerViewTipoProducto.setAdapter(tipoDeProductosAdapter);
        return inflate;
    }
}

package com.example.entregableandroid.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.AvisosActivity;
import com.example.entregableandroid.R;
import com.example.entregableandroid.RecyclerView.TipoDeProducto;
import com.example.entregableandroid.RecyclerView.TipoDeProductosAdapter;

import java.util.List;

public class FragTipoProducto extends Fragment {

    RecyclerView recyclerViewTipoProducto;

    List<TipoDeProducto> listaTiposProducto;
    AvisosActivity listener;

    public FragTipoProducto(List<TipoDeProducto> listaDeProductos) {
        this.listaTiposProducto = listaDeProductos;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (AvisosActivity) context;
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

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

import com.example.entregableandroid.RecyclerViewProducto.ListaDeProductos;
import com.example.entregableandroid.RecyclerViewProducto.Producto;
import com.example.entregableandroid.RecyclerViewProducto.ProductoAdapter;
import com.example.entregableandroid.R;

public class FragmentProducto extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    RecyclerView recyclerViewProducto;
    FragmentProductoListener listener;

    public FragmentProducto (){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (FragmentProductoListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_frag_tipo_producto, container, false);
        recyclerViewProducto = inflate.findViewById(R.id.FragTipoProductoRecuclerView);
        Bundle bundle = getArguments();
        ListaDeProductos lista = (ListaDeProductos) bundle.getSerializable(ListaDeProductos.class.toString());
        ProductoAdapter productoAdapter = new ProductoAdapter(lista.getListaDeProductos() ,this);
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducto.setLayoutManager(dosLayoutManager);
        recyclerViewProducto.setAdapter(productoAdapter);
        return inflate;
    }

    @Override
    public void seleccionProducto(Producto producto) {
        listener.selleccionProducto(producto);
    }

    public interface FragmentProductoListener {
        void selleccionProducto ( Producto producto);
    }
}


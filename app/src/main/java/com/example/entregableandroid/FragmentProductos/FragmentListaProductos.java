package com.example.entregableandroid.FragmentProductos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.ApiML.ElementoLista;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.R;

public class FragmentListaProductos extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    RecyclerView recyclerViewProducto;
    FragmentProductoListener listener;

    public FragmentListaProductos(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (FragmentProductoListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerViewProducto = inflate.findViewById(R.id.FragmentRecyclerView);
        Bundle bundle = getArguments();
        ListaDeVentasML lista = (ListaDeVentasML) bundle.getSerializable(ListaDeVentasML.class.toString());
        Context context = getActivity().getApplicationContext();
        ProductoAdapter productoAdapter = new ProductoAdapter(context, this, lista);
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducto.setLayoutManager(dosLayoutManager);
        recyclerViewProducto.setAdapter(productoAdapter);
        return inflate;
    }

    @Override
    public void seleccionProducto(ElementoLista elementoLista) {
        //listener.selleccionProducto(elementoLista);
    }

    public interface FragmentProductoListener {
        void selleccionProducto ( Producto producto);
    }
}


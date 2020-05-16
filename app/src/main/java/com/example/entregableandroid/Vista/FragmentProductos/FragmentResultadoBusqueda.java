package com.example.entregableandroid.Vista.FragmentProductos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;
import com.example.entregableandroid.R;

import java.util.List;

public class FragmentResultadoBusqueda extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    private RecyclerView recyclerViewProducto;
    private FragmentResultadoBusqueda.Aviso listener;

    public FragmentResultadoBusqueda(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Aviso) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerViewProducto = inflate.findViewById(R.id.FragmentRecyclerView);
        Bundle bundle = getArguments();
        ResultadoBusquedaAPI resultadoBusquedaAPI = (ResultadoBusquedaAPI) bundle.getSerializable(ResultadoBusquedaAPI.class.toString());
        List<ItemListaAPI> results = resultadoBusquedaAPI.getResults();
        Context context = getActivity().getApplicationContext();
        ProductoAdapter productoAdapter = new ProductoAdapter(context, this, results);
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducto.setLayoutManager(dosLayoutManager);
        recyclerViewProducto.setAdapter(productoAdapter);
        return inflate;
    }

    @Override
    public void seleccionProducto(ItemListaAPI itemListaAPI) {
        listener.selleccionProducto(itemListaAPI);
    }

    public interface Aviso {
        void selleccionProducto (ItemListaAPI itemListaAPI);
    }
}



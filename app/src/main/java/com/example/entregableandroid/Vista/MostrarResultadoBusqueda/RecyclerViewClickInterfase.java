package com.example.entregableandroid.Vista.MostrarResultadoBusqueda;

import com.example.entregableandroid.Modelo.ApiML.Item;

public interface RecyclerViewClickInterfase {

    void onItemClick(Item item);

    void onLongItemClick(int position);
}

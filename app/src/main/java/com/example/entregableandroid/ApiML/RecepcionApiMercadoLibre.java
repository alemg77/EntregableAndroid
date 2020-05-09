package com.example.entregableandroid.ApiML;

import com.example.entregableandroid.Modelo.ItemVenta;

public interface RecepcionApiMercadoLibre {
    void recepcionMLlistaVenta(ListaDeVentasML listaDeVentasML);
    void recepcionMLitemVenta(ItemVenta itemVenta);
    void errorPedidoApiMercadolibre();
}
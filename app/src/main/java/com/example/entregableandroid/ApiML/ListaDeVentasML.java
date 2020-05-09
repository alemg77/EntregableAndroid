package com.example.entregableandroid.ApiML;

import com.example.entregableandroid.Modelo.ElementoLista;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaDeVentasML implements Serializable {
    private ArrayList<ElementoLista> arrayListVentasML;

    public ListaDeVentasML(){}

    public ListaDeVentasML(ArrayList<ElementoLista> arrayListVentasML) {
        this.arrayListVentasML = arrayListVentasML;
    }

    public int cantidadElementos() {
        return arrayListVentasML.size();
    }

    public ElementoLista obtenerElemento(int posision){
        return arrayListVentasML.get(posision);
    }

    public ArrayList<ElementoLista> getArrayListVentasML() {
        return arrayListVentasML;
    }
}

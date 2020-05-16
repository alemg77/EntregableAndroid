package com.example.entregableandroid.Modelo.ApiML;

import java.util.List;

public class ListaImagenes {
    private List<Imagen> pictures;

    public ListaImagenes(List<Imagen> pictures) {
        this.pictures = pictures;
    }

    public List<Imagen> getPictures() {
        return pictures;
    }

    public void setPictures(List<Imagen> pictures) {
        this.pictures = pictures;
    }
}

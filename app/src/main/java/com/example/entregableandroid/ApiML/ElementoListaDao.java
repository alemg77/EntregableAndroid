package com.example.entregableandroid.ApiML;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ElementoListaDao {

    @Query("SELECT COUNT(*) FROM " + ElementoLista.TABLE_NAME)
    int cantidadElementos();

    @Query("SELECT * FROM "+ElementoLista.TABLE_NAME)
    List<ElementoLista> getTodos();

    //insertar
    @Insert
    void instarAll(ElementoLista ... elementos);

    //eliminar
    @Query("DELETE FROM " + ElementoLista.TABLE_NAME + " WHERE " + ElementoLista.COLUMN_ID + " = :id")
    int deleteById(String id);

    //actualizar
    @Update
    int updateEntidad(ElementoLista elemento);

    //insertar 2
    @Insert
    long insert(ElementoLista usuarios);
}

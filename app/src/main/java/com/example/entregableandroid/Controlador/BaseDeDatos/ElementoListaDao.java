package com.example.entregableandroid.Controlador.BaseDeDatos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.entregableandroid.Modelo.ApiML.Item;

import java.util.List;

@Dao
public interface ElementoListaDao {

    @Query("SELECT COUNT(*) FROM " + Item.TABLE_NAME)
    int cantidadElementos();

    @Query("SELECT * FROM "+ Item.TABLE_NAME)
    List<Item> getTodos();

    //insertar 2
    @Insert
    long insert(Item usuarios);

    //eliminar
    @Query("DELETE FROM " + Item.TABLE_NAME + " WHERE " + Item.COLUMN_ID + " = :moco")
    int deleteById(String moco);

    @Query("SELECT * FROM "+ Item.TABLE_NAME+" LIMIT 1")
    Item getPrimerElemento();

    /*
    @Query("SELECT * FROM "+ItemListaAPI.TABLE_NAME)
    LiveData<List<ItemListaAPI>> getTodos();

    @Query("SELECT COUNT(*) FROM " + ItemListaAPI.TABLE_NAME)
    LiveData<Integer> cantidadElementos();
     */

}

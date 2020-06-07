package com.example.entregableandroid.Controlador.BaseDeDatos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;

import java.util.List;

@Dao
public interface ElementoListaDao {

    @Query("SELECT COUNT(*) FROM " + ItemListaAPI.TABLE_NAME)
    int cantidadElementos();

    @Query("SELECT * FROM "+ItemListaAPI.TABLE_NAME)
    List<ItemListaAPI> getTodos();

    //insertar 2
    @Insert
    long insert(ItemListaAPI usuarios);

    //eliminar
    @Query("DELETE FROM " + ItemListaAPI.TABLE_NAME + " WHERE " + ItemListaAPI.COLUMN_ID + " = :moco")
    int deleteById(String moco);

    @Query("SELECT * FROM "+ItemListaAPI.TABLE_NAME+" LIMIT 1")
    ItemListaAPI getPrimerElemento();

    /*
    // TODO: Aplicar LiveData!!!!
    @Query("SELECT * FROM "+ItemListaAPI.TABLE_NAME)
    LiveData<List<ItemListaAPI>> getTodos();

    @Query("SELECT COUNT(*) FROM " + ItemListaAPI.TABLE_NAME)
    LiveData<Integer> cantidadElementos();
     */

}

package com.example.entregableandroid.DAO.BaseDeDatos;


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

    //insertar
    @Insert
    void instarAll(ItemListaAPI... elementos);

    @Query("SELECT * FROM "+ItemListaAPI.TABLE_NAME+" LIMIT 1")
    ItemListaAPI getPrimerElemento();

    //eliminar
    @Query("DELETE FROM " + ItemListaAPI.TABLE_NAME + " WHERE " + ItemListaAPI.COLUMN_ID + " = :moco")
    int deleteById(String moco);

    //actualizar
    @Update
    int updateEntidad(ItemListaAPI elemento);

    //insertar 2
    @Insert
    long insert(ItemListaAPI usuarios);
}

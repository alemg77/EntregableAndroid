package com.example.entregableandroid.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;

@Database(entities = {ItemListaAPI.class}, version = 2)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ElementoListaDao elementoListaDao();

    private static AppDatabase sIntance;
}

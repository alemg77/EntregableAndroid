package com.example.entregableandroid.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.entregableandroid.ApiML.ElementoLista;
import com.example.entregableandroid.ApiML.ElementoListaDao;

@Database(entities = {ElementoLista.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ElementoListaDao elementoListaDao();

    private static AppDatabase sIntance;
}

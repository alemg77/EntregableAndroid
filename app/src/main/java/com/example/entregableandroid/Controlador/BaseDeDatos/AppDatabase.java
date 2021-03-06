package com.example.entregableandroid.Controlador.BaseDeDatos;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.entregableandroid.Modelo.ApiML.Item;

@Database(entities = {Item.class}, version = 3)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ElementoListaDao elementoListaDao();
    private static AppDatabase sIntance;

    public static AppDatabase get (Context context){
        if ( sIntance == null) {
            sIntance = Room.databaseBuilder(context, AppDatabase.class, Constantes.BD_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()       // Borra la base existente si en una version anterior
                    .build();
        }
        return sIntance;
    }



    // Para mejorar: Hacer una version que realize la busqueda en otro hilo
    /*
    // synchronized: Un solo hilo puede acceder para que no se creen dos instancias.
    public static synchronized AppDatabase getInstance(Context context){
    */

}

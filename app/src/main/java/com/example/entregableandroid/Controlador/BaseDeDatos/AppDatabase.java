package com.example.entregableandroid.Controlador.BaseDeDatos;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;

@Database(entities = {ItemListaAPI.class}, version = 2)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ElementoListaDao elementoListaDao();

    private static AppDatabase sIntance;


    public static AppDatabase get (Context context){
        return Room.databaseBuilder(context,AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()       // Borra la base existente si en una version anterior
                .build();
    }

    // TODO: Hacer una version que funcione en otro hilo

    /*
    // synchronized: Un solo hilo puede acceder para que no se creen dos instancias.
    public static synchronized AppDatabase getInstance(Context context){
        if ( sIntance == null ) {
            // No se pude hacer un new porque es abstracta
            sIntance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,Constantes.BD_NAME)
                    .fallbackToDestructiveMigration()       // Borra la base existente si en una version anterior
                    .build();
        }
        return sIntance;
    }
    */

}

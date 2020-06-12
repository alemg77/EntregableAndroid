package com.example.entregableandroid.Controlador;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.entregableandroid.Controlador.ApiML.DaoApiML;
import com.example.entregableandroid.Controlador.BaseDeDatos.AppDatabase;
import com.example.entregableandroid.Modelo.ApiML.Item;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private String TAG = getClass().toString();
    private static AppDatabase db;
    private static DaoApiML daoApiML;
    private static ItemViewModel instancia;
    private ViewModelStoreOwner storeOwner;


    private MutableLiveData<ResultadoBusqueda> resultadoBusquedaDB;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.get(application);
        resultadoBusquedaDB = new MutableLiveData<>();
    }

    public static ItemViewModel getInstancia(ViewModelStoreOwner owner){
        if ( instancia == null ){
            instancia = new ViewModelProvider(owner).get(ItemViewModel.class);
        }
        return instancia;
    }

    public int cantidadDB(){
        return db.elementoListaDao().cantidadElementos();
    }

    public List<Item> getTodosDB() {
        List<Item> todos = db.elementoListaDao().getTodos();
        ResultadoBusqueda resultadoBusqueda = new ResultadoBusqueda(todos, ResultadoBusqueda.BUSQUEDA_DB_LOCAL);
        resultadoBusquedaDB.setValue(resultadoBusqueda);
        return todos;
    }

    public void agregarDB(Item item) {
        try {
            long insert = db.elementoListaDao().insert(item);
            if (insert > 0) {
                Log.d(TAG, "Insercion en la base de datos correcto!!");
                if ( insert > 4 ) {
                    Item item1 = db.elementoListaDao().getPrimerElemento();
                    db.elementoListaDao().deleteById(item1.getId());
                }
            } else {
                Log.d(TAG, "Error en la insercion en la base de datos!!");
            }
        } catch ( Exception e ) {
            if ( e instanceof SQLiteConstraintException) {
                Log.d(TAG, "No se agrego el elemento porque ya estaba");
            } else {
                Log.d(TAG, "Ocurrio la excepcion: " + e.toString());
            }
        }
    }

    public MutableLiveData<ResultadoBusqueda> getResultadoBusquedaDB(){
        if ( resultadoBusquedaDB == null) {
            resultadoBusquedaDB = new MutableLiveData<ResultadoBusqueda>();
        }
        return resultadoBusquedaDB;
    }

}

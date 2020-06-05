package com.example.entregableandroid.Controlador.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DAOFirebase extends ViewModel {

    private static DAOFirebase instanciaUnica;

    public final static String NOMBRE_BD_ITEMS = "Items a la venta";

    private CollectionReference referenciaDB;
    private MutableLiveData<List<ItemAPI>> listaItems;
    private MutableLiveData<Boolean> confirmacionFirebase;

    public MutableLiveData<List<ItemAPI>> getListaItems() {
        if ( listaItems == null) {
            listaItems = new MutableLiveData<List<ItemAPI>>();
        }
        return listaItems;
    }

    private MutableLiveData<Boolean> getConfirmacionFirebase() {
        if ( confirmacionFirebase == null ){
            confirmacionFirebase = new MutableLiveData<Boolean>();
        }
        return confirmacionFirebase;
    }

    public static DAOFirebase get() {
        if ( instanciaUnica == null ) {
            instanciaUnica = new DAOFirebase();
        }
        return instanciaUnica;
    }

    public DAOFirebase() {
        this.referenciaDB = FirebaseFirestore.getInstance().collection(NOMBRE_BD_ITEMS);
        // Si me llaman sin escuchador, se cuelga sin estas linea:
        listaItems = new MutableLiveData<List<ItemAPI>>();
        confirmacionFirebase = new MutableLiveData<Boolean>();
    }

    public void leerTodos() {
        referenciaDB.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ItemAPI> lista = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            ItemAPI dato = (ItemAPI) queryDocumentSnapshot.toObject(ItemAPI.class);
                            lista.add(dato);
                        }
                        listaItems.setValue(lista);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Fallo en la lectura de firebase: " + e.toString());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "Fin de la lectura de Firebase");
                    }
                });
    }


    public void guardarNuevo (ItemAPI itemAPI) {
        confirmacionFirebase.setValue(false);
        referenciaDB.document().set(itemAPI)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "No pudimos guardar en Firebase");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Se completo el guardado en Firebase");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Exito guardado en Firebase");
                        confirmacionFirebase.setValue(true);
                    }
                });
    }
}

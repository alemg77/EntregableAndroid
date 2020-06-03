package com.example.entregableandroid.Controlador.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

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

public class DAOFirebase<T> {

    private CollectionReference referenciaDB;
    private DAOFirebase.Recibir listener;
    private T t;
    private Class<?> aClass;
    private Class<?> moco;

    public DAOFirebase(String nombreDB, DAOFirebase.Recibir listener, T dummy) {
        referenciaDB = FirebaseFirestore.getInstance().collection(nombreDB);
        this.listener = listener;
        moco =    this.getClass();
        aClass = dummy.getClass();
    }

    public void guardarNuevo (T datos) {
        referenciaDB.document().set(datos)
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
                    }
                });
    }

    public void actualizar (T datos, String documento) {
        referenciaDB.document(documento).set(datos)
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
                    }
                });
    }

    public void leer() {
        referenciaDB.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<T> listaDatos = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            T dato = (T) queryDocumentSnapshot.toObject(aClass);
                            listaDatos.add(dato);
                            Log.d(TAG, "Leimos un documento");
                        }
                        listener.recibir(listaDatos);
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

    public interface Recibir<T> {
        void recibir(List<T> datos);
    }
}

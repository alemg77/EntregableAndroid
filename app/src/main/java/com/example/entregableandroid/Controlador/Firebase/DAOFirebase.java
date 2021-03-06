package com.example.entregableandroid.Controlador.Firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.entregableandroid.Modelo.ApiML.Item;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DAOFirebase extends ViewModel {

    private static DAOFirebase instanciaUnica;

    public final static String NOMBRE_BD_ITEMS = "Items a la venta";

    private FirebaseStorage storage;
    private CollectionReference referenciaDB;
    private MutableLiveData<ResultadoBusqueda> resultadoBusquedaMutableLiveData;
    private MutableLiveData<String> itemPublicado;
    private MutableLiveData<Integer> progreso;
    private MutableLiveData<String> archivoSubido;

    public static DAOFirebase get() {
        if (instanciaUnica == null) {
            instanciaUnica = new DAOFirebase();
        }
        return instanciaUnica;
    }

    public DAOFirebase() {
        this.referenciaDB = FirebaseFirestore.getInstance().collection(NOMBRE_BD_ITEMS);
        this.storage = FirebaseStorage.getInstance();
        this.resultadoBusquedaMutableLiveData = new MutableLiveData<>();
        this.itemPublicado = new MutableLiveData<>();
        this.progreso = new MutableLiveData<>();
        this.archivoSubido = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getProgreso(){
        if ( progreso == null ) {
            progreso = new MutableLiveData<Integer>();
        }
        return progreso;
    }

    public MutableLiveData<ResultadoBusqueda> getListaItems() {
        if (resultadoBusquedaMutableLiveData == null) {
            resultadoBusquedaMutableLiveData = new MutableLiveData<>();
        }
        return resultadoBusquedaMutableLiveData;
    }

    public MutableLiveData<String> getItemPublicado() {
        if ( itemPublicado == null) {
            itemPublicado = new MutableLiveData<>();
        }
        return itemPublicado;
    }

    public MutableLiveData<String> getArchivoSubido() {
        if ( archivoSubido == null ) {
            archivoSubido = new MutableLiveData<String>();
        }
        return archivoSubido;
    }

    public void leerTodos() {
        referenciaDB.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Item> lista = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            Item dato = queryDocumentSnapshot.toObject(Item.class);
                            lista.add(dato);
                        }
                        ResultadoBusqueda resultadoBusqueda = new ResultadoBusqueda(lista, ResultadoBusqueda.BUSQUEDA_FIREBASE);
                        resultadoBusquedaMutableLiveData.setValue(resultadoBusqueda);
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

    public void buscarMisPublicaciones() {
        Log.d(TAG, "Me piden la lista de mis publicaciones");
        String uid = FirebaseAuth.getInstance().getUid();
        referenciaDB.whereEqualTo("vendedor", uid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "Trajimos la lista de mis publicaciones");
                        List<Item> lista = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            Item dato = queryDocumentSnapshot.toObject(Item.class);
                            lista.add(dato);
                        }
                        ResultadoBusqueda resultadoBusqueda = new ResultadoBusqueda(lista, ResultadoBusqueda.BUSQUEDA_FIREBASE);
                        resultadoBusquedaMutableLiveData.setValue(resultadoBusqueda);
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


    public void guardarNuevo(Item item) {
        itemPublicado.setValue(null);
        referenciaDB.document().set(item)
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
                        itemPublicado.setValue("Se subio un archivo");
                    }
                });
    }

    public void guardarNuevo(Uri uriFile) {
        String fechayHora = Calendar.getInstance().getTime().toString();
        String uid = FirebaseAuth.getInstance().getUid();

        StorageReference riversRef = storage.getReference().child(uid+fechayHora);
        UploadTask uploadTask = riversRef.putFile(uriFile);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "Fallo al subir archivo en FireStore");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Subio archivo en Firestore");
            }
        });
    }

    public void guardarNuevo(@NonNull byte[] bytes) {
        progreso.setValue(0);
        archivoSubido.setValue(null);
        String fechayHora = Calendar.getInstance().getTime().toString();
        String uid = FirebaseAuth.getInstance().getUid();
        StorageReference riversRef = storage.getReference().child(uid+fechayHora);
        UploadTask uploadTask = riversRef.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "Fallo al subir archivo en FireStore");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Subio archivo en Firestore");
                StorageReference storage = taskSnapshot.getStorage();
                String path = taskSnapshot.getMetadata().getPath();
                archivoSubido.setValue(path);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                Long aux = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                progreso.setValue(aux.intValue());
            }
        });
    }

}

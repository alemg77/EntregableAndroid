package com.example.entregableandroid.Firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentBlankBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static android.content.ContentValues.TAG;


public class FragmentFirebase extends Fragment {

    FragmentBlankBinding binding;
    CollectionReference dbVenta;
    FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(getLayoutInflater());
        firebaseFirestore = FirebaseFirestore.getInstance();
        dbVenta = firebaseFirestore.collection("Items a la venta");
        escucharBotonEscribir();
        escucharBotonLeer();
        return binding.getRoot();
    }

    private void escucharBotonEscribir() {
        binding.botonEscribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemAPI itemAPI = new ItemAPI("009", "Sacorcho", "Juancho", "5");
                itemAPI.setCategory_id("cocina");
                guardarNuevoFirebase(itemAPI);
            }
        });
    }

    private void escucharBotonLeer() {
        binding.Leer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerFirebase();
            }
        });
    }

    void guardarNuevoFirebase (ItemAPI itemAPI){
        itemAPI.setSeller_id(FirebaseAuth.getInstance().getUid());
        dbVenta.document().set(itemAPI)
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


    private void leerFirebase() {
        dbVenta.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            ItemAPI itemAPI = queryDocumentSnapshot.toObject(ItemAPI.class);
                            Log.d(TAG, "Leimos un documento");
                        }
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
}
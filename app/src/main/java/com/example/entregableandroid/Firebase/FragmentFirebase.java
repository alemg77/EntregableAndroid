package com.example.entregableandroid.Firebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.databinding.FragmentBlankBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentFirebase extends Fragment implements DAOFirebase.Recibir<Void> {

    FragmentBlankBinding binding;
    CollectionReference dbVenta;
    FirebaseFirestore firebaseFirestore;
    public final static String ITEMSVENTA = "Items a la venta";

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
                ItemAPI itemAPI = new ItemAPI("010", "Sacorcho", "Juancho", "5");
                itemAPI.setCategory_id("cocina");
                itemAPI.setSeller_id(FirebaseAuth.getInstance().getUid());
                DAOFirebase<ItemAPI> daoFirebase = new DAOFirebase<>(ITEMSVENTA, FragmentFirebase.this, new ItemAPI());
                daoFirebase.guardarNuevo(itemAPI);
            }
        });
    }

    private void escucharBotonLeer() {
        binding.Leer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOFirebase<ItemAPI> daoFirebase = new DAOFirebase<>(ITEMSVENTA, FragmentFirebase.this, new ItemAPI());
                daoFirebase.leer();
            }
        });
    }

    @Override
    public void recibir(List<Void> datos) {
        Log.d(TAG, "Llegaron datos!!");
    }

}
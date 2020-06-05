package com.example.entregableandroid.Firebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Controlador.Firebase.DAOFirebaseGenerico;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;
import com.example.entregableandroid.R;
import com.example.entregableandroid.Vista.FragmentListaItems.FragmentListaItems;
import com.example.entregableandroid.databinding.FragmentBlankBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Observable;

import static android.content.ContentValues.TAG;


public class FragmentFirebase extends Fragment  {

    private FragmentBlankBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(getLayoutInflater());
        escucharBotonEscribir();
        escucharBotonLeer();
        escucharDAOFirebase();
        return binding.getRoot();
    }

    private void escucharDAOFirebase() {
        final androidx.lifecycle.Observer<List<ItemAPI>> observadorFirebase = new Observer<List<ItemAPI>>() {
            @Override
            public void onChanged(List<ItemAPI> itemAPIS) {
                Log.d(TAG, "Revisar que llego");
            }
        };
        DAOFirebase.get().getListaItems().observe(getViewLifecycleOwner(),observadorFirebase);
    }

    private void escucharBotonEscribir() {
        binding.botonEscribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemAPI itemAPI = new ItemAPI("011", "Melon", "Juancho", "5");
                itemAPI.setCategory_id("Frutas");
                itemAPI.setSeller_id(FirebaseAuth.getInstance().getUid());
                DAOFirebase.get().guardarNuevo(itemAPI);
            }
        });
    }

    private void escucharBotonLeer() {
        binding.Leer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOFirebase.get().leerTodos();
            }
        });
    }

}
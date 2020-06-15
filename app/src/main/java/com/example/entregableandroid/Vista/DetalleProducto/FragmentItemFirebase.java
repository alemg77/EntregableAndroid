package com.example.entregableandroid.Vista.DetalleProducto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.entregableandroid.Controlador.ApiML.DaoApiML;
import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Controlador.ItemViewModel;
import com.example.entregableandroid.Modelo.ApiML.DescripcionItem;
import com.example.entregableandroid.Modelo.ApiML.Imagen;
import com.example.entregableandroid.Modelo.ApiML.Item;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ItemLocationAPI;
import com.example.entregableandroid.Modelo.ApiML.ListaImagenes;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentDetalleProductoBinding;
import com.example.entregableandroid.databinding.FragmentItemFirebaseBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;


public class FragmentItemFirebase extends Fragment {
    FragmentItemFirebaseBinding binding;

    private String TAG = getClass().toString();
    private Boolean habilitarObservadores;
    private Item item;

    public FragmentItemFirebase() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        item = (Item) arguments.getSerializable(Item.class.toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemFirebaseBinding.inflate(getLayoutInflater());
        habilitarObservadores = false;
        binding.Titulo.setText(item.getTitle());
        binding.textoPrecio.setText("$" + item.getPrice());
        binding.TextoDescripcion.setText(item.getDescripcion());
        StorageReference child = FirebaseStorage.getInstance().getReference().child(item.getImagenFirebase());
        Glide.with(binding.getRoot()).load(child).into(binding.ImagenItemFirebase);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Habilito los observadores aca, para no reaccionar a lo que paso antes del inicio de la actividad
        habilitarObservadores = true;
    }

}

package com.example.entregableandroid.Vista.FragmentDetalleProducto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.entregableandroid.Modelo.ApiML.Imagen;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentDetalleProductoBinding;
import com.example.entregableandroid.databinding.FragmentImagenBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentImagen extends Fragment {

    private FragmentImagenBinding binding;

    public FragmentImagen() {  }

    public static FragmentImagen dameUnFragment(Imagen imagen){
        FragmentImagen fragmentImagen = new FragmentImagen();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Imagen.class.toString(),imagen);
        fragmentImagen.setArguments(bundle);
        return fragmentImagen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImagenBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        Imagen imagen = (Imagen) bundle.getSerializable(Imagen.class.toString());
        Glide.with(binding.getRoot()).load(imagen.getUrl()).into(binding.Imagen);
        return binding.getRoot();
    }
}

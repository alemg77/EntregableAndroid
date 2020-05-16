package com.example.entregableandroid.Vista.FragmentDetalleProducto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.entregableandroid.Modelo.ApiML.Imagen;
import com.example.entregableandroid.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentImagen extends Fragment {

    private ImageView imageView;

    public FragmentImagen() {
        // Required empty public constructor
    }

    //fabrica de fragments
    public static FragmentImagen dameUnFragment(Imagen imagen){
        //crear el fragment
        FragmentImagen fragmentImagen = new FragmentImagen();
        //pasarle el bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(Imagen.class.toString(),imagen);
        fragmentImagen.setArguments(bundle);
        //hacerle un return
        return fragmentImagen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_imagen, container, false);
        imageView = inflate.findViewById(R.id.FragmentImagenImagen);
        Bundle bundle = getArguments();
        Imagen imagen = (Imagen) bundle.getSerializable(Imagen.class.toString());
        Picasso.get().load(imagen.getUrl()).into(imageView);
        return inflate;
    }
}

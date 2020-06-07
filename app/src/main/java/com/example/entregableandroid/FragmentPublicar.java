package com.example.entregableandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;
import com.example.entregableandroid.databinding.FragmentPublicarBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class FragmentPublicar extends Fragment {

    private String TAG = getClass().toString();
    private FragmentPublicarBinding binding;
    private Bitmap imagenPublicacion;
    private String titulo;
    private String precio;
    private String descripcion;
    private Boolean publicando;

    public FragmentPublicar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPublicarBinding.inflate(getLayoutInflater());

        // Esto lo uso para evitar doble disparo;
        publicando = false;

        escucharBotonBuscarImagen();
        escucharBotonPublicar();

        // Actualiza el progress bar
        DAOFirebase.get().getProgreso().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.indeterminateBar.setProgress(integer);
            }
        });

        // Cuando termine de subir la imagen, sube el elemento
        DAOFirebase.get().getArchivoSubido().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String direccionFirebase) {
                if ( direccionFirebase != null ) {
                    ItemListaAPI itemListaAPI = new ItemListaAPI();
                    itemListaAPI.setImagenFirebase(direccionFirebase);
                    itemListaAPI.setVendedor(FirebaseAuth.getInstance().getUid());
                    itemListaAPI.setPrice(precio);
                    itemListaAPI.setTitle(titulo);
                    itemListaAPI.setDescripcion(descripcion);
                    DAOFirebase.get().guardarNuevo(itemListaAPI);
                }
            }
        });

        DAOFirebase.get().getItemPublicado().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if ( s != null ) {
                    Log.d(TAG, "Se subio el item a Firebase");
                    publicando = false;
                    // TODO: Veria ir otro fragment
                }
            }
        });
        return binding.getRoot();
    }

    private void escucharBotonPublicar() {
        binding.buttonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( publicando ){
                    return;
                }
                if (imagenPublicacion == null) {
                    Snackbar.make(binding.getRoot(), "Es necesario agregar una imagen", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                titulo = binding.titulo.getEditText().getText().toString();
                if (titulo.length() < 3) {
                    Snackbar.make(binding.getRoot(), "Titulo muy corto", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                precio = binding.precio.getEditText().getText().toString();
                if (precio.length() == 0) {
                    Snackbar.make(binding.getRoot(), "Es necesario un precio", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                descripcion = binding.descripcion.getEditText().getText().toString();
                if ( descripcion.length() < 3 ){
                    Snackbar.make(binding.getRoot(), "Descripcion muy corta", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                publicando = true;
                publicar();
            }
        });
    }

    private void publicar() {
        DAOFirebase.get().guardarNuevo(comprimir_imagen(imagenPublicacion, 1280, 90));
    }

    private void escucharBotonBuscarImagen() {
        binding.buscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(FragmentPublicar.this, "Elige una foto", 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                imagenPublicacion = BitmapFactory.decodeFile(imageFiles.get(0).getAbsolutePath());
                binding.imagenProducto.setImageBitmap(imagenPublicacion);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                Log.d(TAG, "Cancelo el usuario");
            }
        });
    }

    public byte[] comprimir_imagen(Bitmap bitmap, int anchoMaximo, int calidad) {
        Bitmap scaledBitmap = null;
        int width = bitmap.getWidth();
        if (width > anchoMaximo) {
            float escala = (float) bitmap.getWidth() / (float) anchoMaximo;
            int alto = (int) (bitmap.getHeight() / escala);
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, anchoMaximo, alto, true);
        } else {
            scaledBitmap = bitmap;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, calidad, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
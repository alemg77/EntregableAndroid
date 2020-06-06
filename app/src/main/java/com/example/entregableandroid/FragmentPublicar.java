package com.example.entregableandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.entregableandroid.databinding.FragmentPublicarBinding;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import io.grpc.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class FragmentPublicar extends Fragment {

    private String TAG = getClass().toString();
    private FragmentPublicarBinding binding;


    public FragmentPublicar() {   }

    public static FragmentPublicar newInstance(String param1, String param2) {
        FragmentPublicar fragment = new FragmentPublicar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPublicarBinding.inflate(getLayoutInflater());

        DAOFirebase.get().getProgreso().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.indeterminateBar.setProgress(integer);
            }
        });

        binding.buscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(FragmentPublicar.this, "Elige una foto", 1);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.get(0).getAbsolutePath());
                binding.imagenProducto.setImageBitmap(bitmap);
                DAOFirebase.get().guardarNuevo(comprimir_imagen(bitmap, 1280, 90));
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                Log.d(TAG,"Cancelo el usuario");
            }
        });
    }

    public byte[] comprimir_imagen (Bitmap bitmap, int anchoMaximo, int calidad){
        Bitmap scaledBitmap = null;
        int width = bitmap.getWidth();
        if ( width > anchoMaximo ) {
            float escala = (float)bitmap.getWidth()/(float)anchoMaximo;
            int alto = (int) (bitmap.getHeight()/escala);
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, anchoMaximo, alto, true);
        }
        else {
            scaledBitmap = bitmap;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, calidad, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
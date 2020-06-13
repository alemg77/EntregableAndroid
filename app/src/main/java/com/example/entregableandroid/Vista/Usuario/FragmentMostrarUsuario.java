package com.example.entregableandroid.Vista.Usuario;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentMostrarUsuarioBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 *
 * */

public class FragmentMostrarUsuario extends Fragment {

    FragmentMostrarUsuarioBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,     Bundle savedInstanceState) {
        binding = FragmentMostrarUsuarioBinding.inflate(inflater, container, false);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.usuarioMail.setText(currentUser.getDisplayName());
        binding.usuarioMail.setText(currentUser.getEmail());
        Uri photoUrl = currentUser.getPhotoUrl();
        Glide.with(binding.getRoot()).load(photoUrl).into(binding.imgenUsuario);

        return binding.getRoot();
    }
}
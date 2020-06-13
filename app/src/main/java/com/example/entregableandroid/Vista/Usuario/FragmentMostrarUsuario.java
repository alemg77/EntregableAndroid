package com.example.entregableandroid.Vista.Usuario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentMostrarUsuarioBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 */

public class FragmentMostrarUsuario extends Fragment {

    FragmentMostrarUsuarioBinding binding;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMostrarUsuarioBinding.inflate(inflater, container, false);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        binding.usuarioMail.setText(currentUser.getDisplayName());
        binding.usuarioMail.setText(currentUser.getEmail());
        Uri photoUrl = currentUser.getPhotoUrl();
        Glide.with(binding.getRoot()).load(photoUrl).into(binding.imagenUsuarioLogeado);

        binding.botonSalirFirebase.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Snackbar.make(binding.getRoot(), "Deslogeado correctamente", BaseTransientBottomBar.LENGTH_LONG).show();
                        FragmentManager supportFragmentManager1 = getActivity().getSupportFragmentManager();
                        supportFragmentManager1.popBackStack();
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( binding!= null ) {
            binding = null;
        }
    }


}
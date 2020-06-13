package com.example.entregableandroid.Vista.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.ActivityUsuarioBinding;

public class ActivityUsuario extends AppCompatActivity {

    ActivityUsuarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
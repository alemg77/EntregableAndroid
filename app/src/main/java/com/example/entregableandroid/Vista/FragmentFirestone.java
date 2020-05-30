package com.example.entregableandroid.Vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentFirestoneBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FragmentFirestone extends Fragment {

    FragmentFirestoneBinding binding;

    private FirebaseFirestore db;

    public FragmentFirestone() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", "Ale");
        user.put("Apelligo", "G");
        user.put("born", 1978);
        db.collection("UNO").add(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirestoneBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
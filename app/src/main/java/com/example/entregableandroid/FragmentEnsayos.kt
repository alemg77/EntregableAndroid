package com.example.entregableandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.entregableandroid.Controlador.Firebase.DAOFirebase
import com.example.entregableandroid.databinding.FragmentEnsayosBinding
import com.google.firebase.storage.FirebaseStorage


class FragmentEnsayos : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        binding = FragmentEnsayosBinding.inflate(inflater, container, false)
        Log.d(TAG, "Inicio del Fragment ensayo")
        binding!!.textdummy.setText("Arranco Kotlin!!")

        val storageReference = FirebaseStorage.getInstance().reference.child("xQe0yehNXthSwjNwZC9Dit1LCUr1Sat Jun 06 17:34:18 GMT-03:00 2020")
        Glide.with(binding!!.root).load(storageReference).into(binding!!.imagen1)

        return binding!!.root
    }

    companion object {
       var binding: FragmentEnsayosBinding? = null
        val TAG = javaClass.toString()
    }
}
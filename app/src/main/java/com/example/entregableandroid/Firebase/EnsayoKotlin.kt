package com.example.entregableandroid.Firebase


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.entregableandroid.Modelo.ApiML.ItemAPI
import com.example.entregableandroid.databinding.FragmentEnsayoKotlinBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EnsayoKotlin(val nombreColeccion: String) : Fragment() {

    private lateinit var refdb: CollectionReference

    companion object {
        var TAG = javaClass.toString()
        var _binding: FragmentEnsayoKotlinBinding? = null
        val binding get() = _binding!!
        val usuarioFirebase = FirebaseAuth.getInstance().uid
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEnsayoKotlinBinding.inflate(inflater, container, false)
        refdb = Firebase.firestore.collection(nombreColeccion)
        escucharBotonLeer()
        escucharBotonEscribir()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun escucharBotonLeer() {
        binding.Leer.setOnClickListener(View.OnClickListener {
            leerFirebase()
        });
    }

    private fun escucharBotonEscribir() {
        binding.botonFirestore.setOnClickListener(View.OnClickListener {
            //val itemAPI = ItemAPI("007", "Placa de video Garlompa", "Yo", "15")
            //guardarNuevoItem(itemAPI)
        })
    }

    fun guardarFirebase( documento: String, data: ItemAPI) {
        refdb.document(documento)
                .set(data)
                .addOnSuccessListener { Log.d(TAG, "Documento agregado")
                }
                .addOnFailureListener { e -> Log.w(TAG, "No se agregro el documento", e)
                }
    }

    fun guardarNuevoItem(data: ItemAPI) {
        refdb.document().set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "Documento agregado")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "No se agregro el documento", e)
                }
    }

    private fun leerFirebase() {
        refdb.get()
                .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { queryDocumentSnapshots ->
                    for (queryDocumentSnapshot in queryDocumentSnapshots) {
                        val itemAPI = queryDocumentSnapshot.toObject(ItemAPI::class.java)
                        Log.d(ContentValues.TAG, "Leimos un documento")
                    }
                })
                .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Fallo en la lectura de firebase: $e") })
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { Log.d(TAG, "Fin de la lectura de Firebase") })
    }



}




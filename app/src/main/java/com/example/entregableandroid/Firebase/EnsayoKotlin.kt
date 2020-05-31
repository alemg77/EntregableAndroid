package com.example.entregableandroid.Firebase


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.entregableandroid.Modelo.ApiML.ItemAPI
import com.example.entregableandroid.databinding.FragmentEnsayoKotlinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EnsayoKotlin(val nombreColeccion: String) : Fragment() {

    private val ITEMS_VENTA = "Items a la venta"
    private lateinit var refdb: CollectionReference

    companion object {
        val db = Firebase.firestore
        var TAG = javaClass.toString()
        var _binding: FragmentEnsayoKotlinBinding? = null
        val binding get() = _binding!!
        val usuarioFirebase = FirebaseAuth.getInstance().uid

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEnsayoKotlinBinding.inflate(inflater, container, false)
        refdb = db.collection(nombreColeccion)
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
      //      leerFirebase(ITEMS_VENTA)
        });
    }

    private fun escucharBotonEscribir() {
        binding.botonFirestore.setOnClickListener(View.OnClickListener {
            val itemAPI = ItemAPI("007", "Placa de video Garlompa", "Yo", "15")
            guardarNuevoItem(itemAPI)
        })
    }

    fun guardarFirebase(coleccion: String, documento: String, data: ItemAPI) {
        if (usuarioFirebase == null) {
            return
        }
        db.collection(nombreColeccion)
                .document(usuarioFirebase)
                .collection(coleccion)
                .document(documento)                                 //
                .set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "Documento agregado")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "No se agregro el documento", e)
                }
    }

    fun guardarNuevoItem(data: ItemAPI) {
        if (usuarioFirebase == null)
            return
        refdb.document().set(data)
                .addOnSuccessListener {
                    Log.d(TAG, "Documento agregado")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "No se agregro el documento", e)
                }
    }

    /*
    fun leerFirebase(coleccion: String) {
        if (usuarioFirebase == null) {
            return
        }
        refdb
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "No pudimos leer")
                }
    }

     */
}




package com.example.entregableandroid.Vista.FragmentListaItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.entregableandroid.R
import com.example.entregableandroid.databinding.FragmentBusquedaFirebaseBinding
import com.example.entregableandroid.databinding.FragmentEnsayoKotlinBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentBusquedaFirebase.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentBusquedaFirebase : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBusquedaFirebaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        var TAG = javaClass.toString()
        var _binding: FragmentBusquedaFirebaseBinding? = null
        val binding get() = _binding!!
    }
}
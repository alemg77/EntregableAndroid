package com.example.entregableandroid.FragmentDetalleProducto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entregableandroid.ApiML.ItemVenta;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.FragmentProductos.FragmentListaProductos;
import com.example.entregableandroid.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;


public class FragmentDetalleProducto extends Fragment {

    private TextView textViewTitulo,textViewPrecio;
    private ImageView imageView;
    private ImageView imageViewMapa;
    private ItemVenta itemVenta;
    private String TAG = getClass().toString();
    private FragmentDetalleProducto.Aviso listener;

    public FragmentDetalleProducto() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (FragmentDetalleProducto.Aviso) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Metodo onCreate");
        Bundle arguments = getArguments();
        itemVenta = (ItemVenta) arguments.getSerializable(ItemVenta.class.toString());
        Log.d(TAG, itemVenta.getTitulo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Metodo onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        textViewTitulo = inflate.findViewById(R.id.FragmentDetalleProductoTitulo);
        textViewPrecio = inflate.findViewById(R.id.FragmentDetalleProductoPrecio);
        imageViewMapa = inflate.findViewById(R.id.FragmentDetalleImagenMapa);


        if ( itemVenta.getLatitud() == null ){
            imageViewMapa.setVisibility(View.GONE);
        }
        else {
            imageViewMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.FragmentDetalleAviso( new LatLng(itemVenta.getLatitud(),itemVenta.getLongitud()));
                }
            });
        }

        textViewPrecio.setText("$"+itemVenta.getPrice());
        imageView = inflate.findViewById(R.id.FragmentDetalleProductoImagen);
        textViewTitulo.setText(itemVenta.getTitulo());
        Picasso.get().load(itemVenta.getUrlImagen()).into(imageView);
        return inflate;
    }

    public interface Aviso {
        void FragmentDetalleAviso (Object object);
    }
}

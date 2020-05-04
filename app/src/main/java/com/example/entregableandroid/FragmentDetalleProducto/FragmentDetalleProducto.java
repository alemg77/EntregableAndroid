package com.example.entregableandroid.FragmentDetalleProducto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entregableandroid.ApiML.ItemVenta;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.R;
import com.squareup.picasso.Picasso;


public class FragmentDetalleProducto extends Fragment {

    private TextView textViewTitulo,textViewPrecio;
    private ImageView imageView;
    private ItemVenta itemVenta;
    private String TAG = getClass().toString();

    public FragmentDetalleProducto() {
        // Required empty public constructor
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
        textViewPrecio.setText("$"+itemVenta.getPrice());
        imageView = inflate.findViewById(R.id.FragmentDetalleProductoImagen);
        textViewTitulo.setText(itemVenta.getTitulo());
        Picasso.get().load(itemVenta.getUrlImagen()).into(imageView);
        return inflate;
    }
}

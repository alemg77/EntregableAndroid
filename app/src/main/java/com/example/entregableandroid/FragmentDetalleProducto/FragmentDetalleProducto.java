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

import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ItemLocationAPI;
import com.example.entregableandroid.Modelo.ItemVenta;
import com.example.entregableandroid.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;


public class FragmentDetalleProducto extends Fragment {

    private TextView textViewTitulo,textViewPrecio;
    private ImageView imageView;
    private ImageView imageViewMapa;
    private ItemAPI itemAPI;
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
        itemAPI = (ItemAPI) arguments.getSerializable(ItemAPI.class.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Metodo onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        textViewTitulo = inflate.findViewById(R.id.FragmentDetalleProductoTitulo);
        textViewPrecio = inflate.findViewById(R.id.FragmentDetalleProductoPrecio);
        imageViewMapa = inflate.findViewById(R.id.FragmentDetalleImagenMapa);

        final ItemLocationAPI location = itemAPI.getLocation();
        if ( location.getLatitude() == null ){
            imageViewMapa.setVisibility(View.GONE);
        }
        else {
            imageViewMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    listener.FragmentDetalleAviso( new LatLng(latitude,longitude));
                }
            });
        }

        textViewPrecio.setText("$"+itemAPI.getPrice());
        imageView = inflate.findViewById(R.id.FragmentDetalleProductoImagen);
        textViewTitulo.setText(itemAPI.getTitle());
        Picasso.get().load(itemAPI.getPictures().get(0).getUrl()).into(imageView);
        return inflate;
    }

    public interface Aviso {
        void FragmentDetalleAviso (Object object);
    }
}

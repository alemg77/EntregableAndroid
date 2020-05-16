package com.example.entregableandroid.Vista.FragmentDetalleProducto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.Imagen;
import com.example.entregableandroid.Modelo.ApiML.ItemLocationAPI;
import com.example.entregableandroid.Modelo.ApiML.ListaImagenes;
import com.example.entregableandroid.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FragmentDetalleProducto extends Fragment {

    private TextView textViewTitulo,textViewPrecio;
    private ImageView imageViewMapa;
    private ItemAPI itemAPI;
    private String TAG = getClass().toString();
    private FragmentDetalleProducto.Aviso listener;
    private ListaImagenes listaImagenes;
    private ViewPager viewPager;

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
        listaImagenes = new ListaImagenes(itemAPI.getPictures());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Metodo onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        textViewTitulo = inflate.findViewById(R.id.FragmentDetalleProductoTitulo);
        textViewPrecio = inflate.findViewById(R.id.FragmentDetalleProductoPrecio);

        viewPager = inflate.findViewById(R.id.FragmentDetalleViewPager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        List<Imagen> pictures = listaImagenes.getPictures();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),pictures);
        viewPager.setAdapter(viewPagerAdapter);

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
        // imageView = inflate.findViewById(R.id.FragmentDetalleProductoImagen);
        textViewTitulo.setText(itemAPI.getTitle());
        return inflate;
    }

    public interface Aviso {
        void FragmentDetalleAviso (Object object);
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}

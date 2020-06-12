package com.example.entregableandroid.Vista.DetalleProducto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entregableandroid.Controlador.ApiML.DaoApiML;
import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Controlador.ItemViewModel;
import com.example.entregableandroid.Modelo.ApiML.DescripcionItem;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.Imagen;
import com.example.entregableandroid.Modelo.ApiML.ItemLocationAPI;
import com.example.entregableandroid.Modelo.ApiML.ListaImagenes;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;
import com.example.entregableandroid.databinding.FragmentDetalleProductoBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class FragmentDetalleProducto extends Fragment {

    FragmentDetalleProductoBinding binding;

    private ItemAPI itemAPI;
    private String TAG = getClass().toString();
    private FragmentDetalleProducto.Aviso listener;
    private ListaImagenes listaImagenes;
//    private NuevaLista lista;

    public FragmentDetalleProducto() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.FragmentDetalleProductoPrecio.setText("$" + itemAPI.getPrice());
        binding.FragmentDetalleProductoTitulo.setText(itemAPI.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Metodo onCreateView");
        binding = FragmentDetalleProductoBinding.inflate(getLayoutInflater());

        DaoApiML apiMLDao = new ViewModelProvider(this).get(DaoApiML.class);
        apiMLDao.buscarDescripcionItemm(itemAPI.getId());
        final Observer<List<DescripcionItem>> observadorDescripcion = new Observer<List<DescripcionItem>>() {
            @Override
            public void onChanged(List<DescripcionItem> descripcionItems) {
                DescripcionItem descripcionItem = descripcionItems.get(0);
                binding.FragmentDetalleDescripcion.setText(descripcionItem.getPlain_text());
            }
        };
        apiMLDao.getDescripcionItem().observe(getViewLifecycleOwner(), observadorDescripcion);

        binding.FragmentDetalleViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        List<Imagen> pictures = listaImagenes.getPictures();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), pictures);
        binding.FragmentDetalleViewPager.setAdapter(viewPagerAdapter);

        final ItemLocationAPI location = itemAPI.getLocation();
        if (location.getLatitude() == null) {
            binding.FragmentDetalleImagenMapa.setVisibility(View.GONE);
        } else {
            binding.FragmentDetalleImagenMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    listener.mostrarMapa(new LatLng(latitude, longitude));
                }
            });
        }

        final Observer<ResultadoBusqueda> analizarBusqueda = new Observer<ResultadoBusqueda>() {
            @Override
            public void onChanged(ResultadoBusqueda resultadoBusqueda_) {
                if ( !resultadoBusqueda_.getOrigen().equals(ResultadoBusqueda.BUSQUEDA_VIEJA )) {
                    listener.nuevaListaItems();
                }
            }
        };
        ItemViewModel.getInstancia(this).getResultadoBusquedaDB().observe(getViewLifecycleOwner(),analizarBusqueda);
        DAOFirebase.get().getListaItems().observe(getViewLifecycleOwner(), analizarBusqueda);
        DaoApiML.getInstancia(getActivity()).getResultadoBusquedaAPI().observe(getViewLifecycleOwner(),analizarBusqueda);

        return binding.getRoot();
    }

    public interface Aviso {
        // Le pide a la MainActivity que pegue una actividad con Google Map
        void mostrarMapa(LatLng coordenadas);
        // Le pide a la MainActivity que muestre la lista que llego
        void nuevaListaItems();
    }

    // Genera el efecto de transicion entre las imagenes de los Items
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

package com.example.entregableandroid.Vista.DetalleProducto;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.entregableandroid.Modelo.ApiML.Imagen;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentImagen> fragmentList;

    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Imagen> itemImagenAPIS) {
        super(fm);
        fragmentList = new ArrayList<>();
        for (Imagen itemImagenAPI : itemImagenAPIS) {
            FragmentImagen fragmentImagen = FragmentImagen.dameUnFragment(itemImagenAPI);
            fragmentList.add(fragmentImagen);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size() ;
    }
}

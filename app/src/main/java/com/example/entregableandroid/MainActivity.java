package com.example.entregableandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.entregableandroid.Fragments.FragTipoProducto;
import com.example.entregableandroid.Fragments.FragmentProducto;
import com.example.entregableandroid.RecyclerView.Producto;
import com.example.entregableandroid.RecyclerView.TipoDeProducto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AvisosActivity {

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragmentAPegar);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pegarFragment(new FragTipoProducto(new TipoDeProducto().cargarTiposDeProductos()), R.id.MainFragTipoProducto);
        pegarFragment(new FragmentProducto(new Producto().cargarMesas()), R.id.MainFragProductos);
    }

    @Override
    public void pulsaronElemento(Producto unProducto) {
        Toast.makeText(this, unProducto.getDescripcion(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void seleccionTipoProducto(TipoDeProducto unTipoDeProducto) {
        // Elimino el fragment que ya estaba, porque no quiero regresar a el.
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.MainFragProductos)).commit();
        if (unTipoDeProducto.getNombre().equals("Silla")) {
            pegarFragment(new FragmentProducto(new Producto().cargarSillas()), R.id.MainFragProductos);
        } else if (unTipoDeProducto.getNombre().equals("Mesa")) {
            pegarFragment(new FragmentProducto(new Producto().cargarMesas()), R.id.MainFragProductos);
        }
    }
}

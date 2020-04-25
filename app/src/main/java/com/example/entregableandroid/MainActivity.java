package com.example.entregableandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AvisosMainActivity {

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragmentAPegar);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pegarFragment(new FragTipoProducto(cargarTiposDeProductos(), this), R.id.MainFragTipoProducto);
        pegarFragment(new FragmentProducto(cargarSillas(), this), R.id.MainFragProductos);
    }

    @Override
    public void pulsaronElemento(Producto unProducto) {
        Toast.makeText(this, unProducto.getDescripcion(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void seleccionTipoProducto(TipoDeProducto unTipoDeProducto) {
        Toast.makeText(this, unTipoDeProducto.getNombre(), Toast.LENGTH_SHORT).show();
    }


    private List<Producto> cargarSillas() {
        List<Producto> listaDeSillas = new ArrayList<>();
        listaDeSillas.add(new Producto("Banqueta", "Blanca, regulable en altura", 7200.0, 10, R.drawable.banqueta7200));
        listaDeSillas.add(new Producto("Sillon Ejecutivo", "Clasico, negro", 6800.0, 11, R.drawable.sillon_ejecutivo_6800));
        listaDeSillas.add(new Producto("Sillon Gerencial", "Para el generente", 12000.0, 12, R.drawable.sillon_gerencial));
        listaDeSillas.add(new Producto("Sillas comedor", "Clasicas, blancas", 7500.0, 13, R.drawable.sillas_comedorx4_7500));
        listaDeSillas.add(new Producto("Sillon Gerencial Alto", "Para el generente", 21000.0, 14, R.drawable.sillon_gerencial_alto));
        return listaDeSillas;
    }

    private List<TipoDeProducto> cargarTiposDeProductos() {
        List<TipoDeProducto> listaDeProductos = new ArrayList<>();
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        listaDeProductos.add(new TipoDeProducto("Silla", 50));
        listaDeProductos.add(new TipoDeProducto("Mesa", 51));
        return listaDeProductos;
    }


}

package com.example.entregableandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SillaAdapterListener{

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.MainRecyclerView);
        List<Silla> listaDeSillas = cargarSillas();

        SillaAdapter sillaAdapter = new SillaAdapter(listaDeSillas,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager( this, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sillaAdapter);

    }

    private List<Silla> cargarSillas (){
        List<Silla> listaDeSillas = new ArrayList<>();
        listaDeSillas.add(new Silla("Banqueta", "Blanca, regulable en altura", 7200.0, 10, R.drawable.banqueta7200));
        listaDeSillas.add(new Silla("Sillon Ejecutivo", "Clasico, negro", 6800.0, 11, R.drawable.sillon_ejecutivo_6800));
        listaDeSillas.add(new Silla( "Sillon Gerencial", "Para el generente", 12000.0, 12, R.drawable.sillon_gerencial));
        listaDeSillas.add(new Silla( "Sillas comedor", "Clasicas, blancas", 7500.0, 13, R.drawable.sillas_comedorx4_7500));
        listaDeSillas.add(new Silla( "Sillon Gerencial Alto", "Para el generente", 21000.0, 14, R.drawable.sillon_gerencial_alto));
        return listaDeSillas;
    }

    @Override
    public void pulsaronElemento(Silla unaSilla) {
        Toast.makeText(this, unaSilla.getDescripcion(), Toast.LENGTH_SHORT).show();
    }
}

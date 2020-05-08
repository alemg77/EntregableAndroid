package com.example.entregableandroid;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.entregableandroid.ApiML.ApiMercadoLibre;
import com.example.entregableandroid.ApiML.ElementoLista;
import com.example.entregableandroid.ApiML.ItemVenta;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.ApiML.RecepcionApiMercadoLibre;
import com.example.entregableandroid.Database.AppDatabase;
import com.example.entregableandroid.FragmentDetalleProducto.FragmentDetalleProducto;
import com.example.entregableandroid.FragmentProductos.FragmentListaProductos;
import com.example.entregableandroid.ROOM.Constantes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentListaProductos.Aviso, FragmentDetalleProducto.Aviso, RecepcionApiMercadoLibre {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String TAG = getClass().toString();
    private ApiMercadoLibre apiMercadoLibre;
    private AppDatabase db;

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(containerViewId, fragmentAPegar).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "********* INICIO DE LA APLICACION Entregable Android **********************************");
        Configuration configuracion = getResources().getConfiguration();
        int currentNightMode = configuracion.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                Log.d(TAG, " ************************************ MODO DIA *******************************");
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                Log.d(TAG, " ************************************ MODO NOCHE *******************************");
                break;
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();

        View actionView = navigationView.getMenu().findItem(R.id.menuSwich).getActionView();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (checked) {
                    Log.d(TAG, "Swith del navigation menu activado");
                } else {
                    Log.d(TAG, "Swith del navigation menu apagado");
                }
            }
        });

        apiMercadoLibre = new ApiMercadoLibre(this);
        apiMercadoLibre.buscarPorDescripcion("fiat");

    }

    @Override
    public void selleccionProducto(ElementoLista elementoLista) {
        Log.d(TAG, "El usuario seleciono un elemento");
        try {
            long insert = db.elementoListaDao().insert(elementoLista);
            if (insert > 0) {
                Log.d(TAG, "Insercion en la base de datos correcto!!");
                if ( insert > 4 ) {
                    ElementoLista elementoLista1 = db.elementoListaDao().getPrimerElemento();
                    db.elementoListaDao().deleteById(elementoLista1.getId());
                    // TODO: Si hay muchos elementos deberia borrar el mas viejo.
                }
            } else {
                Log.d(TAG, "Error en la insercion en la base de datos!!");
            }
        } catch ( Exception e ) {
            if ( e instanceof SQLiteConstraintException ) {
                Log.d(TAG, "No se agrego el elemento porque ya estaba");
            } else {
                Log.d(TAG, "Ocurrio la excepcion: " + e.toString());
            }
        }
        apiMercadoLibre.buscarPorId(elementoLista.getId());
    }

    @Override
    public void recepcionMLlistaVenta(ListaDeVentasML listaDeVentasML) {
        Log.d(TAG, "Llego una lista de elementos de la API Mercadolibre");
        pegarFragment(new FragmentListaProductos(), R.id.MainFragProductos, listaDeVentasML);
    }

    @Override
    public void recepcionMLitemVenta(ItemVenta itemVenta) {
        Log.d(TAG, "Llego un item a la venta desde la Api de Mercado Libre");
        pegarFragment(new FragmentDetalleProducto(), R.id.MainFragProductos, itemVenta);
    }

    @Override
    public void errorPedidoApiMercadolibre() {
        Log.d(TAG, "****** ERROR EN LA COMUNICACION CON LA API DE MERCADOLIBRE ************");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAudi:
                apiMercadoLibre.buscarPorDescripcion("audi");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuBMW:
                apiMercadoLibre.buscarPorDescripcion("bmw");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuFiat:
                apiMercadoLibre.buscarPorDescripcion("fiat");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuPeugeot:
                apiMercadoLibre.buscarPorDescripcion("Peugeot");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuRenault:
                apiMercadoLibre.buscarPorDescripcion("Renault");
                drawerLayout.closeDrawers();
                break;

            case R.id.Recientes:
                ArrayList<ElementoLista> arrayList = (ArrayList<ElementoLista>) db.elementoListaDao().getTodos();
                pegarFragment(new FragmentListaProductos(), R.id.MainFragProductos,  new ListaDeVentasML(arrayList));
                break;

            default:
                Toast.makeText(MainActivity.this, "En construccion", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void FragmentDetalleAviso(Object object) {
        if (object instanceof LatLng) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra(LatLng.class.toString(), (LatLng) object);
            startActivity(intent);
        }
    }
}

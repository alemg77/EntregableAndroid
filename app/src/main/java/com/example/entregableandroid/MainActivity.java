package com.example.entregableandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.entregableandroid.ApiML.ApiMercadoLibre;
import com.example.entregableandroid.ApiML.ElementoLista;
import com.example.entregableandroid.ApiML.ItemVenta;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.ApiML.RecepcionApiMercadoLibre;
import com.example.entregableandroid.FragmentDetalleProducto.FragmentDetalleProducto;
import com.example.entregableandroid.FragmentProductos.FragmentListaProductos;
import com.example.entregableandroid.FragmentProductos.Producto;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentListaProductos.Aviso, FragmentDetalleProducto.Aviso, RecepcionApiMercadoLibre {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String TAG = getClass().toString();
    private ApiMercadoLibre apiMercadoLibre;

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


            default:
                Toast.makeText(MainActivity.this, "En construccion", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void FragmentDetalleAviso(Object object) {
        if (object instanceof LatLng) {
            LatLng coordenadas = (LatLng) object;
//            Toast.makeText(this, "Latitud:" + coordenadas.latitude + "\nLongitud:" + coordenadas.longitude, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra(LatLng.class.toString(), coordenadas);
            startActivity(intent);
        }
    }
}

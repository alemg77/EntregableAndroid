package com.example.entregableandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.entregableandroid.ApiML.ApiMercadoLibre;
import com.example.entregableandroid.ApiML.ItemVenta;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.ApiML.RecepcionApiMercadoLibre;
import com.example.entregableandroid.FragmentProductos.FragmentListaProductos;
import com.example.entregableandroid.FragmentProductos.Producto;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentListaProductos.FragmentProductoListener,
        RecepcionApiMercadoLibre {

    // TODO:  EL fragment de seleccion de precio deberia ser un menu desplegable

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardView navigationViewCardView;
    private String TAG = getClass().toString();


    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragmentAPegar).commit();
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

        ApiMercadoLibre apiMercadoLibre = new ApiMercadoLibre(this);
        apiMercadoLibre.buscarPorDescripcion("fiat");


        /*
        // FIXME: Para Ver mas adelante
        View headerView = navigationView.getHeaderView(0);
        navigationViewCardView = headerView.findViewById(R.id.navHeaderCardView);
        navigationViewCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

    }

    @Override
    public void selleccionProducto(Producto producto) {
        Toast.makeText(this, producto.getDescripcion(), Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuInicio:
                Toast.makeText(MainActivity.this, "Presionaron inicio", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;

            case R.id.menuFavorito:
                drawerLayout.closeDrawers();
                break;
            default:
                Toast.makeText(MainActivity.this, "En construccion", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    @Override
    public void recepcionMLlistaVenta(ListaDeVentasML listaDeVentasML) {
        Log.d(TAG, "Llego una lista de elementos de la API Mercadolibre");
        pegarFragment(new FragmentListaProductos(), R.id.MainFragProductos, listaDeVentasML);
    }

    @Override
    public void recepcionMLitemVenta(ItemVenta itemVenta) {

    }

    @Override
    public void errorPedidoApiMercadolibre() {

    }
}

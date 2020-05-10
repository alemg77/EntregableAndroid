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

import com.example.entregableandroid.DAO.ApiML.ConstantesML;
import com.example.entregableandroid.FragmentProductos.FragmentResultadoBusqueda;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;
import com.example.entregableandroid.DAO.BaseDeDatos.AppDatabase;
import com.example.entregableandroid.FragmentDetalleProducto.FragmentDetalleProducto;
import com.example.entregableandroid.DAO.BaseDeDatos.Constantes;
import com.example.entregableandroid.DAO.ApiML.ApiMLDao;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentResultadoBusqueda.Aviso, FragmentDetalleProducto.Aviso, ApiMLDao.Avisos {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String TAG = getClass().toString();
    private AppDatabase db;
    private ApiMLDao apiMLDao;

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

        apiMLDao = new ApiMLDao(this);
        apiMLDao.setProvincia(ConstantesML.BUENOS_AIRES);
        apiMLDao.buscarPorDescripcion("fiat");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAudi:
                apiMLDao.buscarPorDescripcion("audi");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuBMW:
                apiMLDao = new ApiMLDao(this);
                apiMLDao.buscarPorDescripcion("bmw");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuFiat:
                apiMLDao.buscarPorDescripcion("fiat");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuPeugeot:
                apiMLDao.buscarPorDescripcion("Peugeot");
                drawerLayout.closeDrawers();
                break;

            case R.id.menuRenault:
                apiMLDao.buscarPorDescripcion("Renault");
                drawerLayout.closeDrawers();
                break;

            case R.id.Recientes:
                if ( db.elementoListaDao().cantidadElementos() > 0 ){
                    pegarFragment(new FragmentResultadoBusqueda(), R.id.MainFragProductos, new ResultadoBusquedaAPI(db.elementoListaDao().getTodos()));
                } else {
                    Toast.makeText(MainActivity.this, "Cuando veas algun producto se iran guardando aqui automagicamente", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void resultadoBusqueda(ResultadoBusquedaAPI resultadoBusqueda) {
        pegarFragment(new FragmentResultadoBusqueda(), R.id.MainFragProductos, resultadoBusqueda);
    }

    @Override
    public void LlegoItem(ItemAPI itemML) {
        pegarFragment(new FragmentDetalleProducto(), R.id.MainFragProductos, itemML);
    }

    @Override
    public void selleccionProducto(ItemListaAPI itemListaAPI) {
        Log.d(TAG, "El usuario seleciono un elemento");
        try {
            long insert = db.elementoListaDao().insert(itemListaAPI);
            if (insert > 0) {
                Log.d(TAG, "Insercion en la base de datos correcto!!");
                if ( insert > 4 ) {
                    ItemListaAPI itemListaAPI1 = db.elementoListaDao().getPrimerElemento();
                    db.elementoListaDao().deleteById(itemListaAPI1.getId());
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
        apiMLDao.buscarItemPorId(itemListaAPI.getId());
    }

}

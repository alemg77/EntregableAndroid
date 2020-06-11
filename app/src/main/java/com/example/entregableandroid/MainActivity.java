package com.example.entregableandroid;

// TODO: VERIFICAR PORQUE NO VERIFICO CORRECTAMENTE SI ESTANA LOGEADO O NO EN FIREBASE
// TODO: PORQUE ME DEJO IR A PUBLICAR SI NO ESTABA CONECTADO CON FIREBASE ???

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.entregableandroid.Controlador.ApiML.DaoApiML;
import com.example.entregableandroid.Controlador.ApiML.ConstantesML;
import com.example.entregableandroid.Controlador.BaseDeDatos.AppDatabase;
import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Controlador.ItemViewModel;
import com.example.entregableandroid.Modelo.ApiML.Item;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;
import com.example.entregableandroid.Vista.FragmentDetalleProducto.FragmentDetalleProducto;
import com.example.entregableandroid.Vista.FragmentListaItems.FragmentMostrarBusqueda;
import com.example.entregableandroid.Vista.FragmentLogin;
import com.example.entregableandroid.Vista.MapsActivity;
import com.example.entregableandroid.databinding.ActivityMainBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentDetalleProducto.Aviso, FragmentMostrarBusqueda.Aviso
{
    private ActivityMainBinding binding;
    private String TAG = getClass().toString();
    private DaoApiML apiMLDao;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Log.d(TAG, "********* INICIO DE LA APLICACION Entregable Android **********************************");

        itemViewModel = ItemViewModel.getInstancia(this);

        itemViewModel.getResultadoBusquedaDB().observe(this, new Observer<ResultadoBusqueda>() {
            @Override
            public void onChanged(ResultadoBusqueda resultadoBusqueda) {
                Log.d(TAG, "Exito?");
            }
        });


        // Preparo el acceso a la API de Mercado Libre
        apiMLDao = DaoApiML.getInstancia(this);
        apiMLDao.setProvincia(ConstantesML.BUENOS_AIRES);
        apiMLDao.buscarPorDescripcion("fiat");

        // Preparo el ToolBAR
        Toolbar toolbar = binding.MainActivityToolbar.cabezeratool;
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.abrir_menu, R.string.cerrar_menu);

        // Preparo el menu de Navegacion
        binding.navigation.setNavigationItemSelectedListener(this);
        NavigationView navigation = binding.navigation;
        View actionView =  navigation.getMenu().findItem(R.id.menuSwich).getActionView();
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

        // ************************************* Escuchadores *************************************
        final Observer<ResultadoBusqueda> observadorResultadoBusqueda = new Observer<ResultadoBusqueda>() {
            @Override
            public void onChanged(ResultadoBusqueda resultadoBusquedaAPI) {
                pegarFragment(new FragmentMostrarBusqueda(), R.id.MainFragment, resultadoBusquedaAPI);
            }
        };
        apiMLDao.getResultadoBusquedaAPIMutableLiveData().observe(this, observadorResultadoBusqueda);

        //
        final Observer<ItemAPI> observadorItem = new Observer<ItemAPI>() {
            @Override
            public void onChanged(ItemAPI itemAPI) {
                pegarFragment(new FragmentDetalleProducto(), R.id.MainFragment, itemAPI);
            }
        };
        apiMLDao.getItemAPIMutableLiveData().observe(this, observadorItem);

        DAOFirebase.get().getListaItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> item) {
                ResultadoBusqueda busqueda = new ResultadoBusqueda(item, ResultadoBusqueda.BUSQUEDA_FIREBASE);
                pegarFragment(new FragmentMostrarBusqueda(), R.id.MainFragment, busqueda);
            }
        });

    }

    /**
     *  Atiende los pedidos de busqueda que se realizan en la Tool Bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.accion_bar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar aqui...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                apiMLDao.buscarPorDescripcion(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Esto se ejecuta cuando cambia el texto
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *  Atiende la seleccion en la Tool Bar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_usuario:
                pegarFragment(new FragmentLogin(), R.id.MainFragment );
                break;

            case R.id.action_ensayoFirebase:
                pegarFragment(new FragmentEnsayos(), R.id.MainFragment);
                break;

            case R.id.action_bar_publicar:
                if ( FirebaseAuth.getInstance() != null ) {
                    pegarFragment(new FragmentPublicar(), R.id.MainFragment);
                } else {
                    Toast.makeText(MainActivity.this, "Primero es necesario registrarse", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.misPublicaciones:
                if ( FirebaseAuth.getInstance() != null ) {
                    DAOFirebase.get().buscarMisPublicaciones();
                    binding.drawerLayout.closeDrawers();
                } else {
                    Toast.makeText(MainActivity.this, "Primero es necesario registrarse", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.menuAudi:
                apiMLDao.buscarPorDescripcion("audi");
                binding.drawerLayout.closeDrawers();
                break;

            case R.id.menuBMW:
                apiMLDao.buscarPorDescripcion("bmw");
                binding.drawerLayout.closeDrawers();
                break;

            case R.id.menuFiat:
                apiMLDao.buscarPorDescripcion("fiat");
                binding.drawerLayout.closeDrawers();
                break;

            case R.id.menuPeugeot:
                apiMLDao.buscarPorDescripcion("Peugeot");
                binding.drawerLayout.closeDrawers();
                break;

            case R.id.menuRenault:
                apiMLDao.buscarPorDescripcion("Renault");
                binding.drawerLayout.closeDrawers();
                break;

            case R.id.Recientes:
                if ( itemViewModel.cantidadDB() > 0 ){
                    ResultadoBusqueda busqueda = new ResultadoBusqueda(itemViewModel.getTodosDB(), ResultadoBusqueda.BUSQUEDA_DB_LOCAL);
                    pegarFragment(new FragmentMostrarBusqueda(), R.id.MainFragment, busqueda);
                    binding.drawerLayout.closeDrawers();
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
    public void mostrarMapa(LatLng coordenadas) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra(LatLng.class.toString(), coordenadas);
            startActivity(intent);
    }

    @Override
    public void selleccionProducto(Item item) {
        Log.d(TAG, "El usuario seleciono un elemento");
        itemViewModel.agregarDB(item);
        apiMLDao.buscarItemPorId(item.getId());
    }

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
    }

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
    }
}

/*
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
 */

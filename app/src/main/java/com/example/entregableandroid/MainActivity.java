package com.example.entregableandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.entregableandroid.Controlador.ApiML.DaoApiML;
import com.example.entregableandroid.Controlador.ApiML.ConstantesML;
import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Controlador.ItemViewModel;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Vista.DetalleProducto.FragmentDetalleProducto;
import com.example.entregableandroid.Vista.MostrarResultadoBusqueda.FragmentMostrarBusqueda;
import com.example.entregableandroid.Vista.Usuario.FragmentLogin;
import com.example.entregableandroid.Vista.FragmentPublicar;
import com.example.entregableandroid.Vista.MapsActivity;
import com.example.entregableandroid.Vista.Usuario.FragmentMostrarUsuario;
import com.example.entregableandroid.databinding.ActivityMainBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentDetalleProducto.Aviso, FragmentPublicar.Avisos {
    private ActivityMainBinding binding;
    private String TAG = getClass().toString();
    private DaoApiML apiMLDao;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String ultimoFragmentePegado;
    private static final String KEY_FRAGMENT_PEGADO = "ULTIMO fragment pegado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Log.d(TAG, "********* INICIO DE LA APLICACION Entregable Android **********************************");

        // Preparo la API de mercaolibre
        apiMLDao = DaoApiML.getInstancia(this);
        apiMLDao.setProvincia(ConstantesML.BUENOS_AIRES);

        // Si es una reconstruccion, nos llega informacion
        if (savedInstanceState != null) {
            ultimoFragmentePegado = savedInstanceState.getString(KEY_FRAGMENT_PEGADO);
        }

        // Si no hay ningun fragment pegado, pido
        if (ultimoFragmentePegado == null) {
            // Preparo el acceso a la API de Mercado Libre informacion
            apiMLDao.buscarPorDescripcion("fiat");
            pegarFragment(new FragmentMostrarBusqueda(), R.id.MainFragment);
        }

        // Preparo el ToolBAR
        Toolbar toolbar = binding.MainActivityToolbar.cabezeratool;
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.abrir_menu, R.string.cerrar_menu);

        // Preparo el menu de Navegacion
        binding.navigation.setNavigationItemSelectedListener(this);

        //
        final Observer<ItemAPI> observadorItem = new Observer<ItemAPI>() {
            @Override
            public void onChanged(ItemAPI itemAPI) {
                pegarFragment(new FragmentDetalleProducto(), R.id.MainFragment, itemAPI);
            }
        };
        apiMLDao.getItemAPIMutableLiveData().observe(this, observadorItem);

    }

    /**
     * Atiende los pedidos de busqueda que se realizan en la Tool Bar.
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
     * Atiende la seleccion en la Tool Bar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_usuario:
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if ( currentUser == null) {
                    pegarFragment(new FragmentLogin(), R.id.MainFragment);
                } else {
                    pegarFragment(new FragmentMostrarUsuario(), R.id.MainFragment);
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

    /**
     * Atiende los pedidos del menu lateral
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.misPublicaciones:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    DAOFirebase.get().buscarMisPublicaciones();
                    binding.drawerLayout.closeDrawers();
                } else {
                    Toast.makeText(MainActivity.this, "Primero es necesario registrarse", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.publicar:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    pegarFragment(new FragmentPublicar(), R.id.MainFragment);
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
                if (ItemViewModel.getInstancia(this).cantidadDB() > 0) {
                    ItemViewModel.getInstancia(this).getTodosDB();
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

    /**
     * Pasa a otra actividad para mostrar el mapa de google
     *
     * @param coordenadas: lugar a mostrar
     */
    @Override
    public void mostrarMapa(LatLng coordenadas) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra(LatLng.class.toString(), coordenadas);
        startActivity(intent);
    }

    @Override
    public void nuevaListaItems() {
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        if ( backStackEntryCount > 0) {
            supportFragmentManager.popBackStack();
        }
    }

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
        ultimoFragmentePegado = fragmentAPegar.getClass().toString();
    }

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
        ultimoFragmentePegado = fragmentAPegar.getClass().toString();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FRAGMENT_PEGADO, ultimoFragmentePegado);
    }

    @Override
    public void llegoUnaLista() {
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        for ( int i = 1 ; i < backStackEntryCount  ; i++){
            supportFragmentManager.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        if ( supportFragmentManager.getBackStackEntryCount() == 0 ){
            finish();
        }
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

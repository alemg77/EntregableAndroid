package com.example.entregableandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.entregableandroid.Fragments.FragTipoProducto;
import com.example.entregableandroid.Fragments.FragmentProducto;
import com.example.entregableandroid.RecyclerViewProducto.ListaDeProductos;
import com.example.entregableandroid.RecyclerViewTipoDeProducto.ListaDeTiposDeProductos;
import com.example.entregableandroid.RecyclerViewProducto.Producto;
import com.example.entregableandroid.RecyclerViewTipoDeProducto.TipoDeProducto;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragTipoProducto.FragTipoProductoListener, FragmentProducto.FragmentProductoListener {

    // TODO:  EL fragment de seleccion de precio deberia ser un menu desplegable

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardView navigationViewCardView;


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

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


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


        pegarFragment(new FragTipoProducto(),R.id.MainFragTipoProducto,new ListaDeTiposDeProductos().cargarTiposDeProductos());
        pegarFragment(new FragmentProducto(), R.id.MainFragProductos, new ListaDeProductos().Sillas());
    }

    @Override
    public void TipoProuctoSeleccionado(TipoDeProducto unTipoDeProducto) {
        // Elimino el fragment que ya estaba, porque no quiero regresar a el.
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.MainFragProductos)).commit();
        if (unTipoDeProducto.getNombre().equals("Sillas"))
            pegarFragment(new FragmentProducto(), R.id.MainFragProductos, new ListaDeProductos().Sillas());
        else if (unTipoDeProducto.getNombre().equals("Mesas")) {
            pegarFragment(new FragmentProducto(), R.id.MainFragProductos, new ListaDeProductos().Mesas());
        }
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
}

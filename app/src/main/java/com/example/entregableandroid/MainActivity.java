package com.example.entregableandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.entregableandroid.Fragments.FragTipoProducto;
import com.example.entregableandroid.Fragments.FragmentProducto;
import com.example.entregableandroid.RecyclerViewProducto.ListaDeProductos;
import com.example.entregableandroid.RecyclerViewTipoDeProducto.ListaDeTiposDeProductos;
import com.example.entregableandroid.RecyclerViewProducto.Producto;
import com.example.entregableandroid.RecyclerViewTipoDeProducto.TipoDeProducto;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements FragTipoProducto.FragTipoProductoListener, FragmentProducto.FragmentProductoListener {

    // TODO:  EL fragment de seleccion de precio deberia ser un menu desplegable

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
}

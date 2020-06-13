package com.example.entregableandroid.Vista.MostrarResultadoBusqueda;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.entregableandroid.Controlador.ApiML.DaoApiML;
import com.example.entregableandroid.Controlador.Firebase.DAOFirebase;
import com.example.entregableandroid.Controlador.ItemViewModel;
import com.example.entregableandroid.Modelo.ApiML.Item;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentRecyclerviewBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentMostrarBusqueda extends Fragment implements RecyclerViewClickInterfase {

    private FragmentRecyclerviewBinding binding;
    private ResultadoBusqueda ultimaBusqueda;
    private ProductoAdapter productoAdapter;
    private Item elementoBorrado;

    public FragmentMostrarBusqueda() {
    }

    // 1° en la creacion
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    // 2° En la creacion
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState != null) {
            ultimaBusqueda = (ResultadoBusqueda) savedInstanceState.getSerializable(ResultadoBusqueda.class.toString());
        }
    }

    @Override // 3° En la creacion
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecyclerviewBinding.inflate(getLayoutInflater());

        if (ultimaBusqueda == null) {
            ultimaBusqueda = new ResultadoBusqueda(new ArrayList<Item>());
        }

        Context context = getActivity().getApplicationContext();
        productoAdapter = new ProductoAdapter(context, this, ultimaBusqueda.getResults());
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager(getActivity());
        binding.RecyclerView.setLayoutManager(dosLayoutManager);
        binding.RecyclerView.setAdapter(productoAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.RecyclerView);

        final Observer<ResultadoBusqueda> analizarBusqueda = new Observer<ResultadoBusqueda>() {
            @Override
            public void onChanged(ResultadoBusqueda resultadoBusqueda) {
                // Si es la siguiente pagina, agrego
                if ( resultadoBusqueda.getPagina() > 0 ) {
                    ultimaBusqueda.agregarListaElementos(resultadoBusqueda.getResults());
                } else  {
                    ultimaBusqueda = resultadoBusqueda;
                }
                productoAdapter.setListadDeProductos(ultimaBusqueda.getResults());
            }
        };
        ItemViewModel.getInstancia(this).getResultadoBusquedaDB().observe(getViewLifecycleOwner(),analizarBusqueda);
        DAOFirebase.get().getListaItems().observe(getViewLifecycleOwner(), analizarBusqueda);
        DaoApiML.getInstancia(getActivity()).getResultadoBusquedaAPI().observe(getViewLifecycleOwner(),analizarBusqueda);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // Se va el fragment, guardamos para no perder
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ResultadoBusqueda.class.toString(), ultimaBusqueda);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromposition = viewHolder.getAdapterPosition();
            int toposition = target.getAdapterPosition();
            ultimaBusqueda.swapElementos(fromposition, toposition);
            recyclerView.getAdapter().notifyItemMoved(fromposition, toposition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int posicion = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:  // <----
                    ultimaBusqueda.eliminarElemento(posicion);
                    elementoBorrado = ultimaBusqueda.getResults().get(posicion);
                    productoAdapter.notifyItemRemoved(posicion);
                    Snackbar.make(binding.RecyclerView, "Regresar?", Snackbar.LENGTH_LONG)
                            .setAction("Si", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ultimaBusqueda.agregarElemento(posicion, elementoBorrado);
                                    productoAdapter.notifyItemInserted(posicion);
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT: // ---->
                    break;
            }
            Log.d(TAG, "Se desplazo elemento");
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(R.color.colorPrimaryDark)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                    .addActionIcon(R.drawable.ic_delete_sweep_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onItemClick(Item item) {
        ItemViewModel.getInstancia(this).agregarDB(item);
        DaoApiML.getInstancia(this).buscarItemPorId(item.getId());
    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(getContext(), "Toma por curioso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void necesitoMasElementos() {
        Log.d(TAG, "Estamos llegando al final de la lista");
        if ( ultimaBusqueda.getOrigen() == ResultadoBusqueda.BUSQUEDA_API ) {
            DaoApiML.getInstancia(this).masDeLaUltima();
        }
    }




}



package com.example.entregableandroid.Vista.FragmentListaItems;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;
import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentRecyclerviewBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentListaItems extends Fragment implements RecyclerViewClickInterfase {

    private FragmentRecyclerviewBinding binding;
    private FragmentListaItems.Aviso listener;
    private List<ItemListaAPI> listaElementos;
    private ProductoAdapter productoAdapter;
    private ItemListaAPI elementoBorrado;

    public FragmentListaItems(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Aviso) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecyclerviewBinding.inflate(getLayoutInflater());
        Bundle bundle = getArguments();
        ResultadoBusquedaAPI resultadoBusquedaAPI = (ResultadoBusquedaAPI) bundle.getSerializable(ResultadoBusquedaAPI.class.toString());
        listaElementos = resultadoBusquedaAPI.getResults();
        Context context = getActivity().getApplicationContext();
        productoAdapter = new ProductoAdapter(context, this, listaElementos);
        LinearLayoutManager dosLayoutManager = new LinearLayoutManager(getActivity());
        binding.RecyclerView.setLayoutManager(dosLayoutManager);
        binding.RecyclerView.setAdapter(productoAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.RecyclerView);
        return binding.getRoot();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START| ItemTouchHelper.END ,
            ItemTouchHelper.LEFT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromposition = viewHolder.getAdapterPosition();
            int toposition = target.getAdapterPosition();
            Collections.swap(listaElementos, fromposition, toposition);
            recyclerView.getAdapter().notifyItemMoved(fromposition, toposition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int posicion = viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:  // <----

                    elementoBorrado = listaElementos.get(posicion);
                    listaElementos.remove(posicion);
                    productoAdapter.notifyItemRemoved(posicion);
                    Snackbar.make(binding.RecyclerView, "Regresar?", Snackbar.LENGTH_LONG)
                            .setAction("Si", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    listaElementos.add(posicion, elementoBorrado);
                                    productoAdapter.notifyItemInserted(posicion);
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT: // ---->
                    break;
            }
            Log.d(TAG,"Se desplazo elemento");
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
    public void onItemClick(int position) {
        listener.selleccionProducto(listaElementos.get(position));
    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(getContext(), "Toma por curioso", Toast.LENGTH_SHORT).show();
    }

    public interface Aviso {
        void selleccionProducto (ItemListaAPI itemListaAPI);
    }
}



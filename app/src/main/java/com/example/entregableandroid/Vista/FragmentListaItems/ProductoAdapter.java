package com.example.entregableandroid.Vista.FragmentListaItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entregableandroid.Modelo.ApiML.ItemListaAPI;
import com.example.entregableandroid.databinding.CeldaListadoProductoBinding;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<ItemListaAPI> listadDeProductos;
    private RecyclerViewClickInterfase listener;
    private Context context;

    public ProductoAdapter(Context context, RecyclerViewClickInterfase listener, List<ItemListaAPI> listadDeProductos) {
        this.listadDeProductos = listadDeProductos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CeldaListadoProductoBinding celdaListadoProductoBinding = CeldaListadoProductoBinding.inflate(layoutInflater, parent, false);
        ProductoAdapter.ProductoViewHolder viewHolder = new ProductoAdapter.ProductoViewHolder(celdaListadoProductoBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductoAdapter.ProductoViewHolder sillaViewHolder = holder;
        sillaViewHolder.cargarProducto(listadDeProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return listadDeProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private CeldaListadoProductoBinding binding;

        public ProductoViewHolder(@NonNull CeldaListadoProductoBinding celdaListadoProductoBinding) {
            super(celdaListadoProductoBinding.getRoot());
            binding = celdaListadoProductoBinding;

            celdaListadoProductoBinding.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            celdaListadoProductoBinding.cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(getAdapterPosition());
                    return false;
                }
            });
        }

        public void cargarProducto (ItemListaAPI itemListaAPI){
            binding.celdaListadoText1.setText(itemListaAPI.getTitle());
            binding.celdaListadoPrecio.setText("$" + itemListaAPI.getPrice());
            Glide.with(binding.getRoot()).load(itemListaAPI.getThumbnail()).into(binding.celdaListadoImagen);
        }
    }


}

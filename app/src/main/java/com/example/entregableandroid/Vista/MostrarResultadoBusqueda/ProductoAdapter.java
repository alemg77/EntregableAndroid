package com.example.entregableandroid.Vista.MostrarResultadoBusqueda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entregableandroid.Modelo.ApiML.Item;
import com.example.entregableandroid.databinding.CeldaListadoProductoBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Item> listadDeProductos;
    private RecyclerViewClickInterfase listener;
    private Context context;

    public ProductoAdapter(Context context, RecyclerViewClickInterfase listener, List<Item> listadDeProductos) {
        this.listadDeProductos = listadDeProductos;
        this.context = context;
        this.listener = listener;
    }

    public void setListadDeProductos(List<Item> listadDeProductos) {
        this.listadDeProductos = listadDeProductos;
        notifyDataSetChanged();
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
                    listener.onItemClick(listadDeProductos.get(getAdapterPosition()));
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

        public void cargarProducto (Item item){
            binding.celdaListadoText1.setText(item.getTitle());
            binding.celdaListadoPrecio.setText("$" + item.getPrice());

            if ( item.getImagenFirebase() != null ){
                StorageReference child = FirebaseStorage.getInstance().getReference().child(item.getImagenFirebase());
                Glide.with(binding.getRoot()).load(child).into(binding.celdaListadoImagen);
            } else if ( item.getThumbnail() != null ) {
                Glide.with(binding.getRoot()).load(item.getThumbnail()).into(binding.celdaListadoImagen);
            }
        }
    }


}

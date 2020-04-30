package com.example.entregableandroid.RecyclerViewProducto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregableandroid.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter {

    private List<Producto> listadDeProductos;
    private ProductoAdapterListener listener;


    public ProductoAdapter(List<Producto> listadDeProductos, ProductoAdapterListener listener) {
        this.listadDeProductos = listadDeProductos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_producto, parent, false);
        ProductoAdapter.ProductoViewHolder viewHolder = new ProductoAdapter.ProductoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductoAdapter.ProductoViewHolder sillaViewHolder = (ProductoAdapter.ProductoViewHolder)holder;
        sillaViewHolder.cargarProducto(listadDeProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return listadDeProductos.size();
    }

    private class ProductoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewNombre;
        private TextView textViewPrecio;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.celdaSillaImagen);
            textViewNombre = itemView.findViewById(R.id.celdaSillaNombre);
            textViewPrecio = itemView.findViewById(R.id.celdaSillaPrecio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    Producto unProducto = listadDeProductos.get(posicion);
                    listener.seleccionProducto(unProducto);
                }
            });
        }

        public void cargarProducto (Producto unProducto){
            imageView.setImageResource(unProducto.getImagen());
            textViewPrecio.setText("$" + unProducto.getPrecio().toString());
            textViewNombre.setText(unProducto.getNombre());
        }
    }

    public interface ProductoAdapterListener {
        void seleccionProducto (Producto producto);
    }

}

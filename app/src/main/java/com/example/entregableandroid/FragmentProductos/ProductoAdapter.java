package com.example.entregableandroid.FragmentProductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregableandroid.Modelo.ElementoLista;
import com.example.entregableandroid.ApiML.ListaDeVentasML;
import com.example.entregableandroid.R;
import com.squareup.picasso.Picasso;

public class ProductoAdapter extends RecyclerView.Adapter {

    private ListaDeVentasML listadDeProductos;
    private ProductoAdapterListener listener;
    private Context context;

    public ProductoAdapter(Context context, ProductoAdapterListener listener,ListaDeVentasML listadDeProductos) {
        this.listadDeProductos = listadDeProductos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_listado_producto, parent, false);
        ProductoAdapter.ProductoViewHolder viewHolder = new ProductoAdapter.ProductoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductoAdapter.ProductoViewHolder sillaViewHolder = (ProductoAdapter.ProductoViewHolder)holder;
        sillaViewHolder.cargarProducto(listadDeProductos.obtenerElemento(position));
    }

    @Override
    public int getItemCount() {
        return listadDeProductos.cantidadElementos();
    }

    private class ProductoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView1,textViewPrecio;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.celdaListadoImagen);
            textView1 = itemView.findViewById(R.id.celdaListadoText1);
            textViewPrecio = itemView.findViewById(R.id.celdaListadoPrecio);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ElementoLista elementoLista = listadDeProductos.obtenerElemento(getAdapterPosition());
                    listener.seleccionProducto(elementoLista);
                }
            });
        }

        public void cargarProducto (ElementoLista elementoLista){
            textView1.setText(elementoLista.getTitle());
            textViewPrecio.setText("$" + elementoLista.getPrice());
            Picasso.get().load(elementoLista.getThumbnail()).into(imageView);
        }
    }

    public interface ProductoAdapterListener {
        void seleccionProducto (ElementoLista elementoLista);
    }

}

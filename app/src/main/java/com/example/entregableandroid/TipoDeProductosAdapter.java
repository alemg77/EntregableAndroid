package com.example.entregableandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TipoDeProductosAdapter extends RecyclerView.Adapter {
    private List<TipoDeProducto> listaTiposDeProducto;
    private AvisosMainActivity listener;

    public TipoDeProductosAdapter(List<TipoDeProducto> listaTiposDeProducto, AvisosMainActivity listener) {
        this.listaTiposDeProducto = listaTiposDeProducto;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_tipo_producto, parent, false);
        TipoDeProductosAdapter.TipoDeProductosAdapterViewHolder viewHolder = new TipoDeProductosAdapter.TipoDeProductosAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TipoDeProductosAdapterViewHolder) holder).cargarTipoDeProducto(listaTiposDeProducto.get(position));
    }

    @Override
    public int getItemCount() {
        return listaTiposDeProducto.size();
    }

    private class TipoDeProductosAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;

        public TipoDeProductosAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.celdaTipoProductoNombre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.seleccionTipoProducto(listaTiposDeProducto.get(getAdapterPosition()));
                }
            });
        }

        public void cargarTipoDeProducto(TipoDeProducto unTipoDeProducto) {
            nombre.setText(unTipoDeProducto.getNombre());
        }
    }
}


package com.example.entregableandroid.RecyclerView;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregableandroid.R;

import java.util.List;

public class TipoDeProductosAdapter extends RecyclerView.Adapter<TipoDeProductosAdapter.TipoDeProductosViewHolder> {

    private List<TipoDeProducto> listaTiposDeProducto;
    private TipoDeProductoListener listener;

    public TipoDeProductosAdapter(List<TipoDeProducto> listaTiposDeProducto, TipoDeProductoListener listener) {
        this.listaTiposDeProducto = listaTiposDeProducto;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TipoDeProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_tipo_producto, parent, false);
        TipoDeProductosViewHolder viewHolder = new TipoDeProductosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TipoDeProductosViewHolder holder, int position) {
        holder.cargarTipoDeProducto(listaTiposDeProducto.get(position));
    }

    @Override
    public int getItemCount() {
        return listaTiposDeProducto.size();
    }

    protected class TipoDeProductosViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;

        public TipoDeProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.celdaTipoProductoNombre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.seleccionProducto(listaTiposDeProducto.get(getAdapterPosition()));
                }
            });
        }

        protected void cargarTipoDeProducto(TipoDeProducto unTipoDeProducto) {
            nombre.setText(unTipoDeProducto.getNombre());
        }
    }


    public interface TipoDeProductoListener{
        void seleccionProducto ( TipoDeProducto unTipoDeProducto);
    }


}



package com.example.entregableandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SillaAdapter extends RecyclerView.Adapter {

    private List<Silla> listaDeSillas;
    private SillaAdapterListener listener;

    public SillaAdapter (List<Silla> listaDeSillas, SillaAdapterListener sillaAdapterListener){
        this.listaDeSillas = listaDeSillas;
        this.listener = sillaAdapterListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_silla, parent, false);
        SillaViewHolder viewHolder = new SillaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SillaViewHolder sillaViewHolder = (SillaViewHolder)holder;
        sillaViewHolder.cargarSilla(listaDeSillas.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDeSillas.size();
    }

    private class SillaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewNombre;
        private TextView textViewPrecio;

        public SillaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.celdaSillaImagen);
            textViewNombre = itemView.findViewById(R.id.celdaSillaNombre);
            textViewPrecio = itemView.findViewById(R.id.celdaSillaPrecio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    Silla unaSilla = listaDeSillas.get(posicion);
                    listener.pulsaronElemento(unaSilla);
                }
            });
        }

        public void cargarSilla ( Silla unaSilla){
            imageView.setImageResource(unaSilla.getImagen());
            textViewPrecio.setText(unaSilla.getPrecio().toString());
            textViewNombre.setText(unaSilla.getNombre());
        }
    }
}

package com.example.entregableandroid.Retrofit;

import android.util.Log;

import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMLDao {

    private Retrofit retrofit;
    private ServicioML servicioML;
    private String TAG = getClass().toString();
    private Avisos avisos;

    public ApiMLDao(Avisos listening) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioML = retrofit.create(ServicioML.class);

        this.avisos = listening;
    }

    public void buscarPorDescripcion(String descripcion) {
        Log.d(TAG, "Vamos a hacer una busqueda en MercadoLibre");
        servicioML.getItemsPorDescripcion(descripcion).enqueue(new Callback<ResultadoBusquedaAPI>() {
            @Override
            public void onResponse(Call<ResultadoBusquedaAPI> call, Response<ResultadoBusquedaAPI> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                avisos.resultadoBusqueda(response.body());
            }

            @Override
            public void onFailure(Call<ResultadoBusquedaAPI> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public void buscarItemPorId(String id){
        servicioML.getItemPorId(id).enqueue(new Callback<ItemAPI>() {
            @Override
            public void onResponse(Call<ItemAPI> call, Response<ItemAPI> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                avisos.LlegoItem(response.body());
            }

            @Override
            public void onFailure(Call<ItemAPI> call, Throwable t) {

            }
        });
    }

    public interface Avisos {
        void resultadoBusqueda(ResultadoBusquedaAPI resultadoBusqueda);
        void LlegoItem(ItemAPI itemML);
    }
}

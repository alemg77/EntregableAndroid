package com.example.entregableandroid.Controlador.ApiML;

import android.util.Log;

import com.example.entregableandroid.Modelo.ApiML.DescripcionItem;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;

import java.util.List;

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
    private String provincia;

    public ApiMLDao(Avisos listening) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioML = retrofit.create(ServicioML.class);
        this.provincia = ConstantesML.CAPITAL_FEDERAL;
        this.avisos = listening;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public void buscarPorDescripcion(String descripcion) {
        buscarPorDescripcion("*-*",descripcion);
    }

    public void buscarPorDescripcion(String rangoPrecio, String descripcion) {
        Log.d(TAG, "Vamos a hacer una busqueda en MercadoLibre");
        servicioML.getItemsPorDescripcion("all",rangoPrecio,provincia,descripcion).enqueue(new Callback<ResultadoBusquedaAPI>() {
            @Override
            public void onResponse(Call<ResultadoBusquedaAPI> call, Response<ResultadoBusquedaAPI> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                avisos.respuestaApiMercadoLibre(response.body());
            }

            @Override
            public void onFailure(Call<ResultadoBusquedaAPI> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public void buscarPorCategoria(String categoria, String sort){
        Log.d(TAG, "Vamos a hacer una busqueda en MercadoLibre");
        servicioML.getItemsPorCategoria(categoria, sort).enqueue(new Callback<ResultadoBusquedaAPI>() {
            @Override
            public void onResponse(Call<ResultadoBusquedaAPI> call, Response<ResultadoBusquedaAPI> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                avisos.respuestaApiMercadoLibre(response.body());
            }

            @Override
            public void onFailure(Call<ResultadoBusquedaAPI> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public void buscarItemPorId(String id){
        Log.d(TAG, "Vamos buscar un item");
        servicioML.getItemPorId(id).enqueue(new Callback<ItemAPI>() {
            @Override
            public void onResponse(Call<ItemAPI> call, Response<ItemAPI> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                avisos.respuestaApiMercadoLibre(response.body());
            }

            @Override
            public void onFailure(Call<ItemAPI> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public void buscarDescripcionItemm(String id) {
        Log.d(TAG, "Vamos buscar la descripcion de un item");

        servicioML.getItemDescripcionPorId(id).enqueue(new Callback<List<DescripcionItem>>() {
            @Override
            public void onResponse(Call<List<DescripcionItem>> call, Response<List<DescripcionItem>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                }
                List<DescripcionItem> list = response.body();
                avisos.respuestaApiMercadoLibre(list.get(0));
            }

            @Override
            public void onFailure(Call<List<DescripcionItem>> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public interface Avisos {
        void respuestaApiMercadoLibre(Object object);
    }
}

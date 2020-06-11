package com.example.entregableandroid.Controlador.ApiML;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.entregableandroid.Modelo.ApiML.DescripcionItem;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusqueda;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaoApiML extends ViewModel {

    private static DaoApiML daoApiML;


    private MutableLiveData<List<DescripcionItem>> descripcionItem;
    private MutableLiveData<ItemAPI> itemAPIMutableLiveData;
    private MutableLiveData<ResultadoBusqueda> resultadoBusquedaAPIMutableLiveData;

    private Retrofit retrofit;
    private ServicioML servicioML;
    private String TAG = getClass().toString();
    private String provincia;


    public DaoApiML() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioML = retrofit.create(ServicioML.class);
        this.provincia = ConstantesML.CAPITAL_FEDERAL;
    }

    public static DaoApiML getInstancia(ViewModelStoreOwner storeOwner){
        if ( daoApiML == null ){
            daoApiML = new ViewModelProvider(storeOwner).get(DaoApiML.class);
        }
        return daoApiML;
    }

    public MutableLiveData<ItemAPI> getItemAPIMutableLiveData() {
        if ( itemAPIMutableLiveData == null ) {
            itemAPIMutableLiveData = new MutableLiveData<ItemAPI>();
        }
        return itemAPIMutableLiveData;
    }

    public MutableLiveData<ResultadoBusqueda> getResultadoBusquedaAPI(){
        if ( resultadoBusquedaAPIMutableLiveData == null) {
            resultadoBusquedaAPIMutableLiveData = new MutableLiveData<ResultadoBusqueda>();
        }
        return resultadoBusquedaAPIMutableLiveData;
    }

    public MutableLiveData<List<DescripcionItem>> getDescripcionItem(){
        if ( descripcionItem == null) {
            descripcionItem = new MutableLiveData<List<DescripcionItem>>();
        }
        return descripcionItem;
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
        servicioML.getItemsPorDescripcion("all",rangoPrecio,provincia,descripcion).enqueue(new Callback<ResultadoBusqueda>() {
            @Override
            public void onResponse(Call<ResultadoBusqueda> call, Response<ResultadoBusqueda> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                resultadoBusquedaAPIMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResultadoBusqueda> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public void buscarPorCategoria(String categoria, String sort){
        Log.d(TAG, "Vamos a hacer una busqueda en MercadoLibre");
        servicioML.getItemsPorCategoria(categoria, sort).enqueue(new Callback<ResultadoBusqueda>() {
            @Override
            public void onResponse(Call<ResultadoBusqueda> call, Response<ResultadoBusqueda> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                resultadoBusquedaAPIMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResultadoBusqueda> call, Throwable t) {
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
                itemAPIMutableLiveData.setValue(response.body());
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
                descripcionItem.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DescripcionItem>> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }
}

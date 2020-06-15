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

    private static final Integer ITEMS_POR_PEDIDO = 20;

    private static DaoApiML daoApiML;
    private MutableLiveData<List<DescripcionItem>> descripcionItem;
    private MutableLiveData<ItemAPI> itemAPIMutableLiveData;
    private MutableLiveData<ResultadoBusqueda> resultadoBusquedaAPIMutableLiveData;

    private Retrofit retrofit;
    private ServicioML servicioML;
    private String TAG = getClass().toString();
    private String provincia;
    private String ultimaBusquedaDescripcion;
    private String ultimaBusquedaRangoPrecio;
    private Integer ultimaBusquedaOffset;
    private Integer ultimaBusquedaLimit;


    public DaoApiML() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioML = retrofit.create(ServicioML.class);
        this.provincia = ConstantesML.CAPITAL_FEDERAL;
    }

    public static DaoApiML getInstancia(ViewModelStoreOwner storeOwner) {
        if (daoApiML == null) {
            daoApiML = new ViewModelProvider(storeOwner).get(DaoApiML.class);
        }
        return daoApiML;
    }

    public MutableLiveData<ItemAPI> getItemAPIMutableLiveData() {
        if (itemAPIMutableLiveData == null) {
            itemAPIMutableLiveData = new MutableLiveData<ItemAPI>();
        }
        return itemAPIMutableLiveData;
    }

    public MutableLiveData<ResultadoBusqueda> getResultadoBusquedaAPI() {
        if (resultadoBusquedaAPIMutableLiveData == null) {
            resultadoBusquedaAPIMutableLiveData = new MutableLiveData<ResultadoBusqueda>();
        }
        return resultadoBusquedaAPIMutableLiveData;
    }

    public MutableLiveData<List<DescripcionItem>> getDescripcionItem() {
        if (descripcionItem == null) {
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
        buscarPorDescripcion("*-*", descripcion, ITEMS_POR_PEDIDO, 0 );
    }

    public void buscarPorDescripcion(String rangoPrecio, String descripcion, Integer limit, Integer offset) {
        ultimaBusquedaDescripcion = descripcion;
        ultimaBusquedaRangoPrecio = rangoPrecio;
        ultimaBusquedaLimit = limit;
        ultimaBusquedaOffset = offset;
        Log.d(TAG, "Vamos a hacer una busqueda en MercadoLibre");
        servicioML.getItemsPorDescripcion(
                "all",
                rangoPrecio,
                provincia,
                descripcion,
                String.valueOf(limit),
                String.valueOf(offset))
                .enqueue(new Callback<ResultadoBusqueda>() {
                    @Override
                    public void onResponse(Call<ResultadoBusqueda> call, Response<ResultadoBusqueda> response) {
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "Retrofit response code:" + response.code());
                            return;
                        }
                        ResultadoBusqueda resultadoBusqueda = response.body();
                        resultadoBusqueda.setOrigen(ResultadoBusqueda.BUSQUEDA_API);
                        resultadoBusqueda.setPagina(ultimaBusquedaOffset);
                        resultadoBusquedaAPIMutableLiveData.setValue(response.body());
                    }
                    @Override
                    public void onFailure(Call<ResultadoBusqueda> call, Throwable t) {
                        Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
                    }
                });
    }


    /***
     *   Repite la ultima busqueda realizada, pero con el offset incrementado
     *   para poder implementar paginacion      *
     */
    public void masDeLaUltima() {
        ultimaBusquedaOffset++;
        buscarPorDescripcion(ultimaBusquedaRangoPrecio,
                ultimaBusquedaDescripcion,
                ultimaBusquedaLimit,
                ultimaBusquedaOffset);
    }


    /***
     *      Busca items por categoria
     *
     * @param categoria
     * @param limit
     * @param offset
     */
    // TODO: Seria mejor hacer las busquedas por categoria: son mas precisas.
    public void buscarPorCategoria(String categoria, String limit, String offset) {
        Log.d(TAG, "Vamos a hacer una busqueda en MercadoLibre");
        servicioML.getItemsPorCategoria(categoria, limit, offset).enqueue(new Callback<ResultadoBusqueda>() {
            @Override
            public void onResponse(Call<ResultadoBusqueda> call, Response<ResultadoBusqueda> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Retrofit response code:" + response.code());
                    return;
                }
                ResultadoBusqueda resultadoBusqueda = response.body();
                resultadoBusqueda.setOrigen(ResultadoBusqueda.BUSQUEDA_API);
                resultadoBusquedaAPIMutableLiveData.setValue(resultadoBusqueda);
            }

            @Override
            public void onFailure(Call<ResultadoBusqueda> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure:" + t.getMessage().toString());
            }
        });
    }

    public void buscarItemPorId(String id) {
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

    public void buscarDescripcionItem(String id) {
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

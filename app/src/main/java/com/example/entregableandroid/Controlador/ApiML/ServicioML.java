package com.example.entregableandroid.Controlador.ApiML;


import com.example.entregableandroid.Modelo.ApiML.DescripcionItem;
import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioML {

    @GET("sites/MLA/search")
    Call<ResultadoBusquedaAPI> getItemsPorDescripcion(@Query("condition") String condicion, @Query("price") String rangoPrecio, @Query("state") String provincia, @Query("q") String d);

    @GET("sites/MLA/search")
    Call<ResultadoBusquedaAPI> getItemsPorCategoria(@Query("category") String categoria,@Query("sort") String sort);

    @GET("items/{id}")
    Call<ItemAPI> getItemPorId(@Path("id") String id);

    @GET("items/{id}/descriptions")
    Call<List<DescripcionItem>> getItemDescripcionPorId(@Path("id") String id);

    // TODO: PAGINACION!!!!
    // https://api.mercadolibre.com/sites/MLA/search?q=fiat&limit=20&offset=25/

}

package com.example.entregableandroid.DAO.ApiML;


import com.example.entregableandroid.Modelo.ApiML.ItemAPI;
import com.example.entregableandroid.Modelo.ApiML.ResultadoBusquedaAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioML {

    @GET("sites/MLA/search")
    Call<ResultadoBusquedaAPI> getItemsPorDescripcion(@Query("condition") String condicion, @Query("price") String rangoPrecio, @Query("state") String provincia, @Query("q") String d);

    @GET("items/{id}")
    Call<ItemAPI> getItemPorId(@Path("id") String id);
}
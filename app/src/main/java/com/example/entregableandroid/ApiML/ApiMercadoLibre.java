package com.example.entregableandroid.ApiML;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.example.entregableandroid.ApiML.ApiWeb.PedidosApiWeb;
import com.example.entregableandroid.ApiML.ApiWeb.RecepcionPedidoAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.entregableandroid.ApiML.ApiWeb.PedidosApiWeb.URL_API_MERCADOLIBRE_ARGENTINA;

public class ApiMercadoLibre extends AppCompatActivity implements RecepcionPedidoAPI {

    private RecepcionApiMercadoLibre listener;
    private PedidosApiWeb apiWebMercadolibre;
    private String TAG = getClass().toString();


    private String pedido_api;

    public ApiMercadoLibre(RecepcionApiMercadoLibre listener) {
        this.listener = listener;
        this.apiWebMercadolibre = new PedidosApiWeb((Context) listener, this, URL_API_MERCADOLIBRE_ARGENTINA);
    }

    public void buscarPorDescripcion(String string) {
        apiWebMercadolibre.getRequest(URL_API_MERCADOLIBRE_ARGENTINA + "sites/MLA/search?q=" + string);
    }


    public void buscarPorId(String string) {
        apiWebMercadolibre.getRequest(URL_API_MERCADOLIBRE_ARGENTINA + "items/" + string);
    }

    @Override
    public void recepcionJSON(JSONObject jsonRespuesta) {
        ArrayList<ElementoLista> listVentaML = new ArrayList<>();
        if (jsonRespuesta.has("results")) {
            try {
                JSONArray jsonArray = jsonRespuesta.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    listVentaML.add(new ElementoLista((JSONObject) jsonArray.get(i)));
                }
                listener.recepcionMLlistaVenta(new ListaDeVentasML(listVentaML));
            } catch (Exception e) {
                Log.d(TAG, "Error leyendo el JSON: " + jsonRespuesta.toString());
            }
        } else if (jsonRespuesta.has("sale_terms")) {
            try {
                ItemVenta itemVenta = new ItemVenta(jsonRespuesta);
                listener.recepcionMLitemVenta(itemVenta);
            } catch (Exception e) {
                Log.d(TAG, "Error leyendo JSON ItemVenta en la respuesta de la API");
                listener.errorPedidoApiMercadolibre();
            }
        } else {
            Log.d(TAG, "Error leyendo JSON en la respuesta del API: No se pudo determinar que tipo de JSON llego");
        }
    }

    @Override
    public void errorPedidoAPI() {
        listener.errorPedidoApiMercadolibre();
    }
}

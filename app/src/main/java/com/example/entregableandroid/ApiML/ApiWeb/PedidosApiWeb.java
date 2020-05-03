package com.example.entregableandroid.ApiML.ApiWeb;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PedidosApiWeb implements Response.Listener, Response.ErrorListener {

    public final static String URL_API_NOTICIAS = "https://newsapi.org/v2/";
    public final static String URL_API_MERCADOLIBRE_ARGENTINA = "https://api.mercadolibre.com/";

    private String TAG = getClass().toString();
    private Context context;
    private RecepcionPedidoAPI listener;
    private String url;
    private Map<String, String> cabezeras;

    public PedidosApiWeb(Context context, RecepcionPedidoAPI listener, String urlAPI) {
        this.context = context;
        this.listener = listener;
        this.url = url;
        cabezeras = new HashMap<String, String>();
    }

    public PedidosApiWeb(String TAG, Context context, RecepcionPedidoAPI listener, String url, Map<String, String> cabezeras) {
        this.TAG = TAG;
        this.context = context;
        this.listener = listener;
        this.url = url;
        this.cabezeras = cabezeras;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCabezeras(Map<String, String> cabezeras) {
        this.cabezeras = cabezeras;
    }

    public void getRequest(){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest getRequest = new StringRequest(Request.Method.GET, this.url,this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(getRequest);
    }

    public void getRequest (String url){
        Log.d(TAG, "GET: " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return cabezeras;
            }
        };
        queue.add(getRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "Error!!:" + error.toString());
    }

    @Override
    public void onResponse(Object response) {
        try {
            listener.recepcionJSON(new JSONObject((String) response));
        } catch (JSONException e) {
            e.printStackTrace();
            listener.errorPedidoAPI();
        }
    }
}


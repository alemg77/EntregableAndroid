package com.example.entregableandroid.Vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.entregableandroid.Controlador.GoogleMap.FetchAddressIntentService;
import com.example.entregableandroid.MainActivity;
import com.example.entregableandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private final int PERMISO_FINE_LOCATION = 69;
    private String TAG = getClass().toString();
    private GoogleMap mMap;
    private LatLng coordenadas;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GeoApiContext mGeoApiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(TAG, "Inicio de MapsActivity");
        Intent intent = getIntent();
        coordenadas = intent.getParcelableExtra(LatLng.class.toString());
        verificarPermisos();
    }

    public void verificarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No tenemos permiso, vamos a pedirlo");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_FINE_LOCATION);
        } else {
            Log.d(TAG, "Ya tenemos permiso de antes");
            ubicacionGeografica();
            iniciarMapa();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISO_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "El usario nos dio el permiso!!");
                    ubicacionGeografica();
                    iniciarMapa();
                } else {
                    Log.d(TAG, "El usuario nego el permiso");
                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return;
            }
        }
    }

    private void iniciarMapa() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mGeoApiContext == null) mGeoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build();
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);

        mMap.setOnMapClickListener(this);


        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .icon(vectorToBitmap(R.drawable.ic_person_black_24dp, Color.parseColor("#FF0000")))
                .title("Vendedor"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,15));
    }

    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void ubicacionGeografica() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent(getApplicationContext(), FetchAddressIntentService.class);
                AddressResultReceiver addressResultReceiver = new AddressResultReceiver(new Handler());
                intent.putExtra(FetchAddressIntentService.RECEIVER, addressResultReceiver);
                intent.putExtra(FetchAddressIntentService.LOCATION_DATA_EXTRA, location);
                startService(intent);
                LatLng latLngDispocitivo = new LatLng(location.getLatitude(), location.getLongitude());
                if (mMap != null) {

                    mMap.addMarker(new MarkerOptions()
                            .position(latLngDispocitivo)
                            .icon(vectorToBitmap(R.drawable.ic_home_black_24dp, Color.parseColor("#FF0000")))
                            .title("Estamos aqui!!"));

                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngDispocitivo, 15));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d(TAG, "Metodo  onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d(TAG, "Metodo  onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(TAG, "Metodo  onProviderDisabled");
            }
        };

        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // Para usar el GPS en vez de la red:
        //String locationProvider = LocationManager.GPS_PROVIDER;

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick!!!!");
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick. Latidud"+latLng.latitude+" Longitud:"+latLng.longitude);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) { super(handler);  }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData == null) return;
            Address address = resultData.getParcelable(FetchAddressIntentService.RESULT_DATA_KEY);
            Log.d(TAG, "En la actividad llego:"+address);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Para no consumir tanta bateria.
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}


package com.example.entregableandroid;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final int PERMISO_FINE_LOCATION = 69;
    private String TAG = getClass().toString();
    private GoogleMap mMap;
    private LatLng coordenadas;
    private LatLng latLngDispocitivo;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GeoApiContext mGeoApiContext;

    public void verificarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No tenemos permiso, vamos a pedirlo");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_FINE_LOCATION);
        } else {
            Log.d(TAG, "Ya tenemos permiso de antes");
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISO_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "El usario nos dio el permiso!!");
                } else {
                    Log.d(TAG, "El usuario nego el permiso");
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(TAG, "Inicio de MapsActivity");
        verificarPermisos();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "*********** No tenemos permisos suficientes para usar Google Maps ****************");
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
                latLngDispocitivo = new LatLng(location.getLatitude(), location.getLongitude());
                if ( mMap != null ){
                    mMap.addMarker(new MarkerOptions().position(latLngDispocitivo).title("Estamos aqui!!"));
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

        Intent intent = getIntent();
        coordenadas = intent.getParcelableExtra(LatLng.class.toString());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mGeoApiContext == null) mGeoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build();
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(coordenadas).title("Vendedor"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,15));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Para no consumir tanta bateria.
        locationManager.removeUpdates(locationListener);
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
}


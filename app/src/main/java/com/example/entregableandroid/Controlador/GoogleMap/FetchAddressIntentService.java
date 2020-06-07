package com.example.entregableandroid.Controlador.GoogleMap;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME ="com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +".LOCATION_DATA_EXTRA";
    private String TAG = getClass().toString();

    protected ResultReceiver receiver;

    private void deliverResultToReceiver(int resultCode, Address address) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_DATA_KEY, address);
        receiver.send(resultCode, bundle);
    }

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);
        receiver = intent.getParcelableExtra(RECEIVER);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.d(TAG, "Problema con la red:" + ioException.toString());
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.d(TAG, "Problemas con las coordenadas:"+illegalArgumentException.toString());
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            Log.d(TAG, "ERROR: No conseguimos la direccion");
        } else {
            Address address = addresses.get(0);
            deliverResultToReceiver(1, address);
        }
    }

}

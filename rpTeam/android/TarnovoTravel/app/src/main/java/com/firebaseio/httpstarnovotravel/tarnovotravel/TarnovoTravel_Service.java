package com.firebaseio.httpstarnovotravel.tarnovotravel;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rostik on 22.10.16.
 */

public class TarnovoTravel_Service extends Service {
    private LocationListener listener;
    private LocationManager locationManager;
    private  Firebase mFirebaseRef;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase("https://tarnovotravel.firebaseio.com/buses");
        final Bus bus = new Bus("", 0, 0.0, 0.0);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);
                updateBusCoordinates(bus, location.getLongitude(), location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,3,listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    private void updateBusCoordinates(Bus bus, double longtitude, double latitude) {
        String uid = bus.getUid();
        if(uid == "") {
            uid = mFirebaseRef.push().getKey();
            bus.setUid(uid);
        }
        else {
            bus.setBusNumber(23);
            bus.setLatitude(latitude);
            bus.setLongtitude(longtitude);

            Map<String, Object> busValues = bus.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + bus.getUid(), busValues);
            mFirebaseRef.updateChildren(childUpdates);
        }
    }
}

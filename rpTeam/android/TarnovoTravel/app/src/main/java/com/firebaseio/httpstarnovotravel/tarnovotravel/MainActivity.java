package com.firebaseio.httpstarnovotravel.tarnovotravel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button startTrackingButton;
    private Button stopTrackingButton;
    private TextView textField;
    private LocationManager locationManager;
    private LocationListener listener;
    private Firebase myFirebaseRef;
    final Bus bus = new Bus("", 0, 0.0, 0.0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://tarnovotravel.firebaseio.com/buses");

        textField = (TextView) findViewById(R.id.textView);
        startTrackingButton = (Button) findViewById(R.id.button);
        stopTrackingButton = (Button) findViewById(R.id.button2);
        final EditText editText = (EditText) findViewById(R.id.editText);

        stopTrackingButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if(bus.getUid() != "") {
                    myFirebaseRef.child("/" + bus.getUid()).removeValue();
                }
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textField.setText(location.getLongitude() + " " + location.getLatitude());
                bus.setBusNumber(Integer.parseInt(editText.getText().toString()));
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
                startActivity(i);
            }
        };

        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        startTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 3, listener);
            }
        });
    }

    private void updateBusCoordinates(Bus bus, double longitude, double latitude) {
        String uid = bus.getUid();
        if(uid == "") {
            uid = myFirebaseRef.push().getKey();
            bus.setUid(uid);
        }
        else {
            bus.setLatitude(latitude);
            bus.setLongitude(longitude);

            Map<String, Object> busValues = bus.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + bus.getUid(), busValues);
            myFirebaseRef.updateChildren(childUpdates);
        }
    }
}

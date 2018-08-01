package com.master.killercode.broadcastlocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.master.killercode.broadcastlocation.Utils.BroadcastUtils;
import com.master.killercode.broadcastlocation.Utils.GoogleApiLocationManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    public static Activity activity;
    public static GoogleApiClient googleApiClient;
    public static Location mylocation;

    //TextView
    public static TextView labelLat;
    public static TextView labelLong;
    private static TextView labelLocation;
    private static TextView labelLocationInfo;
    private static TextView labelLocationState;

    //Switch
    private static Switch swLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        initActions();
        setUpGClient();

    }

    private void initVars() {
        context = getApplicationContext();
        activity = MainActivity.this;
        //
        //TextView
        labelLat = findViewById(R.id.labelLat);
        labelLong = findViewById(R.id.labelLong);
        labelLocation = findViewById(R.id.labelLocation);
        labelLocationInfo = findViewById(R.id.labelLocationInfo);
        labelLocationState = findViewById(R.id.labelLocationState);

        //Switch
        swLocation = findViewById(R.id.swLocation);
        swLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                } else {
                }
            }
        });
    }

    private void initActions() {
        BroadcastUtils bUtil = new BroadcastUtils(context);
        bUtil.verifieLocationEnabled();
    }

    public static void setLocationState(boolean gps, boolean network) {
        swLocation.setChecked(gps);
        labelLocationInfo.setText("GPS :" + gps);
        labelLocationState.setText("Network :" + network);
    }

    public static void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10);
            }
        } else {
            GoogleApiLocationManager.getMyLocation(context);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            GoogleApiLocationManager.getMyLocation(context);
        } else if (permissionLocation == PackageManager.PERMISSION_DENIED) {

        }
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, GoogleApiLocationManager.onConnectionFailedListener)
                .addConnectionCallbacks(GoogleApiLocationManager.connectionCallbacks)
                .addOnConnectionFailedListener(GoogleApiLocationManager.onConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


}

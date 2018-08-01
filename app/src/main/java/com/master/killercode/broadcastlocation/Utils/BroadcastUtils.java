package com.master.killercode.broadcastlocation.Utils;

import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.master.killercode.broadcastlocation.MainActivity;

public class BroadcastUtils {

    private Context context;

    public BroadcastUtils(Context context) {
        this.context = context;
    }

    public void verifieLocationEnabled() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            if (lm != null) {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                log("Location: Gps:" + gps_enabled + ", Network:" + network_enabled);
            }
        } catch (Exception ignored) {
        }



        if (!gps_enabled && !network_enabled) {
            // notify user
            GoogleApiLocationManager.getMyLocation(context);
        }

        MainActivity.setLocationState(gps_enabled , network_enabled);

    }

    public static void log(String s) {
        Log.w("broadcastUtils Alert", s);
    }

}

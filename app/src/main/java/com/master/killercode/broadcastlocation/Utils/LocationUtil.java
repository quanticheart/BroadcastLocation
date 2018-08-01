package com.master.killercode.broadcastlocation.Utils;

import android.app.Activity;
import android.content.Intent;

public class LocationUtil {

    //For KitKat or <
    public void enableGps(Activity activity, boolean state) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", state);
        activity.sendBroadcast(intent);
    }

}

package com.labournet.surveyapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by Athira on 11/6/19.
 */
public class InternetStatus {

    private static InternetStatus instance = new InternetStatus();

    public static InternetStatus getInstance() {
        return instance;
    }


    public boolean isConnectedToInternet(Context ctx) {
        boolean connected = false;
        String msg = "";
        try {
            ConnectivityManager cManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cManager.getActiveNetworkInfo();

            if (info != null && info.isAvailable() && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    connected = true;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // check NetworkInfo subtype
                    if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                        msg = "Bandwidth less than 100 kbps";
                        connected = false;
                    } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                        msg = "Bandwidth between 50-100 kbps";
                        connected = false;
                    } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_1xRTT) {
                        msg = "Bandwidth between 50-100 kbps";
                        connected = false;
                    } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
                        msg = "Bandwidth between 14-64 kbps";
                        connected = false;
                    } else {
                        connected = info.getSubtype() != TelephonyManager.NETWORK_TYPE_UNKNOWN;
                        msg = "Unknown network type";
                    }
                } else msg = "No Wifi/Mobile Data";
            } else msg = "Not connected";

        } catch (Exception e) {
            msg = e.getMessage();
        }
        if (!connected)
            Utils.captureBugReport(ctx, Utils.getFileName("BugReport", "Internet", "Status", Keys.MIME_TYPE_TXT), "Internet Status", msg);
        return connected;
    }

}

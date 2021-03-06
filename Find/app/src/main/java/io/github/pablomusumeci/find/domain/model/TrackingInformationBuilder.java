package io.github.pablomusumeci.find.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class TrackingInformationBuilder {


    private String group;
    private String username;
    private Date time;
    private String location = "";
    private List<Fingerprint> fingerprints = new ArrayList<>();

    public TrackingInformationBuilder withUser(String username) {
        this.username = username;
        return this;
    }

    public TrackingInformationBuilder withTime(Date time) {
        this.time = time;
        return this;
    }

    public TrackingInformationBuilder withLocation(String location) {
        this.location = location == null ? "" : location;
        return this;
    }

    public TrackingInformationBuilder withGroup(String group) {
        this.group = group;
        return this;
    }

    public TrackingInformation build() {
        return new TrackingInformation(this.username, this.group, this.location, this.time, this.fingerprints);
    }

    public TrackingInformationBuilder withScannedFingerprints(Context context) {
        this.fingerprints = getScannedWifiFingerprints(context);
        return this;
    }

    private List<Fingerprint> getScannedWifiFingerprints(Context context) {
        List<Fingerprint> fingerprints = new ArrayList<>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Log.i("Wifi", "Started scanning");
        for (ScanResult scanResult : wifiManager.getScanResults()) {
            Fingerprint fingerprint = new Fingerprint(scanResult.BSSID, scanResult.level);
            Log.i("BSSID: ", scanResult.BSSID);
            Log.i("Level: ", String.valueOf(scanResult.level));
            fingerprints.add(fingerprint);
        }
        Log.i("Wifi", "Ended scanning");
        return fingerprints;
    }
}

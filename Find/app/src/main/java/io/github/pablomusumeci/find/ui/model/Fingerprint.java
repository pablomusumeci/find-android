package io.github.pablomusumeci.find.ui.model;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class Fingerprint {

    private int rssi;

    private String mac;

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}

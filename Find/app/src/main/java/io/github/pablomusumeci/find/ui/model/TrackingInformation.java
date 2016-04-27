package io.github.pablomusumeci.find.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class TrackingInformation {

    private String group;
    private String username;
    private Date time;
    private String location;

    @SerializedName("wifi-fingerprint")
    private List<Fingerprint> wifiFingerprint;

    public TrackingInformation(String username, String group, String location, Date time, List<Fingerprint> fingerprints) {
        this.group = group;
        this.username = username;
        this.time = time;
        this.location = location;
        this.wifiFingerprint = fingerprints;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Fingerprint> getWifiFingerprint() {
        return wifiFingerprint;
    }

    public void setWifiFingerprint(List<Fingerprint> wifiFingerprint) {
        this.wifiFingerprint = wifiFingerprint;
    }
}

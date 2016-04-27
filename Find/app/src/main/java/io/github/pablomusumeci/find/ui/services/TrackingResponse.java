package io.github.pablomusumeci.find.ui.services;

import java.util.Map;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class TrackingResponse {
    private boolean success;
    private String message;
    private String location;
    private Map<String, Float> bayes;

    public Map<String, Float> getBayes() {
        return bayes;
    }

    public void setBayes(Map<String, Float> bayes) {
        this.bayes = bayes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

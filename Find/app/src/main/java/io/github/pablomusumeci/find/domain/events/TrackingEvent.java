package io.github.pablomusumeci.find.domain.events;

import io.github.pablomusumeci.find.domain.services.api.TrackingResponse;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class TrackingEvent {

    private String location;
    private String message;
    private Boolean success;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public TrackingEvent(TrackingResponse trackingResponse) {
        location = trackingResponse.getLocation();
        message = trackingResponse.getMessage();
        success = trackingResponse.isSuccess();
    }
}

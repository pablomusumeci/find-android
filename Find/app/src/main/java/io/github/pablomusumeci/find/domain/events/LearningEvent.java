package io.github.pablomusumeci.find.domain.events;

import io.github.pablomusumeci.find.domain.services.api.LearningResponse;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class LearningEvent {
    private String message;
    private Boolean success;

    public LearningEvent(LearningResponse response) {
        message = response.getMessage();
        success = response.isSuccess();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package io.github.pablomusumeci.find.domain.services.api;

import java.util.Map;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class LearningResponse {
    private boolean success;
    private String message;

    public Map<String, Float> getBayes() {
        return bayes;
    }

    private Map<String, Float> bayes;

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
}

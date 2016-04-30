package io.github.pablomusumeci.find.domain.events;

/**
 * This event is captured in the UI thread and an
 * {@link android.app.AlertDialog} will be shown
 * with the received message
 */
public class ErrorEvent {

    private String title;
    private String message;

    public ErrorEvent(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

}

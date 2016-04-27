package io.github.pablomusumeci.find.ui;

import android.app.Application;
import io.github.pablomusumeci.find.ui.services.HttpService;
import io.github.pablomusumeci.find.ui.services.ServiceGenerator;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class FindApplication extends Application {

    private final HttpService httpService;

    public FindApplication() {
        httpService = ServiceGenerator.createService(HttpService.class);
    }

    public HttpService getHttpService() {
        return httpService;
    }
}

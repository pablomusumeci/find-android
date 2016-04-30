package io.github.pablomusumeci.find;

import android.app.Application;
import io.github.pablomusumeci.find.domain.services.api.HttpService;
import io.github.pablomusumeci.find.domain.services.api.ServiceGenerator;

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

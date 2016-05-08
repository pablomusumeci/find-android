package io.github.pablomusumeci.find;

import com.pixplicity.easyprefs.library.Prefs;

import android.app.Application;
import android.content.ContextWrapper;
import io.github.pablomusumeci.find.domain.services.api.HttpService;
import io.github.pablomusumeci.find.domain.services.api.ServiceGenerator;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class FindApplication extends Application {

    private HttpService httpService;

    public FindApplication() {
    }

    public HttpService getHttpService() {
        return httpService;
    }

    public void setHttpService(){
        httpService = ServiceGenerator.createService(HttpService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        setHttpService();
    }
}

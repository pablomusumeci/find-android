package io.github.pablomusumeci.find.ui.services;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import io.github.pablomusumeci.find.ui.FindApplication;
import io.github.pablomusumeci.find.ui.events.ScanningCancelled;
import io.github.pablomusumeci.find.ui.model.scanning.ScanningStrategy;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class ScanningService extends IntentService {
    private HttpService httpService;
    private AtomicBoolean shouldRun = new AtomicBoolean(true);

    public ScanningService() {
        super("ScanningService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FindApplication application = (FindApplication) getApplication();
        httpService = application.getHttpService();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        ScanningStrategy scanningStrategy = (ScanningStrategy) bundle.getSerializable("strategy");
        while (shouldRun.get()) {
            try {
                scanningStrategy.scan(getApplicationContext(), bundle, httpService);
            }
            catch (Exception e) {
                shouldRun.set(false);
            }
        }
    }

    @Subscribe
    public void onScanningCancelled(ScanningCancelled event) {
        shouldRun.set(false);
    }
}

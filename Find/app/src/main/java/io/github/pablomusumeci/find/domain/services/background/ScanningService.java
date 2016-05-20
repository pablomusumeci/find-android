package io.github.pablomusumeci.find.domain.services.background;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import io.github.pablomusumeci.find.FindApplication;
import io.github.pablomusumeci.find.domain.events.ScanningCancelled;
import io.github.pablomusumeci.find.domain.model.scanning.ScanningStrategy;
import io.github.pablomusumeci.find.domain.services.api.HttpService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class ScanningService extends IntentService {

    private HttpService httpService;
    private AtomicBoolean shouldRun = new AtomicBoolean(true);
    private Semaphore semaphore = new Semaphore(0);
    private BroadcastReceiver broadcastReceiver;

    public ScanningService() {
        super("ScanningService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FindApplication application = (FindApplication) getApplication();
        createOnScanResultsReceiver();
        httpService = application.getHttpService();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        ScanningStrategy scanningStrategy = (ScanningStrategy) bundle.getSerializable("strategy");
        WifiManager wifi = (WifiManager) getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        while (shouldRun.get()) {
            try {
                // start async scanning
                wifi.startScan();
                // wait until scan is available
                semaphore.acquire();
                if (!shouldRun.get()) {
                    return;
                }
                // process result
                scanningStrategy.scan(getApplicationContext(), bundle, httpService);
            }
            catch (InterruptedException e) {
                Log.d("Interrupted: ", String.valueOf(shouldRun.get()));
                shouldRun.set(false);
            }
            catch (Exception e) {
                Log.e("Exception: ", e.getMessage());
                shouldRun.set(false);
            }
        }
    }

    @Subscribe
    public void onScanningCancelled(ScanningCancelled event) {
        shouldRun.set(false);
        semaphore.release();
    }

    /**
     * Release lock when wifi results are available
     */
    private void createOnScanResultsReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                semaphore.release();
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }
}

package io.github.pablomusumeci.find.ui.model.scanning;

import android.content.Context;
import android.os.Bundle;
import io.github.pablomusumeci.find.ui.services.HttpService;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public interface ScanningStrategy {

    void scan(Context context, Bundle bundle, HttpService httpService) throws Exception;
}

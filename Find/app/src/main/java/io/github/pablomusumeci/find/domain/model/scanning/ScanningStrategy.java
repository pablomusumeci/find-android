package io.github.pablomusumeci.find.domain.model.scanning;

import android.content.Context;
import android.os.Bundle;
import io.github.pablomusumeci.find.domain.services.api.HttpService;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public interface ScanningStrategy {

    void scan(Context context, Bundle bundle, HttpService httpService) throws Exception;
}

package io.github.pablomusumeci.find.domain.model.scanning;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import io.github.pablomusumeci.find.domain.events.ErrorEvent;
import io.github.pablomusumeci.find.domain.events.TrackingEvent;
import io.github.pablomusumeci.find.domain.model.TrackingInformation;
import io.github.pablomusumeci.find.domain.model.TrackingInformationBuilder;
import io.github.pablomusumeci.find.domain.services.api.HttpService;
import io.github.pablomusumeci.find.domain.services.api.ServiceGenerator;
import io.github.pablomusumeci.find.domain.services.api.TrackingResponse;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class TrackingStrategy implements ScanningStrategy, Serializable {

    public void scan(Context context, Bundle bundle, HttpService httpService) throws Exception {

        TrackingInformation trackingInformation = new TrackingInformationBuilder()
                .withGroup(bundle.getString("group"))
                .withUser(bundle.getString("user"))
                .withTime(new Date())
                .withScannedFingerprints(context)
                .build();


        Call<TrackingResponse> call = httpService.track(trackingInformation);

        try {
            TrackingResponse response = call.execute().body();
            EventBus.getDefault().post(new TrackingEvent(response));
        }
        catch (ConnectException e) {
            e.printStackTrace();
            String message = String.format("Cannot connect to server '%s'", ServiceGenerator.API_BASE_URL);
            EventBus.getDefault().post(new ErrorEvent("Connection error", message));
            throw new Exception(message);
        }
        catch (IOException e) {
            e.printStackTrace();
            String message = "An error happened during the learning process";
            EventBus.getDefault().post(new ErrorEvent("Error", message));
            throw new Exception(message);
        }
    }
}
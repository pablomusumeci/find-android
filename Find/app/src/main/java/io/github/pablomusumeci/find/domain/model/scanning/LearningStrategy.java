package io.github.pablomusumeci.find.domain.model.scanning;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import io.github.pablomusumeci.find.domain.events.ErrorEvent;
import io.github.pablomusumeci.find.domain.events.LearningEvent;
import io.github.pablomusumeci.find.domain.model.ApplicationSettings;
import io.github.pablomusumeci.find.domain.model.TrackingInformation;
import io.github.pablomusumeci.find.domain.model.TrackingInformationBuilder;
import io.github.pablomusumeci.find.domain.services.api.HttpService;
import io.github.pablomusumeci.find.domain.services.api.LearningResponse;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class LearningStrategy implements ScanningStrategy, Serializable {

    public void scan(Context context, Bundle bundle, HttpService httpService) throws Exception {

        TrackingInformation trackingInformation = new TrackingInformationBuilder()
                .withGroup(ApplicationSettings.getGroupName())
                .withUser(ApplicationSettings.getUsername())
                .withLocation(bundle.getString("location"))
                .withTime(new Date())
                .withScannedFingerprints(context)
                .build();


        Call<LearningResponse> call = httpService.learn(trackingInformation);

        try {
            LearningResponse response = call.execute().body();
            EventBus.getDefault().post(new LearningEvent(response));
        }
        catch (ConnectException e) {
            Log.e("LearningStrategy", e.getMessage());
            String message = String.format("Cannot connect to server '%s'", ApplicationSettings.getServerAddress());
            EventBus.getDefault().post(new ErrorEvent("Connection error", message));
            throw new Exception(message);
        }
        catch (IOException e) {
            Log.e("LearningStrategy", e.getMessage());
            String message = "An error happened during the learning process";
            EventBus.getDefault().post(new ErrorEvent("Error", message));
            throw new Exception(message);
        }
    }
}
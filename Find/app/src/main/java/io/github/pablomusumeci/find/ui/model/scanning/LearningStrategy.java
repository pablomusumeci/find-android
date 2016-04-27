package io.github.pablomusumeci.find.ui.model.scanning;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import io.github.pablomusumeci.find.ui.events.LearningEvent;
import io.github.pablomusumeci.find.ui.model.TrackingInformation;
import io.github.pablomusumeci.find.ui.model.TrackingInformationBuilder;
import io.github.pablomusumeci.find.ui.services.HttpService;
import io.github.pablomusumeci.find.ui.services.LearningResponse;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;

/**
 * Created by pablomusumeci on 4/26/16.
 */
public class LearningStrategy implements ScanningStrategy, Serializable {

    public void scan(Context context, Bundle bundle, HttpService httpService) throws Exception {

        TrackingInformation trackingInformation = new TrackingInformationBuilder()
                .withGroup(bundle.getString("group"))
                .withUser(bundle.getString("user"))
                .withLocation(bundle.getString("location"))
                .withTime(new Date())
                .withScannedFingerprints(context)
                .build();


        Call<LearningResponse> call = httpService.learn(trackingInformation);

        try {
            LearningResponse response = call.execute().body();
            EventBus.getDefault().post(new LearningEvent(response));
        }
        catch (IOException e) {
            Log.e("LearningStrategy", e.getMessage());
            e.printStackTrace();
            throw new Exception("An error happened during the learning process");
        }
    }
}
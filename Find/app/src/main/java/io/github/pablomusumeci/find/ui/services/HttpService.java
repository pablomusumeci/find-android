package io.github.pablomusumeci.find.ui.services;

import io.github.pablomusumeci.find.ui.model.TrackingInformation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public interface HttpService {
    @POST("/learn")
    Call<LearningResponse> learn(@Body TrackingInformation trackingInformation);

    @POST("/track")
    Call<TrackingResponse> track(@Body TrackingInformation trackingInformation);

}

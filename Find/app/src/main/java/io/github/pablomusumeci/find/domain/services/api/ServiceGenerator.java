package io.github.pablomusumeci.find.domain.services.api;

import io.github.pablomusumeci.find.domain.model.ApplicationSettings;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pablomusumeci on 4/24/16.
 */
public class ServiceGenerator {


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(ApplicationSettings.getServerAddress())
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}

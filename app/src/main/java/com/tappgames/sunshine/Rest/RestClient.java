package com.tappgames.sunshine.Rest;

import com.tappgames.sunshine.BuildConfig;
import com.tappgames.sunshine.Utilities.Constants;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by johndaratzikis on 09/04/2017.
 */

public class RestClient {

    private static RestAPI REST_API;

    static {
        setupRestClient();
    }

    public static RestAPI call() {
        return REST_API;
    }

    private static void setupRestClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(Constants.REST_CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(Constants.REST_READ_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BuildConfig.HOST).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();

        REST_API = retrofit.create(RestAPI.class);
    }


}

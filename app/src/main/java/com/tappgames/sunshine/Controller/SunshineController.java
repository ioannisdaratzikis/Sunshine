package com.tappgames.sunshine.Controller;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tappgames.sunshine.Model.Forecast;
import com.tappgames.sunshine.Rest.Pojos.GetForecastResponse;
import com.tappgames.sunshine.Rest.RestCallback;
import com.tappgames.sunshine.Rest.RestClient;
import com.tappgames.sunshine.Utilities.Constants;

import java.util.ArrayList;

import retrofit2.Call;

public class SunshineController extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SunshineController(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public void fetchForecast(String lat, String lon, final ForecastComplection complection){

        Call<GetForecastResponse> call = RestClient.call().getForecast(lat, lon, Constants.cnt, Constants.appid);
        call.enqueue(new RestCallback<GetForecastResponse>() {
            @Override
            public void handleExeption(Throwable exception) {
                exception.printStackTrace();
            }

            @Override
            public void handleSuccess(GetForecastResponse response) {
                ArrayList<Forecast> forecasts = response.getList();
                complection.onResponse(forecasts);
            }

            @Override
            public void handleFailure(int statusCode, String statusMessage) {
                Log.d("ERROR", "failure");
            }
        });

    }

    public interface ForecastComplection {
        void onResponse(ArrayList<Forecast> complection);
    }

}

package com.tappgames.sunshine.Controller;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.ResultReceiver;
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
     */
    public SunshineController() {
        super("SunshineController");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        final Bundle bundle = new Bundle();

        Call<GetForecastResponse> call = RestClient.call().getForecast("35", "139", Constants.cnt, Constants.appid);
        call.enqueue(new RestCallback<GetForecastResponse>() {
            @Override
            public void handleExeption(Throwable exception) {
                exception.printStackTrace();
            }

            @Override
            public void handleSuccess(GetForecastResponse response) {
                ArrayList<Forecast> forecasts = response.getList();
                bundle.putParcelableArrayList("forecasts", forecasts);
                receiver.send(Constants.RESULT_CODE_SUCCESS, bundle);
            }

            @Override
            public void handleFailure(int statusCode, String statusMessage) {
                Log.d("ERROR", "failure");
            }
        });

    }

}

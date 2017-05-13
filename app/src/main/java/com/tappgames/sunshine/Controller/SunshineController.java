package com.tappgames.sunshine.Controller;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tappgames.sunshine.Database.CityContentProvider;
import com.tappgames.sunshine.Database.DBTableHelper;
import com.tappgames.sunshine.Database.ForecastContentProvider;
import com.tappgames.sunshine.Model.City;
import com.tappgames.sunshine.Model.Coordinators;
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
                //bundle.putParcelableArrayList("forecasts", forecasts);

                for(Forecast forecast: forecasts){

                    ContentValues valuesForecast = new ContentValues();
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_DT, forecast.getDt());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_PRESSURE, forecast.getPressure());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_HUMIDITY, forecast.getHumidity());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_SPEED, forecast.getSpeed());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_DEG, forecast.getDeg());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_CLOUDS, forecast.getClouds());

                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_DAY, forecast.getTmp().getDay());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MIN, forecast.getTmp().getMin());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MAX, forecast.getTmp().getMax());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_NIGHT, forecast.getTmp().getNight());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_EVE, forecast.getTmp().getEve());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MORN, forecast.getTmp().getMorn());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_WEATHER_ID, forecast.getWeather().get(0).getId());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_WEATHER_MAIN, forecast.getWeather().get(0).getMain());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_WEATHER_DESC, forecast.getWeather().get(0).getDescription());
                    valuesForecast.put(DBTableHelper.COLUMN_FORECAST_WEATHER_ICON, forecast.getWeather().get(0).getIcon());

                    getContentResolver().insert(ForecastContentProvider.CONTENT_URI, valuesForecast);

                    Log.d("DB FORECAST INSERT", valuesForecast.toString());

                }

                City city = response.getCity();
                ContentValues values = new ContentValues();
                values.put(DBTableHelper.COLUMN_CITY_CITY_ID, city.getId());
                values.put(DBTableHelper.COLUMN_CITY_NAME, city.getName());
                values.put(DBTableHelper.COLUMN_CITY_COUNTRY, city.getCountry());
                values.put(DBTableHelper.COLUMN_CITY_COORD_LAT, city.getCoord().getLat());
                values.put(DBTableHelper.COLUMN_CITY_COORD_LON, city.getCoord().getLon());
                values.put(DBTableHelper.COLUMN_CITY_POPULATION, city.getPopulation());

                getContentResolver().insert(CityContentProvider.CONTENT_URI, values);


                receiver.send(Constants.RESULT_CODE_SUCCESS, bundle);
            }

            @Override
            public void handleFailure(int statusCode, String statusMessage) {
                Log.d("ERROR", "failure");
            }
        });

    }

}

package com.tappgames.sunshine.View.Activities;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tappgames.sunshine.Controller.ForecastResultReceiver;
import com.tappgames.sunshine.Controller.SunshineController;
import com.tappgames.sunshine.Database.CityContentProvider;
import com.tappgames.sunshine.Database.DBTableHelper;
import com.tappgames.sunshine.Database.ForecastContentProvider;
import com.tappgames.sunshine.Model.City;
import com.tappgames.sunshine.Model.Coordinators;
import com.tappgames.sunshine.Model.Forecast;
import com.tappgames.sunshine.Model.Temperature;
import com.tappgames.sunshine.Model.Weather;
import com.tappgames.sunshine.R;
import com.tappgames.sunshine.Rest.Pojos.GetForecastResponse;
import com.tappgames.sunshine.Rest.RestCallback;
import com.tappgames.sunshine.Rest.RestClient;
import com.tappgames.sunshine.Utilities.Constants;
import com.tappgames.sunshine.View.Adapters.ForecastAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;

public class MainActivity extends SunshineActivity implements ForecastResultReceiver.Receiver{

    @InjectView(R.id.forecast_list)
    ListView mForecastList;

    ForecastResultReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mReceiver = new ForecastResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(MainActivity.this, SunshineController.class);
        intent.putExtra("receiver", mReceiver);
        startService(intent);

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == Constants.RESULT_CODE_SUCCESS){
            ArrayList<Forecast> forecasts = resultData.getParcelableArrayList("forecasts");
            final ForecastAdapter mForecastListAdapter = new ForecastAdapter(MainActivity.this, fetchForecastsFromDB());
            mForecastList.setAdapter(mForecastListAdapter);

//            ArrayList<Forecast> forecasts1 = new ArrayList<>();
//            forecasts1 = fetchForecastsFromDB();
//            for (Forecast f:forecasts1){
//                f.toString();
//            }

            City c = fetchCityFromDB();
            Log.d("CITY", c.getName());
        }

    }

    public ArrayList<Forecast> fetchForecastsFromDB(){

        ArrayList<Forecast> forecasts = new ArrayList<>();
        Cursor mCursor;

        String[] mProjection = {
                DBTableHelper.COLUMN_FORECAST_DT,
                DBTableHelper.COLUMN_FORECAST_PRESSURE,
                DBTableHelper.COLUMN_FORECAST_HUMIDITY,
                DBTableHelper.COLUMN_FORECAST_SPEED,
                DBTableHelper.COLUMN_FORECAST_DEG,
                DBTableHelper.COLUMN_FORECAST_CLOUDS,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_DAY,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MIN,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MAX,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_NIGHT,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_EVE,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MORN,
                DBTableHelper.COLUMN_FORECAST_WEATHER_ID,
                DBTableHelper.COLUMN_FORECAST_WEATHER_MAIN,
                DBTableHelper.COLUMN_FORECAST_WEATHER_DESC,
                DBTableHelper.COLUMN_FORECAST_WEATHER_ICON
        };

        mCursor = getContentResolver().query(
                ForecastContentProvider.CONTENT_URI,
                mProjection,
                null,
                null,
                null);

        if (mCursor != null) {
            while (mCursor.moveToNext()) {

                int forecastDTRetrievedFormDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_DT));
                double forecastPresureRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_PRESSURE));
                int forecastHumidityRetrievedFormDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_HUMIDITY));
                double forecastSpeedRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_SPEED));
                int forecastDEGRetrievedFormDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_DEG));
                int forecastCloudsRetrievedFormDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_CLOUDS));
                double forecastTempDayRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_DAY));
                double forecastTempMinRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MIN));
                double forecastTempMaxRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MAX));
                double forecastTempNightRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_NIGHT));
                double forecastTempEveRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_EVE));
                double forecastTempMornRetrievedFormDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MORN));
                int forecastWeatherIdRetrievedFormDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_WEATHER_ID));
                String forecastWeatherMainRetrievedFormDatabase = mCursor.getString(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_WEATHER_MAIN));
                String forecastWeatherDescRetrievedFormDatabase = mCursor.getString(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_WEATHER_DESC));
                String forecastWeatherIconRetrievedFormDatabase = mCursor.getString(mCursor.getColumnIndex(DBTableHelper.COLUMN_FORECAST_WEATHER_ICON));

                Forecast forecast = new Forecast();
                forecast.setDt(forecastDTRetrievedFormDatabase);
                forecast.setPressure(forecastPresureRetrievedFormDatabase);
                forecast.setHumidity(forecastHumidityRetrievedFormDatabase);
                forecast.setSpeed(forecastSpeedRetrievedFormDatabase);
                forecast.setDeg(forecastDEGRetrievedFormDatabase);
                forecast.setClouds(forecastCloudsRetrievedFormDatabase);

                Temperature temperature = new Temperature();
                temperature.setDay(forecastTempDayRetrievedFormDatabase);
                temperature.setMin(forecastTempMinRetrievedFormDatabase);
                temperature.setMax(forecastTempMaxRetrievedFormDatabase);
                temperature.setNight(forecastTempNightRetrievedFormDatabase);
                temperature.setEve(forecastTempEveRetrievedFormDatabase);
                temperature.setMorn(forecastTempMornRetrievedFormDatabase);
                forecast.setTmp(temperature);

                ArrayList<Weather> weatherArrayList = new ArrayList<>();
                Weather weather = new Weather();
                weather.setId(forecastWeatherIdRetrievedFormDatabase);
                weather.setMain(forecastWeatherMainRetrievedFormDatabase);
                weather.setDescription(forecastWeatherDescRetrievedFormDatabase);
                weather.setIcon(forecastWeatherIconRetrievedFormDatabase);
                weatherArrayList.add(weather);
                forecast.setWeather(weatherArrayList);

                forecasts.add(forecast);

                Log.d("Forecast", forecast.toString());

            }
        }

        if (mCursor.getCount() > 0){
            return forecasts;
        }else {
            return null;
        }

    }

    public City fetchCityFromDB(){

        City city = new City();
        Cursor mCursor;

        String[] mProjection = {
                DBTableHelper.COLUMN_CITY_CITY_ID,
                DBTableHelper.COLUMN_CITY_NAME,
                DBTableHelper.COLUMN_CITY_COORD_LAT,
                DBTableHelper.COLUMN_CITY_COORD_LON,
                DBTableHelper.COLUMN_CITY_COUNTRY,
                DBTableHelper.COLUMN_CITY_POPULATION
        };

        mCursor = getContentResolver().query(
                CityContentProvider.CONTENT_URI,
                mProjection,
                null,
                null,
                null);

        if (mCursor != null) {
            while (mCursor.moveToNext()) {

            String cityNameRetrievedFromDatabase = mCursor.getString(mCursor.getColumnIndex(DBTableHelper.COLUMN_CITY_NAME));
            int cityPopulationRetrievedFromDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_CITY_POPULATION));
            int cityIdRetrievedFromDatabase = mCursor.getInt(mCursor.getColumnIndex(DBTableHelper.COLUMN_CITY_CITY_ID));
            String cityCountryRetrievedFromDatabase = mCursor.getString(mCursor.getColumnIndex(DBTableHelper.COLUMN_CITY_COUNTRY));
            double cityLatRetrievedFromDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_CITY_COORD_LAT));
            double cityLonRetrievedFromDatabase = mCursor.getDouble(mCursor.getColumnIndex(DBTableHelper.COLUMN_CITY_COORD_LON));

            city.setName(cityNameRetrievedFromDatabase);
            city.setPopulation(cityPopulationRetrievedFromDatabase);
            city.setId(cityIdRetrievedFromDatabase);
            city.setCountry(cityCountryRetrievedFromDatabase);

            Coordinators coordinators = new Coordinators();
            coordinators.setLat(cityLatRetrievedFromDatabase);
            coordinators.setLon(cityLonRetrievedFromDatabase);

            city.setCoord(coordinators);

            }
        }

        if (mCursor.getCount() > 0){
            return city;
        }else {
            return null;
        }

    }

}

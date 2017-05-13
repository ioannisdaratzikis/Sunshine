package com.tappgames.sunshine.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tappgames.sunshine.Model.Coordinators;
import com.tappgames.sunshine.Model.Temperature;
import com.tappgames.sunshine.Model.Weather;

import java.util.ArrayList;

/**
 * Created by johndaratzikis on 05/05/2017.
 */

public class DBTableHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Sushine.db";

    public static final int DATABASE_VERSION = 1;

    public DBTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Table Forecasts
    public static final String TABLE_FORECASTS = "table_forecasts";
    public static final String COLUMN_FORECASTS_ID = "_id";
    public static final String COLUMN_FORECAST_DT = "forecast_dt";
    public static final String COLUMN_FORECAST_PRESSURE = "forecast_pressure";
    public static final String COLUMN_FORECAST_HUMIDITY = "forecast_humidity";
    public static final String COLUMN_FORECAST_SPEED = "forecast_speed";
    public static final String COLUMN_FORECAST_DEG = "deg";
    public static final String COLUMN_FORECAST_CLOUDS = "clouds";
    public static final String COLUMN_FORECAST_TEMPERATURE_DAY = "forecast_temp_day";
    public static final String COLUMN_FORECAST_TEMPERATURE_MIN = "forecast_temp_min";
    public static final String COLUMN_FORECAST_TEMPERATURE_MAX = "forecast_temp_max";
    public static final String COLUMN_FORECAST_TEMPERATURE_NIGHT = "forecast_temp_night";
    public static final String COLUMN_FORECAST_TEMPERATURE_EVE = "forecast_temp_eve";
    public static final String COLUMN_FORECAST_TEMPERATURE_MORN = "forecast_temp_morn";
    public static final String COLUMN_FORECAST_WEATHER_ID = "forecast_weather_id";
    public static final String COLUMN_FORECAST_WEATHER_MAIN = "forecast_weather_main";
    public static final String COLUMN_FORECAST_WEATHER_DESC = "forecast_weather_desc";
    public static final String COLUMN_FORECAST_WEATHER_ICON = "forecast_weather_icon";

    //Table city
    public static final String TABLE_CITY = "table_city";
    public static final String COLUMN_CITY_CITY_ID = "city_id";
    public static final String COLUMN_CITY_ID = "_id";
    public static final String COLUMN_CITY_NAME = "city_name";
    public static final String COLUMN_CITY_COORD_LON = "city_coord_lon";
    public static final String COLUMN_CITY_COORD_LAT = "city_coord_lat";
    public static final String COLUMN_CITY_COUNTRY = "city_country";
    public static final String COLUMN_CITY_POPULATION = "city_population";

    //SQL statement to create table users
    private static final String CREATE_TABLE_FORECASTS = "create table "
            + TABLE_FORECASTS + "("
            + COLUMN_FORECASTS_ID + " integer primary key autoincrement, "
            + COLUMN_FORECAST_DT + " text, "
            + COLUMN_FORECAST_PRESSURE + " real, "
            + COLUMN_FORECAST_HUMIDITY + " int, "
            + COLUMN_FORECAST_SPEED + " real, "
            + COLUMN_FORECAST_DEG + " int, "
            + COLUMN_FORECAST_CLOUDS + " int, "
            + COLUMN_FORECAST_TEMPERATURE_DAY + " real, "
            + COLUMN_FORECAST_TEMPERATURE_MIN + " real, "
            + COLUMN_FORECAST_TEMPERATURE_MAX + " real, "
            + COLUMN_FORECAST_TEMPERATURE_NIGHT + " real, "
            + COLUMN_FORECAST_TEMPERATURE_EVE + " real, "
            + COLUMN_FORECAST_TEMPERATURE_MORN + " real, "
            + COLUMN_FORECAST_WEATHER_ID + " int, "
            + COLUMN_FORECAST_WEATHER_MAIN + " text, "
            + COLUMN_FORECAST_WEATHER_DESC + " text, "
            + COLUMN_FORECAST_WEATHER_ICON + " text"
            + ");";


    //SQL statement to create table city
    private static final String CREATE_TABLE_CITY = "create table "
            + TABLE_CITY + "("
            + COLUMN_CITY_ID + " integer primary key autoincrement, "
            + COLUMN_CITY_CITY_ID + " int, "
            + COLUMN_CITY_NAME + " text, "
            + COLUMN_CITY_COORD_LAT + " int, "
            + COLUMN_CITY_COORD_LON + " int, "
            + COLUMN_CITY_POPULATION + " int, "
            + COLUMN_CITY_COUNTRY + " text"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FORECASTS);
        db.execSQL(CREATE_TABLE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(DBTableHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORECASTS);
        onCreate(db);
    }
}

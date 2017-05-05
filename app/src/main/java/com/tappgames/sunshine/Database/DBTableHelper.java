package com.tappgames.sunshine.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String COLUMN_FORECAST_TEMPERATURE = "forecast_temp";
    public static final String COLUMN_FORECAST_PRESSURE = "forecast_pressure";
    public static final String COLUMN_FORECAST_HUMIDITY = "forecast_humidity";
    public static final String COLUMN_FORECAST_WEATHER = "forecast_weather";
    public static final String COLUMN_FORECAST_SPEED = "forecast_speed";
    public static final String COLUMN_FORECAST_DEG = "deg";
    public static final String COLUMN_FORECAST_CLOUDS = "clouds";

    //Table Temperatures
    public static final String TABLE_TEMPERATURES = "table_temperatures";
    public static final String COLUMN_TEMPERATURE_ID = "_id";
    public static final String COLUMN_TEMPERATURE_DAY = "temperature_day";
    public static final String COLUMN_TEMPERATURE_MIN = "temperature_min";
    public static final String COLUMN_TEMPERATURE_MAX = "temperature_max";
    public static final String COLUMN_TEMPERATURE_NIGHT = "temperature_night";
    public static final String COLUMN_TEMPERATURE_EVE = "temperature_eve";
    public static final String COLUMN_TEMPERATURE_MORN = "temperature_morn";

    //Table Weather
    public static final String TABLE_WEATHER = "table_weather";
    public static final String COLUMN_WEATHER_ID = "_id";
    public static final String COLUMN_WEATHER_WEATHER_ID = "weather_id";
    public static final String COLUMN_WEATHER_MAIN = "weather_main";
    public static final String COLUMN_WEATHER_DESCRIPTION = "weather_description";
    public static final String COLUMN_WEATHER_ICON = "weather_icon";

    //SQL statement to create table users
    private static final String CREATE_TABLE_FORECASTS = "create table "
            + TABLE_FORECASTS + "("
            + COLUMN_FORECASTS_ID + " integer primary key autoincrement, "
            + COLUMN_FORECAST_DT + " text, "
            + COLUMN_FORECAST_TEMPERATURE + " int, "
            + COLUMN_FORECAST_PRESSURE + " real, "
            + COLUMN_FORECAST_HUMIDITY + " int, "
            + COLUMN_FORECAST_WEATHER + " int, "
            + COLUMN_FORECAST_SPEED + " real, "
            + COLUMN_FORECAST_DEG + " int, "
            + COLUMN_FORECAST_CLOUDS + " int, "
            + "foreign key(" + COLUMN_FORECAST_TEMPERATURE + ")" + " references " + TABLE_TEMPERATURES + "(" + COLUMN_TEMPERATURE_ID + "), "
            + "foreign key(" + COLUMN_FORECAST_WEATHER + ")" + " references " + TABLE_WEATHER + "(" + COLUMN_WEATHER_ID + ")"
            + ");";

    //SQL statement to create table temperatures
    private static final String CREATE_TABLE_TEMPERATURES = "create table "
            + TABLE_TEMPERATURES + "("
            + COLUMN_TEMPERATURE_ID + " integer primary key autoincrement, "
            + COLUMN_TEMPERATURE_DAY + " real, "
            + COLUMN_TEMPERATURE_MIN + " real, "
            + COLUMN_TEMPERATURE_MAX + " real, "
            + COLUMN_TEMPERATURE_NIGHT + " real, "
            + COLUMN_TEMPERATURE_EVE + " real, "
            + COLUMN_TEMPERATURE_MORN + " real"
            + ");";

    //SQL statement to create table weather
    private static final String CREATE_TABLE_WEATHER = "create table "
            + TABLE_WEATHER + "("
            + COLUMN_WEATHER_ID + " integer primary key autoincrement, "
            + COLUMN_WEATHER_WEATHER_ID + " int, "
            + COLUMN_WEATHER_MAIN + " text, "
            + COLUMN_WEATHER_DESCRIPTION + " text, "
            + COLUMN_WEATHER_ICON + " text"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FORECASTS);
        db.execSQL(CREATE_TABLE_TEMPERATURES);
        db.execSQL(CREATE_TABLE_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(DBTableHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORECASTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(db);
    }
}

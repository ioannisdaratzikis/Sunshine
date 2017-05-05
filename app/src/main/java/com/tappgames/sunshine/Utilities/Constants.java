package com.tappgames.sunshine.Utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by johndaratzikis on 09/04/2017.
 */

public class Constants {

//    public static final String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&appid=f978537a55e9535979fc541fefcce46d";
//    public static final String HOST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
//    public static final String PATH_URL = "data/2.5/forecast/daily?";
//    public static final String QUERY_PARAM = "daily";
//    public static final String DAYS_PARAM = "cnt";
//    public static final String APPID_PARAM = "appid";
//    public static final String LAT_PARAM = "lat";
//    public static final String LON_PARAM = "lon";

    public static final int REST_CONNECT_TIMEOUT = 30;
    public static final int REST_READ_TIMEOUT = 30;
    public static final String LOG_REST_TAG = "Retrofit";

    public static final String REST_ICON_URL = "http://openweathermap.org/img/w/";

    public static final String daily = "daily";
    public static final String lat = "35";
    public static final String lon = "139";
    public static final String cnt = "10";
    public static final String appid = "f978537a55e9535979fc541fefcce46d";

    public static final int RESULT_CODE_SUCCESS = 1;


}

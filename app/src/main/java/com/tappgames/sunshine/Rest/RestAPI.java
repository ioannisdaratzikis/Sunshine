package com.tappgames.sunshine.Rest;

import com.tappgames.sunshine.Rest.Pojos.GetForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by johndaratzikis on 04/05/2017.
 */

public interface RestAPI {

    @GET("data/2.5/forecast/daily")
    Call<GetForecastResponse> getForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("cnt") String days, @Query("appid") String appid);

}

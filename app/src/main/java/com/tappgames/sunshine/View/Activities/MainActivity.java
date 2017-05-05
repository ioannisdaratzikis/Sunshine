package com.tappgames.sunshine.View.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tappgames.sunshine.Model.Forecast;
import com.tappgames.sunshine.R;
import com.tappgames.sunshine.Rest.Pojos.GetForecastResponse;
import com.tappgames.sunshine.Rest.RestCallback;
import com.tappgames.sunshine.Rest.RestClient;
import com.tappgames.sunshine.View.Adapters.ForecastAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;

public class MainActivity extends SunshineActivity {

    @InjectView(R.id.forecast_list)
    ListView mForecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Call<GetForecastResponse> call = RestClient.call().getForecast("35", "139","10","f978537a55e9535979fc541fefcce46d");
        call.enqueue(new RestCallback<GetForecastResponse>() {
            @Override
            public void handleExeption(Throwable exception) {
                exception.printStackTrace();
            }

            @Override
            public void handleSuccess(GetForecastResponse response) {
                ArrayList<Forecast> forecasts = response.getList();
                final ForecastAdapter mForecastListAdapter = new ForecastAdapter(MainActivity.this, forecasts);
                mForecastList.setAdapter(mForecastListAdapter);

            }

            @Override
            public void handleFailure(int statusCode, String statusMessage) {
                Log.d("ERROR", "failure");
            }
        });

    }

}

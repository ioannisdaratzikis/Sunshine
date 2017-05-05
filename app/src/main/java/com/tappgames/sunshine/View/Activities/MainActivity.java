package com.tappgames.sunshine.View.Activities;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tappgames.sunshine.Controller.ForecastResultReceiver;
import com.tappgames.sunshine.Controller.SunshineController;
import com.tappgames.sunshine.Model.Forecast;
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
            final ForecastAdapter mForecastListAdapter = new ForecastAdapter(MainActivity.this, forecasts);
            mForecastList.setAdapter(mForecastListAdapter);
        }

    }
}

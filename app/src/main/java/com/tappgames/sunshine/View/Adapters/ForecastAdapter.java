package com.tappgames.sunshine.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tappgames.sunshine.Model.Forecast;
import com.tappgames.sunshine.R;
import com.tappgames.sunshine.Utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by johndaratzikis on 27/04/2017.
 */

public class ForecastAdapter extends ArrayAdapter<Forecast>{

    List<Forecast> forecast = new ArrayList<Forecast>();
    Context context;

    public ForecastAdapter(Context context,ArrayList<Forecast> forecast) {
        super(context, R.layout.forecast_list_item, forecast);
        this.context = context;
        this.forecast = forecast;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ForecastAdapter.ViewHolder holder;

        if (view != null){
            holder = (ForecastAdapter.ViewHolder) view.getTag();
        }else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.forecast_list_item, parent, false);

            holder = new ForecastAdapter.ViewHolder(view);
            view.setTag(holder);
        }
        holder.mainData.setText("Main: " + forecast.get(position).getWeather().get(0).getMain().toString());
        holder.maxTempData.setText("Max: " + forecast.get(position).getTmp().getMax().toString());
        holder.minTempData.setText("Min: " + forecast.get(position).getTmp().getMin().toString());

        Picasso.with(context).load(Constants.REST_ICON_URL + forecast.get(position).getWeather().get(0).getIcon().toString() + ".png").into(holder.iconImageView);

        return view;
    }

    static class ViewHolder{
        @InjectView(R.id.tv_main)
        TextView mainData;
        @InjectView(R.id.tv_max_temp)
        TextView maxTempData;
        @InjectView(R.id.tv_min_temp)
        TextView minTempData;
        @InjectView(R.id.iv_image_view)
        ImageView iconImageView;
        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }
}

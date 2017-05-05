package com.tappgames.sunshine.Rest.Pojos;

import com.tappgames.sunshine.Model.Forecast;

import java.util.ArrayList;

/**
 * Created by johndaratzikis on 04/05/2017.
 */

public class GetForecastResponse extends GenericResponse{
    private ArrayList<Forecast> list;

    public ArrayList<Forecast> getList() {
        return list;
    }

    public void setList(ArrayList<Forecast> list) {
        this.list = list;
    }
}

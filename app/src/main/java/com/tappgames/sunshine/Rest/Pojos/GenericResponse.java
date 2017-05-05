package com.tappgames.sunshine.Rest.Pojos;

import com.tappgames.sunshine.Model.City;

/**
 * Created by johndaratzikis on 04/05/2017.
 */

public class GenericResponse {
    private City city;
    private String cod;
    private Double message;
    private int cnt;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

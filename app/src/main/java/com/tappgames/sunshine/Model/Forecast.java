package com.tappgames.sunshine.Model;

import java.util.ArrayList;

/**
 * Created by johndaratzikis on 09/04/2017.
 */

public class Forecast {

    private int dt;
    private Temperature temp;
    private Double pressure;
    private int humidity;
    private ArrayList<Weather> weather;
    private Double speed;
    private int deg;
    private int clouds;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Temperature getTmp() {
        return temp;
    }

    public void setTmp(Temperature temp) {
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }
}

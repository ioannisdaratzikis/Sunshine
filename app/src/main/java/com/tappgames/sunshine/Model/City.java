package com.tappgames.sunshine.Model;

/**
 * Created by johndaratzikis on 04/05/2017.
 */

public class City {

    private int id;
    private String name;
    private Coordinators coord;
    private String country;
    private int population;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinators getCoord() {
        return coord;
    }

    public void setCoord(Coordinators coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}

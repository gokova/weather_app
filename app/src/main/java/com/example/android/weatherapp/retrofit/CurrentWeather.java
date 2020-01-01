package com.example.android.weatherapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather {
    @SerializedName("name")
    private String cityName;

    @SerializedName("weather")
    private List<Condition> conditions;

    @SerializedName("main")
    private Temperature temperature;

    @SerializedName("wind")
    private Wind wind;

    public CurrentWeather(String cityName, List<Condition> conditions, Temperature temperature, Wind wind) {
        this.cityName = cityName;
        this.conditions = conditions;
        this.temperature = temperature;
        this.wind = wind;
    }

    public String getCityName() {
        return cityName;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Wind getWind() {
        return wind;
    }
}

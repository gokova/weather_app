package com.example.android.weatherapp.retrofit;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("temp")
    private double currentTemperature;

    @SerializedName("temp_min")
    private double minTemperature;

    @SerializedName("temp_max")
    private double maxTemperature;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("humidity")
    private double humidity;

    public Temperature(double currentTemperature, double minTemperature, double maxTemperature, double pressure, double humidity) {
        this.currentTemperature = currentTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }
}

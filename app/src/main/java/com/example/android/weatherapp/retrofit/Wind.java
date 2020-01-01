package com.example.android.weatherapp.retrofit;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private double speed;

    @SerializedName("deg")
    private double degree;

    public Wind(double speed, double degree) {
        this.speed = speed;
        this.degree = degree;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDegree() {
        return degree;
    }
}

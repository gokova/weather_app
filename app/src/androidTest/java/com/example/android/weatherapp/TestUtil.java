package com.example.android.weatherapp;

import com.example.android.weatherapp.db.entity.WeatherEntity;

import java.util.Calendar;

public class TestUtil {

    public static WeatherEntity createWeather(int id) {
        return new WeatherEntity(id,
                "Izmir",
                "Sunny",
                "Sunny",
                "04N",
                15.35f,
                10.25f,
                20.45f,
                1000f,
                73f,
                25.5f,
                180,
                Calendar.getInstance().getTime());
    }
}

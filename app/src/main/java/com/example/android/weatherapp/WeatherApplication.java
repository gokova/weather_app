package com.example.android.weatherapp;

import android.app.Application;

import com.example.android.weatherapp.db.AppDatabase;
import com.example.android.weatherapp.db.WeatherRepository;
import com.example.android.weatherapp.retrofit.OpenWeatherClient;

import retrofit2.Retrofit;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Retrofit getRetrofit() {
        return OpenWeatherClient.getRetrofitInstance();
    }

    public AppDatabase getRoomDatabase() {
        return AppDatabase.getInstance(getApplicationContext());
    }

    public WeatherRepository getWeatherRepository() {
        return WeatherRepository.getInstance(getRoomDatabase());
    }
}

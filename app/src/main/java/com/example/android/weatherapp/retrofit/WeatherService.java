package com.example.android.weatherapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@Query("appid") String api_key,
                                           @Query("units") String units,
                                           @Query("lang") String language_code,
                                           @Query("q") String location);
}

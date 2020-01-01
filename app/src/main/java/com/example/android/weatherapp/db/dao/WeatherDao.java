package com.example.android.weatherapp.db.dao;

import com.example.android.weatherapp.db.entity.WeatherEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WeatherDao {
    @Insert()
    void insert(@NonNull WeatherEntity weather);

    @Query("DELETE FROM weathers WHERE id = :weatherId")
    void delete(int weatherId);

    @Query("DELETE FROM weathers")
    void deleteAll();

    @Query("SELECT * FROM weathers")
    LiveData<List<WeatherEntity>> loadAllWeathers();

    @Query("SELECT * FROM weathers WHERE id = :weatherId")
    LiveData<WeatherEntity> loadWeather(int weatherId);
}

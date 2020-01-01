package com.example.android.weatherapp.db;

import com.example.android.weatherapp.db.entity.WeatherEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class WeatherRepository {

    private static WeatherRepository weatherRepository;
    private final AppDatabase database;
    private MediatorLiveData<List<WeatherEntity>> weathers;

    private WeatherRepository(final AppDatabase database) {
        this.database = database;
        weathers = new MediatorLiveData<>();

        weathers.addSource(this.database.weatherDao().loadAllWeathers(),
                weatherEntities -> weathers.postValue(weatherEntities));
    }

    public static WeatherRepository getInstance(final AppDatabase database) {
        if (weatherRepository == null) {
            synchronized (WeatherRepository.class) {
                if (weatherRepository == null) {
                    weatherRepository = new WeatherRepository(database);
                }
            }
        }

        return weatherRepository;
    }

    public LiveData<List<WeatherEntity>> getWeathers() {
        return weathers;
    }

    public void insert(WeatherEntity weather) {
        AppDatabase.databaseExecutor.execute(() -> database.weatherDao().insert(weather));
    }

    public void delete(int weatherId) {
        AppDatabase.databaseExecutor.execute(() -> database.weatherDao().delete(weatherId));
    }

    public void deleteAll() {
        AppDatabase.databaseExecutor.execute(() -> database.weatherDao().deleteAll());
    }

    public LiveData<List<WeatherEntity>> loadAllWeathers() {
        return database.weatherDao().loadAllWeathers();
    }

    public LiveData<WeatherEntity> loadWeather(int weatherId) {
        return database.weatherDao().loadWeather(weatherId);
    }
}

package com.example.android.weatherapp.viewmodel;

import com.example.android.weatherapp.db.WeatherRepository;
import com.example.android.weatherapp.db.entity.WeatherEntity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class WeatherDetailsViewModel extends ViewModel {

    private MediatorLiveData<WeatherEntity> currentWeather;

    WeatherDetailsViewModel(int weatherId, WeatherRepository repository) {
        currentWeather = new MediatorLiveData<>();

        currentWeather.addSource(repository.loadWeather(weatherId),
                weather -> currentWeather.postValue(weather));
        Timber.d("Weather details view model is created");
    }

    public LiveData<WeatherEntity> getCurrentWeather() {
        return currentWeather;
    }
}

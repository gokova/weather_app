package com.example.android.weatherapp.viewmodel;

import com.example.android.weatherapp.db.WeatherRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WeatherDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final int weatherId;
    private final WeatherRepository weatherRepository;

    public WeatherDetailsViewModelFactory(int weatherId, WeatherRepository repository) {
        this.weatherId = weatherId;
        weatherRepository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherDetailsViewModel.class)) {
            return (T) new WeatherDetailsViewModel(weatherId, weatherRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

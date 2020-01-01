package com.example.android.weatherapp.viewmodel;

import com.example.android.weatherapp.db.WeatherRepository;
import com.example.android.weatherapp.retrofit.WeatherService;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WeatherListViewModelFactory implements ViewModelProvider.Factory {

    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;

    public WeatherListViewModelFactory(WeatherService service, WeatherRepository repository) {
        weatherService = service;
        weatherRepository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherListViewModel.class)) {
            return (T) new WeatherListViewModel(weatherService, weatherRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

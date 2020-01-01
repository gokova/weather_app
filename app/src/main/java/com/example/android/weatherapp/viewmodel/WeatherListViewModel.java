package com.example.android.weatherapp.viewmodel;

import com.example.android.weatherapp.db.WeatherRepository;
import com.example.android.weatherapp.db.entity.WeatherEntity;
import com.example.android.weatherapp.retrofit.CurrentWeather;
import com.example.android.weatherapp.retrofit.WeatherService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class WeatherListViewModel extends ViewModel {

    private WeatherService weatherService;
    private WeatherRepository weatherRepository;
    private MutableLiveData<CurrentWeather> currentWeather;
    private MutableLiveData<String> errorMessage;
    private MediatorLiveData<List<WeatherEntity>> weathers;

    private static final String LOCATION = "Izmir,TR";
    private static final String LANGUAGE = "en";
    private static final String UNITS = "metric";
    private static final String API_KEY = "e8d78e5d36c3f94e3ab622555b0deda5";

    WeatherListViewModel(WeatherService service, WeatherRepository repository) {
        weatherService = service;
        weatherRepository = repository;
        currentWeather = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        weathers = new MediatorLiveData<>();

        weathers.addSource(weatherRepository.getWeathers(),
                weatherEntities -> weathers.postValue(weatherEntities));
        Timber.d("Weather list view model is created");
    }

    public LiveData<CurrentWeather> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<WeatherEntity>> getWeathers() {
        return weathers;
    }

    public void fetchCurrentWeather() {
        Timber.d("Fetching current weather");
        Call<CurrentWeather> call = weatherService.getCurrentWeather(API_KEY, UNITS, LANGUAGE, LOCATION);

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeather> call, @NonNull Response<CurrentWeather> response) {
                Timber.d("Fetch current weather on response");
                if (response.isSuccessful()) {
                    currentWeather.postValue(response.body());
                } else {
                    errorMessage.postValue(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable t) {
                Timber.d("Fetch current weather on failure");
                errorMessage.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void insert(WeatherEntity weather) {
        Timber.d("Inserting new weather");
        weatherRepository.insert(weather);
    }

    public void delete(int weatherId) {
        Timber.d("Deleting weather with id");
        weatherRepository.delete(weatherId);
    }

    public void deleteAll() {
        Timber.d("Deleting all data");
        weatherRepository.deleteAll();
    }

    public LiveData<List<WeatherEntity>> loadAllWeathers() {
        Timber.d("Loading weathers from database");
        return weatherRepository.loadAllWeathers();
    }

    public LiveData<WeatherEntity> loadWeather(int weatherId) {
        Timber.d("Loading weather with id");
        return weatherRepository.loadWeather(weatherId);
    }
}

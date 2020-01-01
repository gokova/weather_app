package com.example.android.weatherapp.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.WeatherApplication;
import com.example.android.weatherapp.db.entity.WeatherEntity;
import com.example.android.weatherapp.viewmodel.WeatherDetailsViewModel;
import com.example.android.weatherapp.viewmodel.WeatherDetailsViewModelFactory;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

public class WeatherDetailsActivity extends AppCompatActivity {

    private int weatherId;
    private TextView cityName;
    private TextView temperature;
    private TextView minMaxTemperature;
    private TextView condition;
    private TextView pressure;
    private TextView humidity;
    private TextView windSpeed;
    private TextView windDirection;
    private TextView createdOn;
    private ImageView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
        getExtras();
        setViewModel();
    }

    private void findViews() {
        cityName = findViewById(R.id.tv_city_name);
        temperature = findViewById(R.id.tv_temperature_value);
        minMaxTemperature = findViewById(R.id.tv_min_max_temp_value);
        condition = findViewById(R.id.tv_condition_value);
        pressure = findViewById(R.id.tv_pressure_value);
        humidity = findViewById(R.id.tv_humidity_value);
        windSpeed = findViewById(R.id.tv_wind_speed_value);
        windDirection = findViewById(R.id.tv_wind_direction_value);
        createdOn = findViewById(R.id.tv_created_on_value);
        weatherIcon = findViewById(R.id.iv_weather_icon);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        weatherId = 0;
        if (extras != null) {
            weatherId = extras.getInt(WeatherListActivity.WEATHER_ID_KEY, 0);
        }
    }

    private void setViewModel() {
        Timber.d("Creating view model");
        WeatherDetailsViewModelFactory factory = new WeatherDetailsViewModelFactory(
                weatherId,
                ((WeatherApplication) getApplication()).getWeatherRepository()
        );
        WeatherDetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(WeatherDetailsViewModel.class);

        viewModel.getCurrentWeather().observe(this, currentWeather -> {
            if (currentWeather != null) {
                setDataOnViews(currentWeather);
            }
        });

        Timber.d("View model is created");
    }

    private void setDataOnViews(WeatherEntity currentWeather) {
        cityName.setText(currentWeather.getCityName());
        temperature.setText(getString(R.string.temperature_value, Math.round(currentWeather.getCurrentTemperature())));
        minMaxTemperature.setText(getString(R.string.min_max_temperature_value, Math.round(currentWeather.getMinTemperature()), Math.round(currentWeather.getMaxTemperature())));
        condition.setText(currentWeather.getCondition());
        pressure.setText(getString(R.string.pressure_value, Math.round(currentWeather.getPressure())));
        humidity.setText(getString(R.string.humidity_value, Math.round(currentWeather.getHumidity())));
        windSpeed.setText(getString(R.string.speed_value, Math.round(currentWeather.getWindSpeed())));
        windDirection.setText(getWindDegreeDirection(currentWeather.getWindDegree()));
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(this);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);
        createdOn.setText(String.format(Locale.getDefault(), "%s %s ", dateFormat.format(currentWeather.getCreatedOn()), timeFormat.format(currentWeather.getCreatedOn())));
        Picasso.get().load("http://openweathermap.org/img/wn/" + currentWeather.getIcon() + "@2x.png")
                .placeholder((R.drawable.ic_broken_image))
                .error(R.drawable.ic_broken_image)
                .into(weatherIcon);
    }

    private String getWindDegreeDirection(double degree) {
        if (degree <= 20) {
            return getString(R.string.east);
        } else if (degree <= 70) {
            return getString(R.string.north_east);
        } else if (degree <= 110) {
            return getString(R.string.north);
        } else if (degree <= 160) {
            return getString(R.string.north_west);
        } else if (degree <= 200) {
            return getString(R.string.west);
        } else if (degree <= 250) {
            return getString(R.string.south_west);
        } else if (degree <= 290) {
            return getString(R.string.south);
        } else if (degree <= 340) {
            return getString(R.string.south_east);
        } else {
            return getString(R.string.east);
        }
    }
}

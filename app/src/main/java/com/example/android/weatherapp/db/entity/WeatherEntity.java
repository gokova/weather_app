package com.example.android.weatherapp.db.entity;

import com.example.android.weatherapp.retrofit.CurrentWeather;

import java.util.Calendar;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "weathers")
public class WeatherEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String cityName;//
    private String condition;//
    private String description;//
    private String icon;
    private double currentTemperature;//
    private double minTemperature;//
    private double maxTemperature;//
    private double pressure;
    private double humidity;
    private double windSpeed;
    private double windDegree;
    private Date createdOn;

    public WeatherEntity(int id,
                         String cityName,
                         String condition,
                         String description,
                         String icon,
                         double currentTemperature,
                         double minTemperature,
                         double maxTemperature,
                         double pressure,
                         double humidity,
                         double windSpeed,
                         double windDegree,
                         Date createdOn) {
        this.id = id;
        this.cityName = cityName;
        this.condition = condition;
        this.description = description;
        this.icon = icon;
        this.currentTemperature = currentTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.createdOn = createdOn;
    }

    @Ignore
    public WeatherEntity(CurrentWeather currentWeather) {
        this.cityName = currentWeather.getCityName();
        this.condition = currentWeather.getConditions().get(0).getCondition();
        this.description = currentWeather.getConditions().get(0).getDescription();
        this.icon = currentWeather.getConditions().get(0).getIcon();
        this.currentTemperature = currentWeather.getTemperature().getCurrentTemperature();
        this.minTemperature = currentWeather.getTemperature().getMinTemperature();
        this.maxTemperature = currentWeather.getTemperature().getMaxTemperature();
        this.pressure = currentWeather.getTemperature().getPressure();
        this.humidity = currentWeather.getTemperature().getHumidity();
        this.windSpeed = currentWeather.getWind().getSpeed();
        this.windDegree = currentWeather.getWind().getDegree();
        this.createdOn = Calendar.getInstance().getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}

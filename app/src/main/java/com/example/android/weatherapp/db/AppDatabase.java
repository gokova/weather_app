package com.example.android.weatherapp.db;

import android.content.Context;

import com.example.android.weatherapp.db.dao.WeatherDao;
import com.example.android.weatherapp.db.entity.WeatherEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {WeatherEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();

    private static final String DATABASE_NAME = "weather_db";
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(final Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }

        return appDatabase;
    }
}

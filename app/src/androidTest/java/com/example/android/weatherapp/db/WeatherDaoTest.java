package com.example.android.weatherapp.db;

import com.example.android.weatherapp.TestUtil;
import com.example.android.weatherapp.db.dao.WeatherDao;
import com.example.android.weatherapp.db.entity.WeatherEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.android.weatherapp.LiveDataTestUtil.getValue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WeatherDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private WeatherDao weatherDao;

    @Before
    public void before() {
        appDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        weatherDao = appDatabase.weatherDao();
    }

    @After
    public void after() {
        appDatabase.close();
    }

    @Test
    public void loadWeathersWhenNoWeatherInserted() throws InterruptedException {
        List<WeatherEntity> weathers = getValue(weatherDao.loadAllWeathers());
        assertTrue(weathers.isEmpty());
    }

    @Test
    public void insertWeatherAndLoadWithId() throws Exception {
        WeatherEntity newWeather = TestUtil.createWeather(1);
        weatherDao.insert(newWeather);
        WeatherEntity queriedWeather = getValue(weatherDao.loadWeather(1));
        assertEquals(queriedWeather.getId(), newWeather.getId());
        assertEquals(queriedWeather.getCityName(), newWeather.getCityName());
        assertEquals(queriedWeather.getCreatedOn(), newWeather.getCreatedOn());
    }

    @Test
    public void insertWeathersAndLoadAll() throws Exception {
        WeatherEntity newWeather = TestUtil.createWeather(1);
        weatherDao.insert(newWeather);
        newWeather = TestUtil.createWeather(2);
        weatherDao.insert(newWeather);
        List<WeatherEntity> weathers = getValue(weatherDao.loadAllWeathers());
        assertEquals(weathers.size(), 2);
    }

    @Test
    public void insertWeathersAndDeleteOne() throws Exception {
        WeatherEntity newWeather = TestUtil.createWeather(1);
        weatherDao.insert(newWeather);
        newWeather = TestUtil.createWeather(2);
        weatherDao.insert(newWeather);
        weatherDao.delete(1);
        List<WeatherEntity> weathers = getValue(weatherDao.loadAllWeathers());
        assertEquals(weathers.size(), 1);
        assertEquals(weathers.get(0).getId(), 2);
    }

    @Test
    public void insertWeathersAndDeleteAll() throws Exception {
        WeatherEntity newWeather = TestUtil.createWeather(1);
        weatherDao.insert(newWeather);
        newWeather = TestUtil.createWeather(2);
        weatherDao.insert(newWeather);
        weatherDao.deleteAll();
        List<WeatherEntity> weathers = getValue(weatherDao.loadAllWeathers());
        assertTrue(weathers.isEmpty());
    }
}

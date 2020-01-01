package com.example.android.weatherapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.WeatherApplication;
import com.example.android.weatherapp.db.entity.WeatherEntity;
import com.example.android.weatherapp.retrofit.WeatherService;
import com.example.android.weatherapp.viewmodel.WeatherListViewModel;
import com.example.android.weatherapp.viewmodel.WeatherListViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class WeatherListActivity extends AppCompatActivity implements WeatherListAdapter.OnOptionsMenuClickListener {

    private ProgressDialog progressDialog;
    private WeatherListViewModel viewModel;
    private TextView emptyView;
    private RecyclerView recyclerView;
    private WeatherListAdapter adapter;

    public static final String WEATHER_ID_KEY = "weather_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        setSupportActionBar(findViewById(R.id.toolbar));
        setRecyclerView();
        setViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_fetch_weather) {
            Timber.d("Fetch current weather is clicked");
            fetchCurrentWeather();
        } else if (id == R.id.action_delete_all) {
            Timber.d("Delete all data is clicked");
            viewModel.deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, WeatherEntity weatherEntity) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.weather_list_popup_menu);
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_more_details:
                    Intent detailsIntent = new Intent(getApplicationContext(), WeatherDetailsActivity.class);
                    detailsIntent.putExtra(WeatherListActivity.WEATHER_ID_KEY, weatherEntity.getId());
                    startActivity(detailsIntent);
                    return true;
                case R.id.action_delete:
                    viewModel.delete(weatherEntity.getId());
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    private void setRecyclerView() {
        emptyView = findViewById(R.id.empty_view);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new WeatherListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViewModel() {
        Timber.d("Creating view model");
        WeatherListViewModelFactory factory = new WeatherListViewModelFactory(
                ((WeatherApplication) getApplication()).getRetrofit().create(WeatherService.class),
                ((WeatherApplication) getApplication()).getWeatherRepository()
        );
        viewModel = ViewModelProviders.of(this, factory).get(WeatherListViewModel.class);

        viewModel.getCurrentWeather().observe(this, currentWeather -> {
            progressDialog.dismiss();
            if (currentWeather != null) {
                viewModel.insert(new WeatherEntity(currentWeather));
            }
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            progressDialog.dismiss();
            Snackbar.make(getWindow().getDecorView().getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
        });

        viewModel.getWeathers().observe(this, weatherEntities -> {
            if (weatherEntities.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
            adapter.setWeathers(weatherEntities);
        });

        Timber.d("View model is created");
    }

    private void fetchCurrentWeather() {
        progressDialog = new ProgressDialog(WeatherListActivity.this);
        progressDialog.setMessage(getString(R.string.fetching_current_weather));
        progressDialog.show();

        viewModel.fetchCurrentWeather();
    }
}

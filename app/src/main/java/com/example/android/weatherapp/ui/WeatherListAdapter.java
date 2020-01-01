package com.example.android.weatherapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.db.entity.WeatherEntity;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private List<WeatherEntity> weathers;
    private OnOptionsMenuClickListener optionsMenuClickListener;

    WeatherListAdapter(Context context, OnOptionsMenuClickListener optionsMenuClickListener) {
        this.context = context;
        this.optionsMenuClickListener = optionsMenuClickListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherEntity current = weathers.get(position);
        holder.currentTemperature.setText(context.getString(R.string.temperature_value, Math.round(current.getCurrentTemperature())));
        holder.condition.setText(current.getCondition());
        holder.maxTemperature.setText(context.getString(R.string.temperature_value, Math.round(current.getMaxTemperature())));
        holder.minTemperature.setText(context.getString(R.string.temperature_value, Math.round(current.getMinTemperature())));
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
        holder.createdOn.setText(String.format(Locale.getDefault(), "%s %s ", dateFormat.format(current.getCreatedOn()), timeFormat.format(current.getCreatedOn())));
        holder.moreOptionsBtn.setOnClickListener(view -> optionsMenuClickListener.onClick(view, current));
        if (holder.windSpeed != null) {
            holder.windSpeed.setText(context.getString(R.string.speed_value, Math.round(current.getWindSpeed())));
        }
        if (holder.windDirection != null) {
            holder.windDirection.setText(getWindDegreeDirection(current.getWindDegree()));
        }

        Picasso.get().load("http://openweathermap.org/img/wn/" + current.getIcon() + "@2x.png")
                .placeholder((R.drawable.ic_broken_image))
                .error(R.drawable.ic_broken_image)
                .into(holder.weatherIcon);
    }

    @Override
    public int getItemCount() {
        if (weathers != null)
            return weathers.size();
        else return 0;
    }

    private String getWindDegreeDirection(double degree) {
        if (degree <= 20) {
            return context.getString(R.string.east);
        } else if (degree <= 70) {
            return context.getString(R.string.north_east);
        } else if (degree <= 110) {
            return context.getString(R.string.north);
        } else if (degree <= 160) {
            return context.getString(R.string.north_west);
        } else if (degree <= 200) {
            return context.getString(R.string.west);
        } else if (degree <= 250) {
            return context.getString(R.string.south_west);
        } else if (degree <= 290) {
            return context.getString(R.string.south);
        } else if (degree <= 340) {
            return context.getString(R.string.south_east);
        } else {
            return context.getString(R.string.east);
        }
    }

    void setWeathers(List<WeatherEntity> weathers) {
        this.weathers = weathers;
        notifyDataSetChanged();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        private final ImageView weatherIcon;
        private final TextView currentTemperature;
        private final TextView condition;
        private final TextView maxTemperature;
        private final TextView minTemperature;
        private final TextView windSpeed;
        private final TextView windDirection;
        private final TextView createdOn;
        private final AppCompatImageButton moreOptionsBtn;

        private WeatherViewHolder(View itemView) {
            super(itemView);
            weatherIcon = itemView.findViewById(R.id.iv_weather_icon);
            currentTemperature = itemView.findViewById(R.id.tv_current_temperature);
            maxTemperature = itemView.findViewById(R.id.tv_max_temperature);
            minTemperature = itemView.findViewById(R.id.tv_min_temperature);
            windSpeed = itemView.findViewById(R.id.tv_wind_speed);
            windDirection = itemView.findViewById(R.id.tv_wind_direction);
            createdOn = itemView.findViewById(R.id.tv_created_on);
            condition = itemView.findViewById(R.id.tv_condition);
            moreOptionsBtn = itemView.findViewById(R.id.iv_more_options);
        }
    }

    interface OnOptionsMenuClickListener {
        void onClick(View view, WeatherEntity weatherEntity);
    }
}

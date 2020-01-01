package com.example.android.weatherapp.retrofit;

import com.google.gson.annotations.SerializedName;

public class Condition {
    @SerializedName("main")
    private String condition;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    public Condition(String condition, String description, String icon) {
        this.condition = condition;
        this.description = description;
        this.icon = icon;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}

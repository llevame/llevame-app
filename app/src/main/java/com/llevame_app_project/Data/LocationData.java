package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {


    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

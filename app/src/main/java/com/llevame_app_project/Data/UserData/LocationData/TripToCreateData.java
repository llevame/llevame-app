package com.llevame_app_project.Data.UserData.LocationData;


import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripToCreateData {

    public TripToCreateData(String driver, List<LocationData> trip){
        this.driverUsername = driver;
        this.trip = trip;
    }

    @SerializedName("driver")
    @Expose
    String driverUsername;

    @SerializedName("trip")
    @Expose
    List<LocationData> trip;

    public String getDriverUsername() {
        return driverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    public List<LocationData> getTrip() {
        return trip;
    }

    public void setTrip(List<LocationData> trip) {
        this.trip = trip;
    }
}

